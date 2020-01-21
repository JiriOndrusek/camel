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
package org.apache.camel.component.milo.server.internal;

import org.apache.camel.component.milo.client.MiloClientConsumer;
import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.ManagedNamespace;

import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.api.NodeManager;
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.sdk.server.nodes.UaObjectNode;
import org.eclipse.milo.opcua.sdk.server.nodes.factories.NodeFactory;
import org.eclipse.milo.opcua.sdk.server.util.SubscriptionModel;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamelNamespace extends ManagedNamespace {

    private static final Logger LOG = LoggerFactory.getLogger(MiloClientConsumer.class);

    private final SubscriptionModel subscriptionModel;

    private final Map<String, CamelServerItem> itemMap = new HashMap<>();

    private UaNodeContext nodeContext;

    private UaObjectNode itemsNode;

    public CamelNamespace(OpcUaServer server, String namespaceUri) {
        super(server, namespaceUri);

        this.subscriptionModel = new SubscriptionModel(server, this);
    }

    protected UaNodeContext getNodeContext() {
        if(nodeContext == null) {

            nodeContext = new UaNodeContext() {
                @Override
                public OpcUaServer getServer() {
                    return getServer();
                }

                @Override
                public NodeManager<UaNode> getNodeManager() {
                    return getNodeManager();
                }
            };
        }
        return nodeContext;
    };

    @Override
    protected void onStartup() {
        super.onStartup();

        // Create a "HelloWorld" folder and add it to the node manager
        NodeId folderNodeId = newNodeId("camel");

        UaFolderNode folderNode = new UaFolderNode(
                getNodeContext(),
                folderNodeId,
                newQualifiedName("camel"),
                LocalizedText.english("Camel")
        );

        getNodeManager().addNode(folderNode);

        //todo ??
        // Make sure our new folder shows up under the server's Objects folder.
        folderNode.addReference(new Reference(
                folderNode.getNodeId(),
                Identifiers.Organizes,
                Identifiers.ObjectsFolder.expanded(),
                false
        ));

        itemsNode = new UaObjectNode(
                getNodeContext(),
                newNodeId("items"),
                newQualifiedName("items"),
                LocalizedText.english("Items")
        );

        getNodeManager().addNode(itemsNode);
        folderNode.addOrganizes(itemsNode);
    }

    @Override
    public void onDataItemsCreated(List<DataItem> list) {
        this.subscriptionModel.onDataItemsCreated(list);
    }

    @Override
    public void onDataItemsModified(List<DataItem> list) {
        this.subscriptionModel.onDataItemsModified(list);
    }

    @Override
    public void onDataItemsDeleted(List<DataItem> list) {
        this.subscriptionModel.onDataItemsDeleted(list);
    }

    @Override
    public void onMonitoringModeChanged(List<MonitoredItem> list) {
        this.subscriptionModel.onMonitoringModeChanged(list);
    }

    public CamelServerItem getOrAddItem(final String itemId) {
        synchronized (this) {
            CamelServerItem item = this.itemMap.get(itemId);
            if (item == null) {
                item = new CamelServerItem(itemId, getNodeContext(), getNamespaceIndex(), this.itemsNode);
                this.itemMap.put(itemId, item);
            }
            return item;
        }
    }

}
