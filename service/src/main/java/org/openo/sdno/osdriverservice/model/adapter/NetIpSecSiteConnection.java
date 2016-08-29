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

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.sdno.osdriverservice.model.BaseDataModel;

/**
 * Model class for connection between IpSec and site in adapter layer.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class NetIpSecSiteConnection extends BaseDataModel {

    /**
     * Peer vpn address or FQDN.
     */
    @JsonProperty("peer_address")
    private String peerAddress;

    @JsonProperty("peer_id")
    private String peerId;

    @JsonProperty("peer_cidrs")
    Map<String, List<String>> peerCidrs;

    /**
     * Routing mode either 'static' or 'dynamic' - for first release only 'static' supported.
     */
    @JsonProperty("route_mode")
    private String routeMode;

    /**
     * psk or certificate.
     */
    @JsonProperty("auth_mode")
    private String authMode;

    /**
     * MTU(value>=68).
     */
    private int mtu;

    /**
     * Vpn connection admin state.
     */
    @JsonProperty("admin_state_up")
    private boolean adminStateUp = false;

    /**
     * Pre-shared-key.
     */
    private String psk;

    /**
     * Whether this vpn can only respond to connections or can initiate an well.
     */
    private String initiator;

    /**
     * ACTIVE, DOWN, PENDING_CREATE,ERROR
     */
    private String status;

    @JsonProperty("ikepolicy_id")
    private String ikepolicyId;

    @JsonProperty("ipsecpolicy_id")
    private String ipsecpolicyId;

    @JsonProperty("vpnservice_id")
    private String vpnserviceId;

    private Dpd dpd;

    private String subnets;

    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public Map<String, List<String>> getPeerCidrs() {
        return peerCidrs;
    }

    public void setPeerCidrs(Map<String, List<String>> peerCidrs) {
        this.peerCidrs = peerCidrs;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public String getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(String routeMode) {
        this.routeMode = routeMode;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIkepolicyId() {
        return ikepolicyId;
    }

    public void setIkepolicyId(String ikepolicyId) {
        this.ikepolicyId = ikepolicyId;
    }

    public String getIpsecpolicyId() {
        return ipsecpolicyId;
    }

    public void setIpsecpolicyId(String ipsecpolicyId) {
        this.ipsecpolicyId = ipsecpolicyId;
    }

    public String getVpnserviceId() {
        return vpnserviceId;
    }

    public void setVpnserviceId(String vpnserviceId) {
        this.vpnserviceId = vpnserviceId;
    }

    public Dpd getDpd() {
        return dpd;
    }

    public void setDpd(Dpd dpd) {
        this.dpd = dpd;
    }

    public String getSubnets() {
        return subnets;
    }

    public void setSubnets(String subnets) {
        this.subnets = subnets;
    }

    public void setAdminStateUp(boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

}
