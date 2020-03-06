package org.apache.asyncapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.camel.asyncApi.Aa20AbstractSecuritySchema;
import org.apache.camel.asyncApi.Aa20ChannelItem;
import org.apache.camel.asyncApi.Aa20Object;
import org.apache.camel.asyncApi.Aa20Server;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class PlaygroundModelTest {

    @Test
    public void test() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);

        Aa20Object model = createModel();
        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(model);
        JsonNode node = objectMapper.readTree(bytes);
        String yaml = new YAMLMapper().writeValueAsString(node);

        System.out.println(yaml);


    }

    private Aa20Object createModel() {
        Aa20Object doc =  new Aa20Object("2.0.0");
        doc.createInfo("Streetlights API", "1.0.0")
                .setDescription("todo")
                .createLicense("Apache 2.0")
                    .setUrl("https://www.apache.org/licenses/LICENSE-2.0");

        Aa20Server prod = doc.createServer("production",
                "api.streetlights.smartylighting.com:{port}",
                "mqtt");
        prod.setDescription("Testbroker");
        prod.createVariable("port")
            .setDescription("Secure connection (TLS) is available through port 8883.")
            .setDefaultValue("1883")
            .createEnum("1883")
            .createEnum("8883");
        prod.createSecurityRequirement(Aa20AbstractSecuritySchema.Type.apiKey, Collections.emptyList())
                .createSecurityRequirement(Aa20AbstractSecuritySchema.Type.oauth2, Arrays.asList("streetlights:on", "streetlights:off", "streetlights:dim"))
                .createSecurityRequirement(Aa20AbstractSecuritySchema.Type.openIdConnect, Collections.emptyList());

        Aa20ChannelItem channel = doc.createChannel("smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measured");
        channel.setDescription("The topic on which measured values may be produced and consumed.");
        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId");
        channel.createSubscribe()
                .setSummary("Receive information about environmental lighting conditions of a particular streetlight.")
                .createTraitAsReference("#/components/operationTraits/kafka")
                .createMessageAsReference("#/components/messages/lightMeasured");

        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/on:");
        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId");
        channel.createPublish()
                .setOperationId("turnOn")
                .createTraitAsReference("#/components/operationTraits/kafka")
                .createMessageAsReference("#/components/messages/turnOnOff");

        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/turn/off:");
        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId")
                .createPublish()
                    .setOperationId("turnOff")
                    .createTraitAsReference("#/components/operationTraits/kafka")
                    .createMessageAsReference("#/components/messages/turnOnOff");

        channel = doc.createChannel("smartylighting/streetlights/1/0/action/{streetlightId}/dim");
        channel.createParameterAsReference("streetlightId", "#/components/parameters/streetlightId")
                .createPublish()
                .setOperationId("dimLight")
                .createTraitAsReference("#/components/operationTraits/kafka")
                .createMessageAsReference("/components/messages/dimLight");



        return doc;
    }
}
