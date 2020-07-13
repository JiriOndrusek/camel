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
package org.apache.camel.utils.cassandra;


import com.datastax.oss.driver.api.core.loadbalancing.LoadBalancingPolicy;
import com.datastax.oss.driver.internal.core.loadbalancing.DefaultLoadBalancingPolicy;

public class CassandraLoadBalancingPolicies {

    public final String roundRobinPolicy = "RoundRobinPolicy";
    public final String tokenAwarePolicy = "TokenAwarePolicy";
    public final String dcAwareRoundRobinPolicy = "DcAwareRoundRobinPolicy";
    public final String latencyAwarePolicy = "LatencyAwarePolicy";
    public final String errorAwarePolicy = "ErrorAwarePolicy";

    public LoadBalancingPolicy getLoadBalancingPolicy(String policy) {
        // todo jondruse
        //Load balancing policy
        //Previous driver versions came with multiple load balancing policies that could be nested into each other. In our experience, this was one of the most complicated aspects of the configuration.
        //
        //In driver 4, we are taking a more opinionated approach: we provide a single default policy, with what we consider as the best practices:
        //
        //local only: we believe that failover should be handled at infrastructure level, not by application code.
        //token-aware.
        //optionally filtering nodes with a custom predicate.
        //You can still provide your own policy by implementing the LoadBalancingPolicy interface.
        LoadBalancingPolicy loadBalancingPolicy = new DefaultLoadBalancingPolicy(null, policy);
//        LoadBalancingPolicy loadBalancingPolicy = new RoundRobinPolicy();
//        switch (policy) {
//            case roundRobinPolicy:
//                loadBalancingPolicy = new RoundRobinPolicy();
//                break;
//            case tokenAwarePolicy:
//                loadBalancingPolicy = new TokenAwarePolicy(new RoundRobinPolicy());
//                break;
//            case dcAwareRoundRobinPolicy:
//                loadBalancingPolicy = DCAwareRoundRobinPolicy.builder().build();
//                break;
//            case latencyAwarePolicy:
//                loadBalancingPolicy = LatencyAwarePolicy.builder(new RoundRobinPolicy()).build();
//                break;
//            case errorAwarePolicy:
//                loadBalancingPolicy = ErrorAwarePolicy.builder(new RoundRobinPolicy()).build();
//                break;
//            default:
//                throw new IllegalArgumentException("Cassandra load balancing policy can be " + roundRobinPolicy + " ," + tokenAwarePolicy + " ," + dcAwareRoundRobinPolicy);
//        }
        return loadBalancingPolicy;
    }
}
