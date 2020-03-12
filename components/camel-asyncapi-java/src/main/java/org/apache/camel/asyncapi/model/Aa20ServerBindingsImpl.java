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
package org.apache.camel.asyncapi.model;

import org.apache.camel.asyncApi.Aa20ServerBindings;

public class Aa20ServerBindingsImpl extends Aa20AbstractBindingsImpl implements Aa20ServerBindings {


    public static Builder newBuilder() {
        return new Builder();
    }

    private Aa20ServerBindingsImpl() {
    }

    public Aa20ServerBindingsImpl(Builder b) {
        super(b);
    }

// --------------------------------------- builder ---------------------------------------------------------

    public static class Builder extends AbstractBindingsBuilder<Builder, Aa20ServerBindings> {

        @Override
        public Aa20ServerBindings done() {
            return new Aa20ServerBindingsImpl(this);
        }
    }
}
