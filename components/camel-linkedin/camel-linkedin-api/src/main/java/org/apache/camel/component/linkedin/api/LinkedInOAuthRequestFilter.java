/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.linkedin.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.LinkedInJSoupTest;

/**
 * LinkedIn OAuth request filter to handle OAuth token.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public final class LinkedInOAuthRequestFilter implements ClientRequestFilter {

    private static final int SC_OK = 200;
    private static final int SC_MOVED_TEMPORARILY = 302;
    private static final int SC_SEE_OTHER = 303;
    private static final String HEADER_LOCATION = "location";

    public static final String BASE_ADDRESS = "https://api.linkedin.com/v1";

    private static final Logger LOG = LoggerFactory.getLogger(LinkedInOAuthRequestFilter.class);

    private static final String AUTHORIZATION_URL_PREFIX = "https://www.linkedin.com";

    private static final String AUTHORIZATION_URL =  AUTHORIZATION_URL_PREFIX + "/uas/oauth2/authorization?"
        + "response_type=code&client_id=%s&state=%s&redirect_uri=%s";
    private static final String AUTHORIZATION_URL_WITH_SCOPE = AUTHORIZATION_URL_PREFIX + "/uas/oauth2/authorization?"
        + "response_type=code&client_id=%s&state=%s&scope=%s&redirect_uri=%s";

    private static final String ACCESS_TOKEN_URL = "https://www.linkedin.com/uas/oauth2/accessToken?"
        + "grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s";

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("&?([^=]+)=([^&]+)");

    private final OAuthParams oAuthParams;

    private OAuthToken oAuthToken;

    public LinkedInOAuthRequestFilter(OAuthParams oAuthParams, Map<String, Object> httpParams,
                                      boolean lazyAuth, String[] enabledProtocols) {

        //TODO JiriOndrusek test http proxy
        this.oAuthParams = oAuthParams;
        this.oAuthToken = null;

        if (!lazyAuth) {
            try {
                updateOAuthToken();
            } catch (IOException e) {
                throw new IllegalArgumentException(
                    String.format("Error authorizing user %s: %s", oAuthParams.getUserName(), e.getMessage()), e);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private String getRefreshToken() {

        try {

            final String csrfId = String.valueOf(new SecureRandom().nextLong());
            final String encodedRedirectUri = URLEncoder.encode(oAuthParams.getRedirectUri(), "UTF-8");
            final OAuthScope[] scopes = oAuthParams.getScopes();

            final String url = initializeUrl(csrfId, encodedRedirectUri, scopes);

            //load loginPage
            final Connection.Response loginPageResponse = getLoginResponse(url);
            final Document loginPage = loginPageResponse.parse(); // this is the document that contains response html

            //validate for loginErrors
            validatePageErrors(loginPage);

            //fill login form
            FormElement loginForm = (FormElement)loginPage.select("form").first();

            Element loginField = loginForm.select("input[name=session_key]").first();
            loginField.val(oAuthParams.getUserName());

            Element passwordField = loginForm.select("input[name=session_password]").first();
            passwordField.val(oAuthParams.getUserPassword());

            //submit loginPage
            Map<String, String> cookies = new HashMap();
            cookies.putAll(loginPageResponse.cookies());

            ResponseWithQuery response = submitFormAndRedirect(cookies, loginForm);

            //if needed allow acess
            if(response.getResponse() != null) {
                Document allowPage = response.getResponse().parse();
                FormElement allowForm = (FormElement)allowPage.select("form").get(1);
                response = submitFormAndRedirect(cookies, allowForm);
            }

            final Map<String, String> params = new HashMap<String, String>();
            final Matcher matcher = QUERY_PARAM_PATTERN.matcher(response.getRedirectQuerty());
            while (matcher.find()) {
                params.put(matcher.group(1), matcher.group(2));
            }
            // check if we got caught in a Captcha!
            if (params.get("challengeId") != null) {
                throw new SecurityException("Unable to login due to CAPTCHA, use with a valid accessToken instead!");
            }
            final String state = params.get("state");
            if (!csrfId.equals(state)) {
                throw new SecurityException("Invalid CSRF code!");
            } else {
                // return authorization code
                // TODO check results??
                return params.get("code");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error authorizing application: " + e.getMessage(), e);
        }
    }

    private ResponseWithQuery submitFormAndRedirect(Map<String, String> cookies, FormElement loginForm) throws IOException {
        Connection.Response response;

        int statusCode;
        String redirectUrl = null;
        FormElement form = loginForm;
        do {
            Connection con = redirectUrl == null ? form.submit() :  Jsoup.connect(redirectUrl);
            //clear tmp values
            form = null;
            redirectUrl = null;


            response = con
                    .followRedirects(false)
                    .cookies(cookies)
                    .execute();

            statusCode = response.statusCode();
            cookies.putAll(response.cookies());

            switch (statusCode) {
                case SC_OK:
                    Document page = response.parse();
                    validatePageErrors(page);
                    break;
                case SC_MOVED_TEMPORARILY:
                case SC_SEE_OTHER:
                    String headerLocation = response.header(HEADER_LOCATION);
                    URL location;
                    try {
                        location = new URL(headerLocation);
                        //in case that url is complete, we can skip following code, because it means that it is redirected to final page
                        //TODO JiriOndrusek test with reditect url linkedin.com
                        return new ResponseWithQuery(null, location.getQuery());
                    } catch (MalformedURLException e) {
                        //in case of malformed url, it misses prefix
                        location = new URL(AUTHORIZATION_URL_PREFIX+headerLocation);
                    }
                    final String locationQuery = location.getQuery();
                    if (locationQuery != null && locationQuery.contains("error=")) {
                        throw new IOException(URLDecoder.decode(locationQuery).replaceAll("&", ", "));
                    } else {
                        // follow the redirect to login form
                        redirectUrl = location.toString();
                    }

                    break;
                default:
                    throw new IllegalArgumentException(String.format("Can not loade page '%s' (status code '%s')", response.url().toString(), response.statusCode()));
            }

        } while(SC_MOVED_TEMPORARILY == statusCode || SC_SEE_OTHER == statusCode);

        return new ResponseWithQuery(response, null);
    }

    private void validatePageErrors(Document loginPage) {
        Elements errorDivs = loginPage.select("body[class=error]");
        if(errorDivs.isEmpty()) {
            errorDivs = loginPage.select("div[role=alert]:not([class*=hidden])");
        }

        if (!errorDivs.isEmpty()) {
            final String errorMessage = errorDivs.first().text();
            throw new IllegalArgumentException("Error authorizing application: " + errorMessage);
        }
    }

    private Connection.Response getLoginResponse(String url) throws IOException {

        Connection.Response loginPageResponse = Jsoup.connect(url).followRedirects(false).method(Connection.Method.GET).execute();
        // only handle errors returned with redirects
        if (loginPageResponse.statusCode() == SC_MOVED_TEMPORARILY || loginPageResponse.statusCode() == SC_SEE_OTHER) {
            final URL location = new URL(loginPageResponse.header(HEADER_LOCATION));
            final String locationQuery = location.getQuery();
            if (locationQuery != null && locationQuery.contains("error=")) {
                throw new IOException(URLDecoder.decode(locationQuery).replaceAll("&", ", "));
            } else {
                // follow the redirect to login form
                loginPageResponse = Jsoup.connect(location.toString()).followRedirects(false).method(Connection.Method.GET).execute();
            }
        }
        return loginPageResponse;
    }

    private String initializeUrl(String csrfId, String encodedRedirectUri, OAuthScope[] scopes) {
        String url;
        if (scopes == null || scopes.length == 0) {
            url = String.format(AUTHORIZATION_URL, oAuthParams.getClientId(), csrfId, encodedRedirectUri);
        } else {
            final int nScopes = scopes.length;
            final StringBuilder builder = new StringBuilder();
            int i = 0;
            for (OAuthScope scope : scopes) {
                builder.append(scope.getValue());
                if (++i < nScopes) {
                    builder.append("%20");
                }
            }
            url = String.format(AUTHORIZATION_URL_WITH_SCOPE, oAuthParams.getClientId(), csrfId, builder.toString(), encodedRedirectUri);
        }
        return url;
    }

    private OAuthToken getAccessToken(String refreshToken) throws IOException {
        final String tokenUrl = String.format(ACCESS_TOKEN_URL, refreshToken, oAuthParams.getRedirectUri(), oAuthParams.getClientId(), oAuthParams.getClientSecret());
        final Connection.Response response = Jsoup.connect(tokenUrl).ignoreContentType(true).method(Connection.Method.POST).execute();

        if (response.statusCode() != SC_OK) {
            throw new IOException(String.format("Error getting access token: [%s]", response.statusCode()));
        }
        final long currentTime = System.currentTimeMillis();
        final ObjectMapper mapper = new ObjectMapper();
        final Map map = mapper.readValue(response.body(), Map.class);
        final String accessToken = map.get("access_token").toString();
        final Integer expiresIn = Integer.valueOf(map.get("expires_in").toString());
        return new OAuthToken(refreshToken, accessToken,
            currentTime + TimeUnit.MILLISECONDS.convert(expiresIn, TimeUnit.SECONDS));
    }

    public synchronized OAuthToken getOAuthToken() {
        return oAuthToken;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        updateOAuthToken();

        // add OAuth query param
        final String requestUri = requestContext.getUri().toString();
        final StringBuilder builder = new StringBuilder(requestUri);
        if (requestUri.contains("?")) {
            builder.append('&');
        } else {
            builder.append('?');
        }
        builder.append("oauth2_access_token=").append(oAuthToken.getAccessToken());
        requestContext.setUri(URI.create(builder.toString()));
    }

    private synchronized void updateOAuthToken() throws IOException {

        // check whether an update is needed
        final long currentTime = System.currentTimeMillis();
        if (oAuthToken == null || oAuthToken.getExpiryTime() < currentTime) {
            LOG.info("OAuth token doesn't exist or has expired");

            // check whether a secure store is provided
            final OAuthSecureStorage secureStorage = oAuthParams.getSecureStorage();
            if (secureStorage != null) {

                oAuthToken = secureStorage.getOAuthToken();
                // if it returned a valid token, we are done, otherwise fall through and generate a new token
                if (oAuthToken != null && oAuthToken.getExpiryTime() > currentTime) {
                    return;
                }
                LOG.info("OAuth secure storage returned a null or expired token, creating a new token...");

                // throw an exception if a user password is not set for authorization
                if (oAuthParams.getUserPassword() == null || oAuthParams.getUserPassword().isEmpty()) {
                    throw new IllegalArgumentException("Missing password for LinkedIn authorization");
                }
            }

            // need new OAuth token, authorize user, LinkedIn does not support OAuth2 grant_type=refresh_token
            final String refreshToken = getRefreshToken();
            this.oAuthToken = getAccessToken(refreshToken);
            LOG.info("OAuth token created!");

            // notify secure storage
            if (secureStorage != null) {
                secureStorage.saveOAuthToken(this.oAuthToken);
            }
        }
    }

    /**
     * Helper class, which contains 2 return values needed inside method getRefreshToken.
     */
    class ResponseWithQuery {
        final Connection.Response response;
        final String redirectQuery;

        public ResponseWithQuery(Connection.Response response, String redirectQuery) {
            this.response = response;
            this.redirectQuery = redirectQuery;
        }

        public Connection.Response getResponse() {
            return response;
        }

        public String getRedirectQuerty() {
            return redirectQuery;
        }


    }
}
