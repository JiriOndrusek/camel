/*
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
package org.apache.camel.spi;

import java.util.Map;

/**
 * Configuration use by {@link org.apache.camel.spi.RestConsumerFactory} and {@link org.apache.camel.spi.RestApiConsumerFactory}
 * for Camel components to support the Camel {@link org.apache.camel.model.rest.RestDefinition rest} DSL.
 */
public class RestConfiguration extends ApiConfiguration {

    public static final String CORS_ACCESS_CONTROL_ALLOW_ORIGIN = "*";
    public static final String CORS_ACCESS_CONTROL_ALLOW_METHODS = "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, PATCH";
    public static final String CORS_ACCESS_CONTROL_MAX_AGE = "3600";
    public static final String CORS_ACCESS_CONTROL_ALLOW_HEADERS = "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers";

    public enum RestBindingMode {
        auto, off, json, xml, json_xml
    }

    public enum RestHostNameResolver {
        allLocalIp, localIp, localHostName
    }

    private String apiComponent;
    private String producerComponent;
    private String producerApiDoc;
    private boolean useXForwardHeaders = true;
    private RestBindingMode bindingMode = RestBindingMode.off;
    private boolean skipBindingOnErrorCode = true;
    private boolean clientRequestValidation;
    private String jsonDataFormat;
    private String xmlDataFormat;
    private Map<String, Object> componentProperties;
    private Map<String, Object> dataFormatProperties;
    private Map<String, Object> apiProperties;
    private Map<String, String> corsHeaders;

    /**
     * Gets the name of the Camel component to use as the REST API (such as swagger)
     *
     * @return the component name, or <tt>null</tt> to let Camel use the default name <tt>swagger</tt>
     */
    public String getApiComponent() {
        return apiComponent;
    }

    /**
     * Sets the name of the Camel component to use as the REST API (such as swagger)
     *
     * @param apiComponent the name of the component (such as swagger)
     */
    public void setApiComponent(String apiComponent) {
        this.apiComponent = apiComponent;
    }

    /**
     * Gets the name of the Camel component to use as the REST producer
     *
     * @return the component name, or <tt>null</tt> to let Camel search the {@link Registry} to find suitable implementation
     */
    public String getProducerComponent() {
        return producerComponent;
    }

    /**
     * Sets the name of the Camel component to use as the REST producer
     *
     * @param componentName the name of the component (such as http, netty-http, undertow, etc.)
     */
    public void setProducerComponent(String componentName) {
        this.producerComponent = componentName;
    }

    /**
     * Gets the location of the api document (swagger api) the REST producer will use
     * to validate the REST uri and query parameters are valid accordingly to the api document.
     */
    public String getProducerApiDoc() {
        return producerApiDoc;
    }

    /**
     * Sets the location of the api document (swagger api) the REST producer will use
     * to validate the REST uri and query parameters are valid accordingly to the api document.
     * This requires adding camel-swagger-java to the classpath, and any miss configuration
     * will let Camel fail on startup and report the error(s).
     * <p/>
     * The location of the api document is loaded from classpath by default, but you can use
     * <tt>file:</tt> or <tt>http:</tt> to refer to resources to load from file or http url.
     */
    public void setProducerApiDoc(String producerApiDoc) {
        this.producerApiDoc = producerApiDoc;
    }

    /**
     * WWhether to use X-Forward headers to set host etc. for Swagger.
     * <p/>
     * This option is default <tt>true</tt>.
     */
    public boolean isUseXForwardHeaders() {
        return useXForwardHeaders;
    }

    /**
     * WWhether to use X-Forward headers to set host etc. for Swagger.
     * <p/>
     * This option is default <tt>true</tt>.
     * 
     * @param useXForwardHeaders whether to use X-Forward headers
     */
    public void setUseXForwardHeaders(boolean useXForwardHeaders) {
        this.useXForwardHeaders = useXForwardHeaders;
    }

    /**
     * Gets the binding mode used by the REST consumer
     *
     * @return the binding mode
     */
    public RestBindingMode getBindingMode() {
        return bindingMode;
    }

    /**
     * Sets the binding mode to be used by the REST consumer
     *
     * @param bindingMode the binding mode
     */
    public void setBindingMode(RestBindingMode bindingMode) {
        this.bindingMode = bindingMode;
    }

    /**
     * Sets the binding mode to be used by the REST consumer
     *
     * @param bindingMode the binding mode
     */
    public void setBindingMode(String bindingMode) {
        this.bindingMode = RestBindingMode.valueOf(bindingMode);
    }

    /**
     * Whether to skip binding output if there is a custom HTTP error code, and instead use the response body as-is.
     * <p/>
     * This option is default <tt>true</tt>.
     *
     * @return whether to skip binding on error code
     */
    public boolean isSkipBindingOnErrorCode() {
        return skipBindingOnErrorCode;
    }

    /**
     * Whether to skip binding output if there is a custom HTTP error code, and instead use the response body as-is.
     * <p/>
     * This option is default <tt>true</tt>.
     *
     * @param skipBindingOnErrorCode whether to skip binding on error code
     */
    public void setSkipBindingOnErrorCode(boolean skipBindingOnErrorCode) {
        this.skipBindingOnErrorCode = skipBindingOnErrorCode;
    }

    public boolean isClientRequestValidation() {
        return clientRequestValidation;
    }

    /**
     * Whether to enable validation of the client request to check whether the Content-Type and Accept headers from
     * the client is supported by the Rest-DSL configuration of its consumes/produces settings.
     * <p/>
     * This can be turned on, to enable this check. In case of validation error, then HTTP Status codes 415 or 406 is returned.
     * <p/>
     * The default value is false.
     */
    public void setClientRequestValidation(boolean clientRequestValidation) {
        this.clientRequestValidation = clientRequestValidation;
    }

    /**
     * Gets the name of the json data format.
     * <p/>
     * <b>Important:</b> This option is only for setting a custom name of the data format, not to refer to an existing data format instance.
     *
     * @return the name, or <tt>null</tt> to use default
     */
    public String getJsonDataFormat() {
        return jsonDataFormat;
    }

    /**
     * Sets a custom json data format to be used
     * <p/>
     * <b>Important:</b> This option is only for setting a custom name of the data format, not to refer to an existing data format instance.
     *
     * @param name name of the data format
     */
    public void setJsonDataFormat(String name) {
        this.jsonDataFormat = name;
    }

    /**
     * Gets the name of the xml data format.
     * <p/>
     * <b>Important:</b> This option is only for setting a custom name of the data format, not to refer to an existing data format instance.
     *
     * @return the name, or <tt>null</tt> to use default
     */
    public String getXmlDataFormat() {
        return xmlDataFormat;
    }

    /**
     * Sets a custom xml data format to be used.
     * <p/>
     * <b>Important:</b> This option is only for setting a custom name of the data format, not to refer to an existing data format instance.
     *
     * @param name name of the data format
     */
    public void setXmlDataFormat(String name) {
        this.xmlDataFormat = name;
    }

    /**
     * Gets additional options on component level
     *
     * @return additional options
     */
    public Map<String, Object> getComponentProperties() {
        return componentProperties;
    }

    /**
     * Sets additional options on component level
     *
     * @param componentProperties the options
     */
    public void setComponentProperties(Map<String, Object> componentProperties) {
        this.componentProperties = componentProperties;
    }

    /**
     * Gets additional options on data format level
     *
     * @return additional options
     */
    public Map<String, Object> getDataFormatProperties() {
        return dataFormatProperties;
    }

    /**
     * Sets additional options on data format level
     *
     * @param dataFormatProperties the options
     */
    public void setDataFormatProperties(Map<String, Object> dataFormatProperties) {
        this.dataFormatProperties = dataFormatProperties;
    }

    public Map<String, Object> getApiProperties() {
        return apiProperties;
    }

    /**
     * Sets additional options on api level
     *
     * @param apiProperties the options
     */
    public void setApiProperties(Map<String, Object> apiProperties) {
        this.apiProperties = apiProperties;
    }

    /**
     * Gets the CORS headers to use if CORS has been enabled.
     *
     * @return the CORS headers
     */
    public Map<String, String> getCorsHeaders() {
        return corsHeaders;
    }

    /**
     * Sets the CORS headers to use if CORS has been enabled.
     *
     * @param corsHeaders the CORS headers
     */
    public void setCorsHeaders(Map<String, String> corsHeaders) {
        this.corsHeaders = corsHeaders;
    }
}
