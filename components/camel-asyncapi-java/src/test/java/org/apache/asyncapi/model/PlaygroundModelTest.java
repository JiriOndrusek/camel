package org.apache.asyncapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.camel.asyncApi.*;
import org.apache.camel.asyncapi.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlaygroundModelTest {

//    @Test
//    public void testYaml() throws Exception{
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
//
//        Aa20Object model = createModel();
//        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(model);
//        JsonNode node = objectMapper.readTree(bytes);
//        String yaml = new YAMLMapper().writeValueAsString(node);
//
//        System.out.println(yaml);
//
//
//    }
//
//    @Test
//    public void testJson() throws Exception{
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
//
//        Aa20Object model = createModel();
//        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
//
//        System.out.println(json);
//
//
//    }
    @Test
    public void test2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(Aa20Info.class, Aa20InfoImpl.class);
        resolver.addMapping(Aa20Contact.class, Aa20ContactImpl.class);
        resolver.addMapping(Aa20License.class, Aa20LicenseImpl.class);
        resolver.addMapping(Aa20Server.class, Aa20ServerImpl.class);
        resolver.addMapping(Aa20ServerVariable.class, Aa20ServerVariableImpl.class);
        resolver.addMapping(Aa20ServerBindings.class, Aa20ServerBindingsImpl.class);
        resolver.addMapping(Aa20SecurityRequirement.class, Aa20SecurityRequirementImpl.class);
        resolver.addMapping(Aa20ChannelItem.class, Aa20ChannelItemImpl.class);
        resolver.addMapping(Aa20Message.class, Aa20MessageImpl.class);
        resolver.addMapping(Aa20Operation.class, Aa20OperationImpl.class);

        module.setAbstractTypes(resolver);

        objectMapper.registerModule(module);


        //JSON from file to Object
        Aa20Object doc = objectMapper.readValue(new File("/home/jondruse/git/camel/components/camel-asyncapi-java/src/test/resources/generated.json"), Aa20ObjectImpl.class);
        System.out.println(doc);
    }

    @Test
    public void testCompareSerializationAndDeserializationr() throws Exception {


        Aa20Object obj = createModel();



        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(Aa20Info.class, Aa20InfoImpl.class);
        resolver.addMapping(Aa20Contact.class, Aa20ContactImpl.class);
        resolver.addMapping(Aa20License.class, Aa20LicenseImpl.class);
        resolver.addMapping(Aa20Server.class, Aa20ServerImpl.class);
        resolver.addMapping(Aa20ServerVariable.class, Aa20ServerVariableImpl.class);
        resolver.addMapping(Aa20ServerBindings.class, Aa20ServerBindingsImpl.class);
        resolver.addMapping(Aa20SecurityRequirement.class, Aa20SecurityRequirementImpl.class);
        resolver.addMapping(Aa20ChannelItem.class, Aa20ChannelItemImpl.class);
        resolver.addMapping(Aa20Message.class, Aa20MessageImpl.class);
        resolver.addMapping(Aa20Operation.class, Aa20OperationImpl.class);

        module.setAbstractTypes(resolver);

        objectMapper.registerModule(module);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        System.out.println(s);

        Aa20Object doc = objectMapper.readValue(s, Aa20ObjectImpl.class);
        String s2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc);

        Assert.assertEquals(s, s2);
    }
  @Test
    public void testBuilder() throws Exception {


        Aa20Object obj = createModel();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

//        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
//        JsonNode node = objectMapper.readTree(bytes);
//        String yaml = new YAMLMapper().writeValueAsString(node);

//        System.out.println(yaml);


        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

        System.out.println(s);
    }

    private Aa20Object createModel() {

        Aa20ObjectImpl.Builder builder = Aa20ObjectImpl.newBuilder();

        builder.withAsyncApi("2.0.0")
            .addInfo()
                .withTitle("Streetlights API")
                .withVersion("1.0.0")
                .addContact()
                    .withEmail("test@email.com")
                    .withName("test")
                .done()
                .addLicense()
                    .withName("license name")
                    .withUrl("http://testLicense.com")
                .done()
            .done()
            .addServer("production")
                .withUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .withProtocol("mqtt")
                .withDescription("test broker")
                .addSecurity()
                    .withApiKey()
                    .withOAuth2("streetlights:on").withOAuth2("streetlights:off").withOAuth2("streetlights:dim")
                    .withOpenIdConnect()
                .done()
                .addVariable("port").withDescription("Secure connection (TLS) is available through port 8883.").withEnum("1883").withEnum("8883").done()
                .addBindings().withHttp().done()
                .addBindings().withWsSchema("test").done()
            .done()
            .addChannel("smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured")
                .withDescription("The topic on which measured values may be produced and consumed.")
                .addSubscribe()
                    .withDescription("Receive information about environmental lighting conditions of a particular streetlight.")
                    .addMessage()
                        .with$ref("#/components/messages/lightMeasured")
                    .done()
                .done()
                .addPublish()
                    .withOperationId("turnOn")
                    .addMessage()
                        .addOneOfMessage().with$ref("#/components/messages/turnOnOff1").done()
                        .addOneOfMessage().with$ref("#/components/messages/turnOnOff2").done()
                        .with$ref("#/components/messages/turnOnOff")
                    .done()
                .done()
            .done();

        return builder.build();
    }

//    private Aa20Object createModel() {
//        Aa20Object doc =  new Aa20Object("2.0.0");
//        doc.createInfo("Streetlights API", "1.0.0")
//                .setDescription("todo")
//                .createLicense("Apache 2.0")
//                    .setUrl("https://www.apache.org/licenses/LICENSE-2.0");
//
//        Aa20Server prod = doc.createServer("production",
//                "api.streetlights.smartylighting.com:{port}",
//                "mqtt");
//        prod.setDescription("Testbroker");
//        prod.createVariable("port")
//            .setDescription("Secure connection (TLS) is available through port 8883.")
////            .setDefaultValue("1883")
//            .createEnum("1883")
//            .createEnum("8883");
//
//       prod.createSecurityRequirementApiKey();
//       prod.createSecurityRequirementOAuth2().createSchema("streetlights:on").createSchema("streetlights:off").createSchema("streetlights:dim");
//       prod.createSecurityRequirementOpenIdConnect();
//
//
//        Aa20ChannelItem channel = doc.createChannel("smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured");
//        channel.setDescription("The topic on which measured values may be produced and consumed.");
//        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId");
//        channel.createSubscribe()
//                .setSummary("Receive information about environmental lighting conditions of a particular streetlight.")
//                .createTraitAsReference("#/components/operationTraits/kafka")
//                .createMessageAsReference("#/components/messages/lightMeasured");
//
//        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/on:");
//        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId");
//        channel.createPublish()
//                .setOperationId("turnOn")
//                .createTraitAsReference("#/components/operationTraits/kafka")
//                .createMessageAsReference("#/components/messages/turnOnOff");
//
//        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/off:");
//        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId")
//                .createPublish()
//                    .setOperationId("turnOff")
//                    .createTraitAsReference("#/components/operationTraits/kafka")
//                    .createMessageAsReference("#/components/messages/turnOnOff");
//
//        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/dim");
//        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId")
//                .createPublish()
//                .setOperationId("dimLight")
//                .createTraitAsReference("#/components/operationTraits/kafka")
//                .createMessageAsReference("#/components/messages/dimLight");
//
//        Aa20Components comps = doc.createComponents();
//        comps.createMessage("lightMeasured")
//                .setName("lightMeasured")
//                .setTitle("Streetlights API")
//                .setSummary("Inform about environmental lighting conditions for a particular streetlight.")
//                .setContentType("application/json")
//                .createTraitAsReference("#/components/messageTraits/commonHeaders")
//                .createPayload("$ref", "#/components/schemas/lightMeasuredPayload");
//
//        comps.createMessage("turnOnOff")
//            .setName("turnOnOff")
//            .setTitle("Turn on/off")
//            .setSummary("ommand a particular streetlight to turn the lights on or off.")
//            .createTraitAsReference("#/components/messageTraits/commonHeaders")
//            .createPayload("$ref", "#/components/messageTraits/commonHeaders");
//
//        comps.createMessage("dimLight")
//                .setName("dimLight")
//                .setTitle("Dim light")
//                .setSummary("Command a particular streetlight to dim the lights.")
//                .createTraitAsReference("#/components/messageTraits/commonHeaders")
//                .createPayload("$ref", "#/components/schemas/dimLightPayload");
//
//        Aa20Schema schema = comps.createSchema("lightMeasuredPayload");
//        schema.put("type", "object");
//        Map<String, Object> properties = new LinkedHashMap<>();
//        schema.put("properties", properties);
//        Map<String, Object> lumens = new LinkedHashMap<>();
//        properties.put("lumens", lumens);
//        lumens.put("type", "integer");
//        lumens.put("minimum", 0);
//        lumens.put("description", "Light intensity measured in lumens.");
//        properties.put("sentAt", Collections.singletonMap("$ref", "#/components/schemas/sentAt"));
//
//        schema = comps.createSchema("turnOnOffPayload");
//        schema.put("type", "object");
//        Map<String, Object> command = new LinkedHashMap<>();
//        schema.put("properties", properties);
//        properties.put("command", command);
//        lumens.put("type", "string");
//        lumens.put("enum", Arrays.asList("on", "off"));
//        lumens.put("description", "Whether to turn on or off the light.");
//        properties.put("sentAt", Collections.singletonMap("$ref", "#/components/schemas/sentAt"));
//
//        schema = comps.createSchema("dimLightPayload");
//        schema.put("type", "object");
//        Map<String, Object> percentage = new LinkedHashMap<>();
//        schema.put("properties", properties);
//        properties.put("percentage", percentage);
//        percentage.put("type", "integer");
//        percentage.put("minimum", 0);
//        percentage.put("maximum", 100);
//        percentage.put("description", "Percentage to which the light should be dimmed to.");
//        properties.put("sentAt", Collections.singletonMap("$ref", "#/components/schemas/sentAt"));
//
//        schema = comps.createSchema("sentAt");
//        schema.put("type", "string");
//        schema.put("format", "date-time");
//        schema.put("description", "Date and time when the message was sent.");
//
//        comps.createSecuritySchemaApiKey("apiKey", "user")
//                .setDescription("Provide your API key as the user and leave the password empty.");
//        Aa20SecuritySchemaOAuth2 oAuthScheme = comps.createSecuritySchemaHttpOAuth2("supportedOauthFlows");
//        oAuthScheme.setDescription("Flows to support OAuth 2.0");
//
//        //todo even token urls should be mandatory
//        oAuthScheme.getFlows().createImplicit()
//                .setAuthorizationUrl("https://authserver.example/auth")
//                .createScope("streetlights:on", "Ability to switch lights on")
//                .createScope("streetlights:off", "Ability to switch lights off")
//                .createScope("streetlights:dim", "Ability to dim the lights");
//        oAuthScheme.getFlows().createPassword()
//                .setTokenUrl("https://authserver.example/token")
//                .createScope("streetlights:on", "Ability to switch lights on")
//                .createScope("streetlights:off", "Ability to switch lights off")
//                .createScope("streetlights:dim", "Ability to dim the lights");
//        oAuthScheme.getFlows().createClientCredentials()
//                .setTokenUrl("https://authserver.example/token")
//                .createScope("streetlights:on", "Ability to switch lights on")
//                .createScope("streetlights:off", "Ability to switch lights off")
//                .createScope("streetlights:dim", "Ability to dim the lights");
//        oAuthScheme.getFlows().createAuthorizationCode()
//                .setAuthorizationUrl("https://authserver.example/auth")
//                .setTokenUrl("https://authserver.example/token")
//                .setRefreshUrl("https://authserver.example/refresh")
//                .createScope("streetlights:on", "Ability to switch lights on")
//                .createScope("streetlights:off", "Ability to switch lights off")
//                .createScope("streetlights:dim", "Ability to dim the lights");
//
//        //todo openIdConnect vs openIdConnectUrl
//        comps.createSecuritySchemaOpenIdConnectDiscovery("openIdConnectWellKnown", "https://authserver.example/.well-known");
//
//        comps.createParameter("streetlightId")
//                .setDescription("The ID of the streetlight.")
//                .createSchema()
//                    .put("type", "string");
//
//        Aa20Schema headers = comps.createMessageTrait("commonHeaders")
//            .createHeaders();
//        headers.put("type", "object");
//        properties = new LinkedHashMap<>();
//
////        Map<String, Object> myAppHeader = new LinkedHashMap<>();
////        properties.put("my-app-header", myAppHeader);
//        headers.put("properties", properties);
////        myAppHeader.put("type", "integer");
////        myAppHeader.put("minimum", 0);
////        myAppHeader.put("maximum", 100);
//
//        Aa20MessageBinding binding = comps.createOperationTrait("kafka")
//                .createBindings(Aa20MessageBinding.Field.kafka);
//        binding.put(Aa20MessageBinding.Field.kafka, Collections.singletonMap("clientId", "my-app-id"));
//
//
//
//
//
//        return doc;
//    }

}
