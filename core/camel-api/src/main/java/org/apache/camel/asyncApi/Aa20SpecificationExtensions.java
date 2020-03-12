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
package org.apache.camel.asyncApi;

import java.util.Set;

/**
 * While the AsyncAPI Specification tries to accommodate most use cases,
 * additional data can be added to extend the specification at certain points.
 *
 * The extensions properties are implemented as patterned fields that
 * are always prefixed by "x-".
 *
 * ^x-[\w\d\-\_]+$
 * Allows extensions to the AsyncAPI Schema. The field name MUST begin with x-,
 * for example, x-internal-id. The value can be null, a primitive, an array
 * or an object. Can have any valid JSON format value.
 */
public interface Aa20SpecificationExtensions {

    Object getSpecificationExtension(String name);

    Set<String> getSpecificationExtensionKeys();
}
