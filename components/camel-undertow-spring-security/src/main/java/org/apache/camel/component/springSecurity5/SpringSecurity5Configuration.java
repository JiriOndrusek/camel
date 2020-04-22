package org.apache.camel.component.springSecurity5;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.Filter;

public interface SpringSecurity5Configuration {

    public Filter getSecurityFilter();

    public ClientRegistration getKeycloakRegistration();

}
