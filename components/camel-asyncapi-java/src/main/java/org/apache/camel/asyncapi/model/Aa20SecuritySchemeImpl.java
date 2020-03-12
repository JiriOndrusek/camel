package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.*;

public class Aa20SecuritySchemeImpl extends AbstractAa20SpecificationExtensionImpl implements Aa20SecurityScheme {

    private Type type;
    private String description;
    private String name;
    private String in;
    private String scheme;
    private String bearerFormat;
    private Aa20OAuthFlows flows;
    private String openConnectId;
    private String $ref;


    public static CommonBuilder newUserPassword() {
        return new CommonBuilder(Type.userPassword);
    }
    public static CommonBuilder newX509() {
        return new CommonBuilder(Type.X509);
    }
    public static CommonBuilder newSymmetricEncryption() {
        return new CommonBuilder(Type.symmetricEncryption);
    }
    public static CommonBuilder newAsymmetricEncryption() {
        return new CommonBuilder(Type.asymmetricEncryption);
    }
    public static ApiKeyBuilder newApiKey() {
        return new ApiKeyBuilder();
    }
    public static HttpBuilder newHttp() {
        return new HttpBuilder();
    }
    public static HttpApiKeyBuilder newHttpApiKey() {
        return new HttpApiKeyBuilder();
    }
    public static OAuth2Builder newOAuth2() {
        return new OAuth2Builder();
    }
    public static OpenIdConnectBuilder newOpenIdConnect() {
        return new OpenIdConnectBuilder();
    }

    private Aa20SecuritySchemeImpl() {
        super(null);
    }

    private Aa20SecuritySchemeImpl(AbstractBuilder b) {
        super(b);
        this.type = b.type;
        this.description = b.description;
        this.name = b.name;
        this.in = b.in;
        this.scheme = b.scheme;
        this.bearerFormat = b.bearerFormat;
        this.flows = b.flows;
        this.openConnectId = b.openConnectId;
        this.$ref = b.$ref;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getBearerFormat() {
        return bearerFormat;
    }

    public void setBearerFormat(String bearerFormat) {
        this.bearerFormat = bearerFormat;
    }

    @Override
    public Aa20OAuthFlows getFlows() {
        return flows;
    }

    public void setFlows(Aa20OAuthFlows flows) {
        this.flows = flows;
    }

    @Override
    public String getOpenConnectId() {
        return openConnectId;
    }

    public void setOpenConnectId(String openConnectId) {
        this.openConnectId = openConnectId;
    }

    @Override
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }
// --------------------------------------- builder ---------------------------------------------------------
    public static class CommonBuilder  extends AbstractBuilder<CommonBuilder> {

        public CommonBuilder(Type type) {
            super(type);
        }
    }

    public static class ApiKeyBuilder  extends AbstractBuilder<ApiKeyBuilder> {

        public ApiKeyBuilder() {
            super(Type.apiKey);
        }

        public ApiKeyBuilder withIn(String in) {
            this.in = in;
            return this;
        }
    }

    public static class HttpApiKeyBuilder  extends AbstractBuilder<HttpApiKeyBuilder> {

        public HttpApiKeyBuilder() {
            super(Type.httpApiKey);
        }

        public HttpApiKeyBuilder withIn(String in) {
            this.in = in;
            return this;
        }

        public HttpApiKeyBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

    public static class HttpBuilder  extends AbstractBuilder<HttpBuilder> {

        public HttpBuilder() {
            super(Type.http);
        }

        public HttpBuilder withSchema(String schema) {
            this.scheme = schema;
            return this;
        }

        public HttpBuilder withBearerFormat(String bearerFormat) {
            this.bearerFormat = bearerFormat;
            return this;
        }
    }

    public static class OAuth2Builder  extends AbstractBuilder<OAuth2Builder> {

        public OAuth2Builder() {
            super(Type.oauth2);
        }

        public OAuth2Builder withFlows(Aa20OAuthFlows flows) {
            this.flows = flows;
            return this;
        }
    }

    public static class OpenIdConnectBuilder  extends AbstractBuilder<OpenIdConnectBuilder> {

        public OpenIdConnectBuilder() {
            super(Type.openIdConnect);
        }

        public OpenIdConnectBuilder withOpenConnectId(String openConnectId) {
            this.openConnectId = openConnectId;
            return this;
        }
    }

    private static abstract class AbstractBuilder<T extends AbstractBuilder> extends AbstractSpecificationExtensionsBuilder<T, Aa20SecurityScheme> {

        private final Type type;
        private String description;
        protected String name;
        protected String in;
        protected String scheme;
        protected String bearerFormat;
        protected Aa20OAuthFlows flows;
        protected String openConnectId;
        private String $ref;

        public AbstractBuilder(Type type) {
            this.type = type;
        }

        public T withDescription(String description) {
            this.description = description;
            return (T)this;
        }

        public T with$ref(String $ref) {
            this.$ref = $ref;
            return (T)this;
        }


        @Override
        public Aa20SecurityScheme done() {
            return new Aa20SecuritySchemeImpl(this);
        }
    }
}
