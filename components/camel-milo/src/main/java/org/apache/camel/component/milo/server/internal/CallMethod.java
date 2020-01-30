/*
 * Copyright (c) 2019 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.apache.camel.component.milo.server.internal;

import org.eclipse.milo.opcua.sdk.core.ValueRanks;
import org.eclipse.milo.opcua.sdk.server.api.methods.AbstractMethodInvocationHandler;
import org.eclipse.milo.opcua.sdk.server.nodes.UaMethodNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallMethod extends AbstractMethodInvocationHandler {

    public static final Argument IN = new Argument(
        "in",
        Identifiers.String,
        ValueRanks.Scalar,
        null,
        new LocalizedText("A value.")
    );

    public static final Argument OUT = new Argument(
        "out",
        Identifiers.String,
        ValueRanks.Scalar,
        null,
        new LocalizedText("A value.")
    );

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CallMethod(UaMethodNode node) {
        super(node);
    }

    @Override
    public Argument[] getInputArguments() {
        return new Argument[]{IN};
    }

    @Override
    public Argument[] getOutputArguments() {
        return new Argument[]{OUT};
    }

    @Override
    protected Variant[] invoke(InvocationContext invocationContext, Variant[] inputValues) {
        logger.debug("Invoking sqrt() method of objectId={}", invocationContext.getObjectId());

        String in = (String) inputValues[0].getValue();

        return new Variant[]{new Variant("out-"+in)};
    }

}
