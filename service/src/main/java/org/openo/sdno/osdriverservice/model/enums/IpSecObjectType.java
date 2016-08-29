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

package org.openo.sdno.osdriverservice.model.enums;

/**
 * Enum class of object type in IpSec.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public enum IpSecObjectType {

    IKE_POLICY(0), IPSEC_POLICY(1), VPN_SERVICE(2), IPSEC_SITE_CONNECTION(3);

    private int value;

    IpSecObjectType(int value) {
        this.value = value;
    }

    public String getName() {
        switch(this.value) {
            case 0:
                return "IkePolicy";
            case 1:
                return "IpSecPolicy";
            case 2:
                return "VpnService";
            case 3:
                return "IpSecSiteConnection";
            default:
                return "";
        }
    }
}
