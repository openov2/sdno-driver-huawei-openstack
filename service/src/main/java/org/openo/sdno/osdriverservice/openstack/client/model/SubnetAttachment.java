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

/**
 * Model class for Subnet attachment with router.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class SubnetAttachment {
    /**
     * Subnet UUID.
     */
    @JsonProperty("subnet_id")
    private String subnetId;

    public String getSubnetId() {
        return this.subnetId;
    }

    public SubnetAttachment setSubnetId(String subnetId) {
        this.subnetId = subnetId;
        return this;
    }

}
