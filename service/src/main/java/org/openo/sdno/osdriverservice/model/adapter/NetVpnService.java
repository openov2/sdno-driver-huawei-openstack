/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.osdriverservice.model.adapter;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.sdno.osdriverservice.model.BaseDataModel;

/**
 * Model class for VPN service in adapter layer.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class NetVpnService extends BaseDataModel {

    @JsonProperty("router_id")
    private String routerId;

    @JsonProperty("subnet_id")
    private String subnetId;

    /**
     * ACTIVE/DOWN/PENDING_CREATE/ERROR.
     */
    private String status;

    /**
     * Vpnservice admin state, it doesn’t transfer data if the state is false.
     */
    @JsonProperty("admin_state_up")
    private boolean adminStateUp = false;

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }
}
