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

import java.util.Collections;

public class PlaygroundModelTest {

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
        resolver.addMapping(Aa20Components.class, Aa20ComponentsImpl.class);
        resolver.addMapping(Aa20Schema.class, Aa20SchemaImpl.class);
        resolver.addMapping(Aa20MessageTrait.class, Aa20MessageTraitImpl.class);
        resolver.addMapping(Aa20OperationTrait.class, Aa20OperationTraitImpl.class);
        resolver.addMapping(Aa20Parameter.class, Aa20ParameterImpl.class);
        resolver.addMapping(Aa20SecurityScheme.class, Aa20SecuritySchemeImpl.class);
        resolver.addMapping(Aa20OAuthFlows.class, Aa20OAuthFlowsImpl.class);
        resolver.addMapping(Aa20OAuthFlow.class, Aa20OAuthFlowImpl.class);
        resolver.addMapping(Aa20OperationBindings.class, Aa20OperationBindingsImpl.class);

        module.setAbstractTypes(resolver);

        objectMapper.registerModule(module);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);


        Aa20Object doc = objectMapper.readValue(s, Aa20ObjectImpl.class);
        String s2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc);
        Assert.assertEquals(s, s2);

        System.out.println(s);
    }

    @Test
    public void testJson() throws Exception {


        Aa20Object obj = createModel();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

        System.out.println(s);
    }

    @Test
    public void testYaml() throws Exception {


        Aa20Object obj = createModel();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
        JsonNode node = objectMapper.readTree(bytes);
        String yaml = new YAMLMapper().writeValueAsString(node);

        System.out.println(yaml);


    }

    private Aa20Object createModel() {


        Aa20Object model = Aa20ObjectImpl.newBuilder()
            .withAsyncApi("2.0.0")
            .addInfo(infoBuilder -> infoBuilder
                .withTitle("Streetlights API")
                .withDescription("The Smartylighting Streetlights API allows you to remotely manage the city lights.")
                .withVersion("1.0.0")
                .addLicense(license -> license
                        .withName("Apache 2.0")
                        .withUrl("https://www.apache.org/licenses/LICENSE-2.0")
                )
            )
            .withServer("production", Aa20ServerImpl.newBuilder()
                .withUrl("api.streetlights.smartylighting.com:{port}")
                .withProtocol("mqtt")
                .withDescription("Test broker")
                .withVariable("port", Aa20ServerVariableImpl.newBuilder()
                    .withDescription("Secure connection (TLS) is available through port 8883.")
                    .withEnum("1883")
                    .withEnum("8883").done())
                .withSecurity(Aa20SecurityRequirementImpl.newBuilder()
                    .withApiKey()
                    .withOAuth2("streetlights:on").withOAuth2("streetlights:off").withOAuth2("streetlights:dim")
                    .withOpenIdConnect()
                    .done())
                .done())
            .withChannel("smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured", Aa20ChannelItemImpl.newBuilder()
                .withDescription("The topic on which measured values may be produced and consumed.")
                .withParameters("streetlightId",
                        Aa20ParameterImpl.newBuilder().with$ref("#/components/parameters/streetlightId'").done())
                .withSubscribe(Aa20OperationImpl.newBuilder()
                    .withDescription("Receive information about environmental lighting conditions of a particular streetlight.")
                    .withOperationId("receiveLightMeasurement")
                    .withTrait(Aa20OperationTraitImpl.newBuilder().with$ref("#/components/operationTraits/kafka").done())
                    .withMessage(Aa20MessageImpl.newBuilder().with$ref("#/components/messages/lightMeasured").done())
                    .done())
                .withPublish(Aa20OperationImpl.newBuilder()
                    .withOperationId("turnOn")
                    .withMessage(Aa20MessageImpl.newBuilder()
                        .withOneOf(Aa20MessageImpl.newBuilder().with$ref("#/components/messages/turnOnOff1").done())
                        .withOneOf(Aa20MessageImpl.newBuilder().with$ref("#/components/messages/turnOnOff2").done())
                        .with$ref("#/components/messages/turnOnOff")
                    .done())
                .done())
            .done())
            .withChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/on", Aa20ChannelItemImpl.newBuilder()
                .withParameters("streetlightId",
                        Aa20ParameterImpl.newBuilder().with$ref("#/components/parameters/streetlightId'").done())
                .withPublish(Aa20OperationImpl.newBuilder()
                    .withOperationId("turnOn")
                    .withTrait(Aa20OperationTraitImpl.newBuilder()
                            .with$ref("#/components/operationTraits/kafka").done())
                    .withMessage(Aa20MessageImpl.newBuilder()
                            .with$ref("#/components/messages/turnOnOff").done())
                .done())
            .done())
            .withChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/off", Aa20ChannelItemImpl.newBuilder()
                    .withParameters("streetlightId",
                            Aa20ParameterImpl.newBuilder().with$ref("#/components/parameters/streetlightId").done())
                    .withPublish(Aa20OperationImpl.newBuilder()
                        .withOperationId("turnOff")
                        .withTrait(Aa20OperationTraitImpl.newBuilder().with$ref("#/components/operationTraits/kafka").done())
                        .withMessage(Aa20MessageImpl.newBuilder().with$ref("#/components/messages/turnOnOff").done())
                        .done())
                .done())
            .withChannel("smartylighting/streetlights/1/0/action/{streetlightId}/dim", Aa20ChannelItemImpl.newBuilder()
                        .withParameters("streetlightId", Aa20ParameterImpl.newBuilder()
                        .with$ref("#/components/parameters/streetlightId").done())
                .withPublish(Aa20OperationImpl.newBuilder()
                    .withOperationId("dimLight")
                    .withTrait(Aa20OperationTraitImpl.newBuilder().with$ref("#/components/operationTraits/kafka").done())
                    .withMessage(Aa20MessageImpl.newBuilder().with$ref("#/components/messages/dimLight").done())
                    .done())
                .done())
            .withComponents(Aa20ComponentsImpl.newBuilder()
                    .withMessage("lightMeasured", Aa20MessageImpl.newBuilder()
                            .withName("lightMeasured")
                            .withTitle("Light measured")
                            .withSummary("Inform about environmental lighting conditions for a particular streetlight.")
                            .withContentType("application/json")
                            .withTrait(Aa20MessageTraitImpl.newBuilder().with$ref("#/components/messageTraits/commonHeaders").done())
                            .withPayload(Collections.singletonMap("$ref", "#/components/schemas/lightMeasuredPayload")) //todo
                        .done())
                    .withMessage("turnOnOff", Aa20MessageImpl.newBuilder()
                            .withTitle("Turn on/off")
                            .withSummary("Command a particular streetlight to turn the lights on or off.")
                            .withTrait(Aa20MessageTraitImpl.newBuilder().with$ref("#/components/messageTraits/commonHeaders").done())
                            .withPayload(Collections.singletonMap("$ref", "#/components/schemas/turnOnOffPayload"))
                        .done())
                    .withMessage("dimLight", Aa20MessageImpl.newBuilder()
                            .withTitle("Dim light")
                            .withSummary("Command a particular streetlight to dim the lights.")
                            .withTrait(Aa20MessageTraitImpl.newBuilder().with$ref("#/components/messageTraits/commonHeaders").done())
                            .withPayload(Collections.singletonMap("$ref", "#/components/schemas/dimLightPayload"))
                        .done())
                    .addSchema("lightMeasuredPayload", schemaBuilder -> schemaBuilder
                            .withString("type", "object")
                            .withObject("properties", o -> o
                                    .withObject("lumens", o1 -> o1
                                            .withString("type", "integer")
                                            .withInteger("minimum", 0)
                                            .withString("description", "Light intensity measured in lumens."))
                                    .withObject("sentAt", o2 -> o2
                                            .withString("$ref", "#/components/schemas/sentAt")))
                            .done())

//                    .witSchema("schema01", Aa20SchemaImpl.newBuilder() //todo
//                            .with$ref("#ref")
//                            .withDeprecated(true)
//                            .withData("data", "test")
//                            .withSpecificationExtension("x-schema", "xtest")
//                            .done())
//
//
//
                    .withSecurityScheme("apiKey", Aa20SecuritySchemeImpl.newApiKey()
                            .withIn("user")
                            .withDescription("Provide your API key as the user and leave the password empty.").done())
                    .withSecurityScheme("supportedOauthFlows", Aa20SecuritySchemeImpl.newOAuth2()
                            .withDescription(" Flows to support OAuth 2.0")
                            .withFlows(Aa20OAuthFlowsImpl.newBuilder()
                                    .withImplicit(Aa20OAuthFlowImpl.newBuilder()
                                        .withAuthorizationUrl("https://authserver.example/auth")
                                        .withScope("streetlights:on", "Ability to switch lights on")
                                        .withScope("streetlights:off", "Ability to switch lights off")
                                        .withScope("streetlights:dim", "Ability to dim the lights").done())
                                    .withPassword(Aa20OAuthFlowImpl.newBuilder()
                                        .withAuthorizationUrl("https://authserver.example/auth")
                                        .withScope("streetlights:on", "Ability to switch lights on")
                                        .withScope("streetlights:off", "Ability to switch lights off")
                                        .withScope("streetlights:dim", "Ability to dim the lights").done())
                                    .withClientCredentials(Aa20OAuthFlowImpl.newBuilder()
                                        .withAuthorizationUrl("https://authserver.example/auth")
                                        .withScope("streetlights:on", "Ability to switch lights on")
                                        .withScope("streetlights:off", "Ability to switch lights off")
                                        .withScope("streetlights:dim", "Ability to dim the lights").done())
                                    .withAuthorizationCode(Aa20OAuthFlowImpl.newBuilder()
                                        .withAuthorizationUrl("https://authserver.example/auth")
                                        .withTokenUrl("https://authserver.example/token")
                                        .withRefreshUrl("https://authserver.example/refresh")
                                        .withScope("streetlights:on", "Ability to switch lights on")
                                        .withScope("streetlights:off", "Ability to switch lights off")
                                        .withScope("streetlights:dim", "Ability to dim the lights").done())
                                    .done())
                            .done())
                    .withSecurityScheme("openIdConnectWellKnown", Aa20SecuritySchemeImpl.newOpenIdConnect()
                            .withOpenConnectId("https://authserver.example/.well-known").done())
                    .withParameter("streetlightId", Aa20ParameterImpl.newBuilder()
                            .witDescription("The ID of the streetlight.")
                            .withSchema(Aa20SchemaImpl.newBuilder().withString("type", "string").done()).done())
                    .withMessageTrait("commonHeaders", Aa20MessageTraitImpl.newBuilder()
                            .withHeaders(Aa20SchemaImpl.newBuilder().withString("type", "object").done()).done())
                    .withOperationTrait("kafka", Aa20OperationTraitImpl.newBuilder()
                            .withBindings(Aa20OperationBindingsImpl.newBuilder()
                                    .withKafkaSchema("clientId", "my-app-id").done()).done())
                    .done())
            .done();

        return model;
    }
//
//    private Aa20Object createModel2() {
//
//        Aa20OperationImpl.OperationBuilder b = Aa20OperationImpl.newBuilder(null, null)
//                    .withOperationId("turnOn")
//                    .addMessage()
//                        .addOneOfMessage().with$ref("#/components/messages/turnOnOff1").done()
//                        .addOneOfMessage().with$ref("#/components/messages/turnOnOff2").done()
//                        .with$ref("#/components/messages/turnOnOff")
//                    .done();
//Aa20Operation o = b.build();
//        return null;
//    }

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
