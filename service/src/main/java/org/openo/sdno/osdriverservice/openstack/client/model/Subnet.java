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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Model class for subnet.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("subnet")
public class Subnet {

    private String id;

    /**
     * Subnet name
     */
    private String name;

    /**
     * Network uuid.
     */
    @JsonProperty("network_id")
    private String networkId;

    @JsonProperty("tenant_id")
    private String projectId;

    /**
     * CIDR
     */
    private String cidr;

    /**
     * IP version. supported only v4
     */
    @JsonProperty("ip_version")
    private String ipVersion = "4";

    @JsonProperty("gateway_ip")
    private String gatewayIp;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGatewayIp() {
        return this.gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getNetworkId() {
        return this.networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getCidr() {
        return this.cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpVersion() {
        return this.ipVersion;
    }

    public String setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
        return this.ipVersion;
    }
}
