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

import java.util.LinkedHashMap;

/**
 * The Schema Object allows the definition of input and output data types. These types can be objects, but also primitives and arrays.
 * This object is a superset of the JSON Schema Specification Draft 07.
 *
 * Further information about the properties can be found in JSON Schema Core and JSON Schema Validation. Unless stated otherwise,
 * the property definitions follow the JSON Schema specification as referenced here.
 *
 * Properties
 * The AsyncAPI Schema Object is a JSON Schema vocabulary which extends JSON Schema Core and Validation vocabularies. As such,
 * any keyword available for those vocabularies is by definition available in AsyncAPI, and will work the exact same way, including but not limited to:
 *
 * title
 * type
 * required
 * multipleOf
 * maximum
 * exclusiveMaximum
 * minimum
 * exclusiveMinimum
 * maxLength
 * minLength
 * pattern (This string SHOULD be a valid regular expression, according to the ECMA 262 regular expression dialect)
 * maxItems
 * minItems
 * uniqueItems
 * maxProperties
 * minProperties
 * enum
 * const
 * examples
 * if / then / else
 * readOnly
 * writeOnly
 * properties
 * patternProperties
 * additionalProperties
 * additionalItems
 * items
 * propertyNames
 * contains
 * allOf
 * oneOf
 * anyOf
 * not
 * The following properties are taken from the JSON Schema definition but their definitions were adjusted to the AsyncAPI Specification.
 *
 * description - CommonMark syntax can be used for rich text representation.
 * format - See Data Type Formats for further details. While relying on JSON Schema’s defined formats, the AsyncAPI Specification offers a few additional predefined formats.
 * default - The default value represents what would be assumed by the consumer of the input as the value of the schema if one is not provided. Unlike JSON Schema, the value MUST conform to the defined type for the Schema Object defined at the same level. For example, of type is string, then default can be "foo" but cannot be 1.
 * Alternatively, any time a Schema Object can be used, a Reference Object can be used in its place. This allows referencing definitions in place of defining them inline.
 *
 * In addition to the JSON Schema fields, the following AsyncAPI vocabulary fields MAY be used for further schema documentation:
 *
 * Fixed Fields
 *
 * todo This object can be extended with Specification Extensions.
 */
public class Aa20Schema extends LinkedHashMap<String, Object> implements Aa20OrReferenceType<Aa20Schema> {

    private String discriminator;
    private Aa20ExternalDocumentation externalDocs;
    private boolean deprecated;

    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * Adds support for polymorphism. The discriminator is the schema property
     * name that is used to differentiate between other schema that inherit
     * this schema. The property name used MUST be defined at this schema
     * and it MUST be in the required property list. When used, the value MUST
     * be the name of this schema or any schema that inherits it.
     * See Composition and Inheritance for more details.
     */
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public Aa20ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    /**
     * @param externalDocs Additional external documentation for this schema.
     */
    public void setExternalDocs(Aa20ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public Aa20ExternalDocumentation createExternalDocs(String url) {
        Aa20ExternalDocumentation externalDocs = new Aa20ExternalDocumentation(url);
        this.externalDocs = externalDocs;
        return externalDocs;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    /**
     * @param deprecated Specifies that a schema is deprecated and SHOULD be transitioned out of usage. Default value is false.
     */
    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }
}
