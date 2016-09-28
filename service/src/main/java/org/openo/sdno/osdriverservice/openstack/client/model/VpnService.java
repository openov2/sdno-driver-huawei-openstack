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
 * Model class for VPN service in adapter layer.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("vpnservice")
public class VpnService extends BaseDataModel {

    @JsonProperty("subnet_id")
    private String subnetId;

    @JsonProperty("router_id")
    private String routerId;

    /**
     * ACTIVE/DOWN/PENDING_CREATE/ERROR.
     */
    private String status;

    /**
     * Vpnservice admin state, it doesn't transfer data if the state is false.
     */
    @JsonProperty("admin_state_up")
    private boolean adminStateUp;

    public String getSubnetId() {
        return this.subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getRouterId() {
        return this.routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAdminStateUp() {
        return this.adminStateUp;
    }

    public void setAdminStateUp(boolean state) {
        this.adminStateUp = state;
    }
}
