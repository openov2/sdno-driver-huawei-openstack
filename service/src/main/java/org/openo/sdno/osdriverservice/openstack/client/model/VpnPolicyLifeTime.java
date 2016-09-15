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

/**
 * Lifetime model class.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class VpnPolicyLifeTime {

    /**
     * Lifetime of the SA unit in 'seconds'.
     */
    private String units;

    /**
     * Lifetime value in seconds(value>=60).
     */
    private int value;

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
