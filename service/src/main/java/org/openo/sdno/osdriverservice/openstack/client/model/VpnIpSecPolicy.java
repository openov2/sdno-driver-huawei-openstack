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
 * Model class for IpSec policy in adapter layer.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("ipsecpolicy")
public class VpnIpSecPolicy extends BaseDataModel {

    /**
     * Transform protocol.
     */
    @JsonProperty("transform_protocol")
    private String transformProtocol;

    /**
     * Encapsulation mode.
     */
    @JsonProperty("encapsulation_mode")
    private String encapsulationMode;

    /**
     * Hash algorithm.
     */
    @JsonProperty("auth_algorithm")
    private String authAlgorithm;

    /**
     * Encrypt algorithm.
     */
    @JsonProperty("encryption_algorithm")
    private String encryptionAlgorithm;

    /**
     * Perfect Forward Secrecy(group5, group2, group14).
     */
    private String pfs;

    private VpnPolicyLifeTime lifetime;

    public String getTransformProtocol() {
        return this.transformProtocol;
    }

    public void setTransformProtocol(String transformProtocol) {
        this.transformProtocol = transformProtocol;
    }

    public String getEncapsulationMode() {
        return this.encapsulationMode;
    }

    public void setEncapsulationMode(String encapsulationMode) {
        this.encapsulationMode = encapsulationMode;
    }

    public String getAuthAlgorithm() {
        return this.authAlgorithm;
    }

    public void setAuthAlgorithm(String authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    public String getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getPfs() {
        return this.pfs;
    }

    public void setPfs(String pfs) {
        this.pfs = pfs;
    }

    public VpnPolicyLifeTime getLifetime() {
        return this.lifetime;
    }

    public void setLifetime(VpnPolicyLifeTime lifetime) {
        this.lifetime = lifetime;
    }

}
