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

package org.openo.sdno.osdriverservice.openstack.client;

/**
 * Helps to enum OpenStack REST API version.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  Jul 31, 2016
 */
public enum OpenStackServiceApiVersion {
    V2("v2.0"), V3("v3");

    private String version;

    private OpenStackServiceApiVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}