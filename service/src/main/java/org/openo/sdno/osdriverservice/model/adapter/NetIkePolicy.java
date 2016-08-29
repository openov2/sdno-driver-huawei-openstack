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
 * Model class for IKE policy in adapter layer.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class NetIkePolicy extends BaseDataModel {

    /**
     * Encrypt algorithm.
     */
    @JsonProperty("encryption_algorithm")
    private String encryptionAlgorithm;

    /**
     * Hash algorithm.
     */
    @JsonProperty("auth_algorithm")
    private String authAlgorithm;

    /**
     * IKE mode main mode, only support main now.
     */
    @JsonProperty("phase1_negotiation_mode")
    private String phase1NegotiationMode;

    /**
     * Perfect Forward Secrecy(group5, group2, group14).
     */
    private String pfs;

    /**
     * v1 or v2 version.
     */
    @JsonProperty("ike_version")
    private String ikeVersion;

    private LifeTime lifetime;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public NetIkePolicy() {
        super();
    }

    public String getAuthAlgorithm() {
        return authAlgorithm;
    }

    public void setAuthAlgorithm(String authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getPhase1NegotiationMode() {
        return phase1NegotiationMode;
    }

    public void setPhase1NegotiationMode(String phase1NegotiationMode) {
        this.phase1NegotiationMode = phase1NegotiationMode;
    }

    public String getPfs() {
        return pfs;
    }

    public void setPfs(String pfs) {
        this.pfs = pfs;
    }

    public String getIkeVersion() {
        return ikeVersion;
    }

    public void setIkeVersion(String ikeVersion) {
        this.ikeVersion = ikeVersion;
    }

    public LifeTime getLifetime() {
        return lifetime;
    }

    public void setLifetime(LifeTime lifetime) {
        this.lifetime = lifetime;
    }
}
