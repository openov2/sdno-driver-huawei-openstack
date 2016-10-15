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

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Model class for connection between IpSec and site in adapter layer.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("ipsec_site_connection")
public class VpnIpSecSiteConnection extends BaseDataModel {

    /**
     * Peer VPN address or FQDN.
     */
    @JsonProperty("peer_address")
    private String peerAddress;

    @JsonProperty("peer_id")
    private String peerId;

    @JsonProperty("peer_cidrs")
    List<String> peerCidrs;

    /**
     * Routing mode either 'static' or 'dynamic' - for first release only 'static' supported.
     */
    @JsonProperty("route_mode")
    private String routeMode;

    /**
     * MTU(value>=68).
     */
    private int mtu;

    /**
     * PSK or certificate.
     */
    @JsonProperty("auth_mode")
    private String authMode;

    /**
     * Pre-shared-key.
     */
    private String psk;

    /**
     * Whether this VPN can only respond to connections or can initiate an well.
     */
    private String initiator;

    /**
     * VPN connection administrator state.
     */
    @JsonProperty("admin_state_up")
    private boolean adminStateUp;

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

    private VpnDpd dpd;

    private String subnets;

    public String getPeerAddress() {
        return this.peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public String getPeerId() {
        return this.peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public List<String> getPeerCidrs() {
        return this.peerCidrs;
    }

    public void setPeerCidrs(List<String> peerCidrs) {
        this.peerCidrs = peerCidrs;
    }

    public String getRouteMode() {
        return this.routeMode;
    }

    public void setRouteMode(String routeMode) {
        this.routeMode = routeMode;
    }

    public int getMtu() {
        return this.mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public String getAuthMode() {
        return this.authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getPsk() {
        return this.psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getInitiator() {
        return this.initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public boolean isAdminStateUp() {
        return this.adminStateUp;
    }

    public void setAdminStateUp(boolean state) {
        this.adminStateUp = state;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIkepolicyId() {
        return this.ikepolicyId;
    }

    public void setIkepolicyId(String ikepolicyId) {
        this.ikepolicyId = ikepolicyId;
    }

    public String getIpsecpolicyId() {
        return this.ipsecpolicyId;
    }

    public void setIpsecpolicyId(String ipsecpolicyId) {
        this.ipsecpolicyId = ipsecpolicyId;
    }

    public String getVpnserviceId() {
        return this.vpnserviceId;
    }

    public void setVpnserviceId(String vpnserviceId) {
        this.vpnserviceId = vpnserviceId;
    }

    public VpnDpd getDpd() {
        return this.dpd;
    }

    public void setDpd(VpnDpd dpd) {
        this.dpd = dpd;
    }

    public String getSubnets() {
        return this.subnets;
    }

    public void setSubnets(String subnets) {
        this.subnets = subnets;
    }

}
