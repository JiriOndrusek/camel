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
package org.apache.camel.component.undertow.elytron;


import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.UndertowEndpoint;
import org.apache.camel.component.undertow.UndertowHttpBinding;
import org.apache.camel.spi.UriParam;
import org.wildfly.security.authz.PermissionMapper;

/**
 *
 * @author JiriOndrusek
 */
public class UndertowElytronEndpoint extends UndertowEndpoint {

    @UriParam(label = "security")
    private PermissionMapper permissionMapper;

    public UndertowElytronEndpoint(String uri, UndertowComponent component) {
        super(uri, component);
    }

    public PermissionMapper getPermissionMapper() {
        return permissionMapper;
    }

    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
