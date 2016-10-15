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

package org.openo.sdno.osdriverservice.openstack.client.model.enums;

/**
 * Enumeration class of IpSec and VPN status.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public enum IpSecVpnStatus {

    ACTIVE(0), DOWN(1), PENDING_CREATE(2), ERROR(3);

    private int value;

    IpSecVpnStatus(int value) {
        this.value = value;
    }

    public String getName() {
        switch(value) {
            case 0:
                return "ACTIVE";
            case 1:
                return "DOWN";
            case 2:
                return "PENDING_CREATE";
            case 3:
                return "ERROR";
            default:
                return "";
        }
    }
}
