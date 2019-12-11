package org.apache.camel.component.elytron;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import org.apache.camel.Processor;
import org.apache.camel.component.undertow.UndertowConsumer;
import org.apache.camel.component.undertow.UndertowEndpoint;
import org.wildfly.security.auth.server.SecurityIdentity;
import org.wildfly.security.authz.Roles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElytronConsumer extends UndertowConsumer {

    private Set<String> roles;
    private List<String> allowedRoles;

    public ElytronConsumer(UndertowEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    public ElytronEndpoint getElytronEndpoint() {
        return (ElytronEndpoint) super.getEndpoint();
    }

    @Override
    protected void registerEndpoint(UndertowEndpoint endpoint, HttpHandler httpHandler) {
        super.registerEndpoint(endpoint, httpHandler);
    }

    @Override
    public void handleRequest(HttpServerExchange httpExchange) throws Exception {
        SecurityIdentity identity = getElytronEndpoint().getElytronComponent().getSecurityDomain().getCurrentSecurityIdentity();

        if(identity != null) {
            //already authenticated
            Set<String> roles = new HashSet<>();
            Roles identityRoles = identity.getRoles();

            if (identityRoles != null) {
                for (String roleName : identityRoles) {
                    roles.add(roleName);
                }
            }

            if (isAllowed(roles, getElytronEndpoint().getAllowedRoles())) {
                super.handleRequest(httpExchange);
            } else {
                httpExchange.setStatusCode(StatusCodes.FORBIDDEN);
            }

            return;
        }

        super.handleRequest(httpExchange);
    }

    public boolean isAllowed(Set<String> roles, List<String> allowedRoles) throws Exception {
        for (String role : allowedRoles) {
            if (roles.contains(role)) {
                return true;
            }
        }

        return false;
    }
}
