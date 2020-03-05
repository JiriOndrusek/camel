package org.apache.camel.asyncapi;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.asyncApi.Aai20Object;
import org.apache.camel.spi.AsyncApiConfiguration;
import org.apache.camel.util.ObjectHelper;
import org.apache.camel.util.URISupport;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AsyncApiRouteDefinitionHelper {

    public static void invoke(CamelContext camelContext, AsyncApiConfiguration configuration) throws Exception {
        RouteDefinition answer = new RouteDefinition();
        Aai20Object o;

        // create the from endpoint uri which is using the rest-api component
        String from = "async-api:" + configuration.getApiContextPath();

        // append options
        Map<String, Object> options = new HashMap<String, Object>();

        String routeId = configuration.getApiContextRouteId();
        if (routeId == null) {
            routeId = answer.idOrCreate(camelContext.adapt(ExtendedCamelContext.class).getNodeIdFactory());
        }
        options.put("routeId", routeId);
        if (configuration.getComponent() != null && !configuration.getComponent().isEmpty()) {
            options.put("componentName", configuration.getComponent());
        }
        if (configuration.getApiContextIdPattern() != null) {
            options.put("contextIdPattern", configuration.getApiContextIdPattern());
        }

        if (!options.isEmpty()) {
            String query;
            try {
                query = URISupport.createQueryString(options);
            } catch (URISyntaxException e) {
                throw ObjectHelper.wrapRuntimeCamelException(e);
            }
            from = from + "?" + query;
        }

        // we use the same uri as the producer (so we have a little route for the rest api)
        String to = from;
        answer.fromRest(from);
        answer.id(routeId);
        answer.to(to);


        ((ModelCamelContext)camelContext).addRouteDefinitions(Collections.singletonList(answer));
    }
}
