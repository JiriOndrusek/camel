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

/**
 * A simple object to allow referencing other components in the specification,
 * internally and externally.
 *
 * The Reference Object is defined by JSON Reference and follows the same structure,
 * behavior and rules. A JSON Reference SHALL only be used to refer to a schema that
 * is formatted in either JSON or YAML. In the case of a YAML-formatted Schema,
 * the JSON Reference SHALL be applied to the JSON representation of that schema.
 * The JSON representation SHALL be made by applying the conversion described here.
 *
 * For this specification, reference resolution is done as defined by the JSON Reference
 * specification and not by the JSON Schema specification.
 */
public class Aa20Reference  implements Aa20OrReferenceType {

    final String $ref;

    /**
     * @param $ref Required. The reference string.
     */
    public Aa20Reference(String $ref) {
        this.$ref = $ref;
    }

    public String get$ref() {
        return $ref;
    }
}
