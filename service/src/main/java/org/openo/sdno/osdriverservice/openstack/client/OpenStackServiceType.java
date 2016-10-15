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
 * Provides list of supported service types by this client.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 July 28, 2016
 */
public enum OpenStackServiceType {
    NETOWRK("network"), IDENTITY("identity");

    private String typeName;

    private OpenStackServiceType(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return this.typeName;
    }

    /**
     * Get enumeration by given type name.
     * <br>
     *
     * @param typeName
     * @return
     * @since  SDNO 0.5
     */
    public static OpenStackServiceType find(String typeName) {
        for(OpenStackServiceType type : OpenStackServiceType.values()) {
            if(type.getName().equals(typeName)) {
                return type;
            }
        }

        return null;
    }
}
