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
 * Helps to capture the OpenStack REST API version
 * and type, and is used in all OpenStackClient
 * java api to provide the right context for the API.
 * In addition, it captures the OpenStack region name.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  Jul 31, 2016
 */
public class OpenStackServiceContext {
    private OpenStackServiceType serviceType;
    private OpenStackServiceApiVersion version;
    private String region;

    public OpenStackServiceType getServiceType() {
        return this.serviceType;
    }

    public OpenStackServiceContext setServiceType(OpenStackServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public OpenStackServiceApiVersion getVersion() {
        return this.version;
    }

    public OpenStackServiceContext setVersion(OpenStackServiceApiVersion version) {
        this.version = version;
        return this;
    }


    public String getRegion() {
        return this.region;
    }


    public OpenStackServiceContext setRegion(String region) {
        this.region = region;
        return this;
    }

}