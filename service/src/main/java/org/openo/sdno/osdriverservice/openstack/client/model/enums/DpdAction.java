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
 * Enum class of dpd action.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-21
 */
public enum DpdAction {

    CLEAR(0), HOLD(1), RESTART(2), DISABLED(3), RESTART_BY_PEER(4);

    private int value;

    DpdAction(int value) {
        this.value = value;
    }

    public String getName() {
        String[] action = { "clear", "hold", "restart", "disabled", "restart-by-peer"};
        if(this.value >= 0 && this.value <= 4){
            return action[this.value];
         }
        return "";
    }
}
