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
package org.apache.camel.component.undertow.elytron.springboot;

import javax.annotation.Generated;
import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.elytron.UndertowElytronComponent;
import org.apache.camel.component.undertow.springboot.UndertowComponentAutoConfiguration;
import org.apache.camel.component.undertow.springboot.UndertowComponentConfiguration;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.spring.boot.ComponentConfigurationProperties;
import org.apache.camel.spring.boot.util.ConditionalOnCamelContextAndAutoConfigurationBeans;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.packaging.SpringBootAutoConfigurationMojo")
@Configuration
@Conditional({ConditionalOnCamelContextAndAutoConfigurationBeans.class,
        UndertowComponentAutoConfiguration.GroupConditions.class})
@AutoConfigureAfter(CamelAutoConfiguration.class)
@EnableConfigurationProperties({ComponentConfigurationProperties.class,
        UndertowComponentConfiguration.class})
public class UndertowElytronComponentAutoConfiguration extends UndertowComponentAutoConfiguration {

    @Lazy
    @Bean(name = "undertow-elytron-component")
    @ConditionalOnMissingBean(UndertowElytronComponent.class)
    public UndertowComponent configureUndertowComponent() throws Exception {
        return super.configureUndertowComponent();
    }

    @Override
    protected UndertowComponent createComponent() {
        return new UndertowElytronComponent();
    }
}