/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.osdriverservice.openstack.client.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Model class for router.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("router")
public class Router {

    private String id;

    @JsonProperty("tenant_id")
    private String projectId;

    /**
     * Router name
     */
    private String name;

    /**
     * External gateway info
     */
    @JsonProperty("external_gateway_info")
    private Router.ExternalGatewayInfo externalGatewayInfo;

    public Router.ExternalGatewayInfo getExternalGatewayInfo() {
        return this.externalGatewayInfo;
    }

    public void setExternalGatewayInfo(Router.ExternalGatewayInfo externalGatewayInfo) {
        this.externalGatewayInfo = externalGatewayInfo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * ExternalGatewayInfo class
     * <br>
     * <p>
     * </p>
     *
     * @author
     * @version SDNO 0.5 Aug 8, 2016
     */
    @JsonRootName("external_gateway_info")
    public static class ExternalGatewayInfo {

        /**
         * Network uuid
         */
        @JsonProperty("network_id")
        private String networkId;

        /**
         * Enable SNAT
         */
        @JsonProperty("enable_snat")
        private boolean enableSnat;

        /**
         * External Ips
         */
        @JsonProperty("external_fixed_ips")
        List<Map<String, String>> externalFixedIps;

        public List<Map<String, String>> getExternalFixedIps() {
            return this.externalFixedIps;
        }

        public void setExternalFixedIps(List<Map<String, String>> externalFixedIps) {
            this.externalFixedIps = externalFixedIps;
        }

        public String getNetworkId() {
            return this.networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public boolean isEnableSnat() {
            return this.enableSnat;
        }

        public void setEnableSnat(boolean enableSnat) {
            this.enableSnat = enableSnat;
        }

    }
}
