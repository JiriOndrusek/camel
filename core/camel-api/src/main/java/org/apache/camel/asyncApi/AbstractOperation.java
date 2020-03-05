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
 */package org.apache.camel.asyncApi;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractOperation<T extends AbstractOperation> extends AbstractObject<T>{

    String operationId;

    public String getOperationId() {
        return operationId;
    }

    /**
     * Unique string used to identify the operation. The id MUST be unique
     * among all operations described in the API. The operationId value
     * is case-sensitive. Tools and libraries MAY use the operationId
     * to uniquely identify an operation, therefore, it is RECOMMENDED
     * to follow common programming naming conventions.
     */
    public T setOperationId(String operationId) {
        this.operationId = operationId;
        return (T)this;
    }


}
