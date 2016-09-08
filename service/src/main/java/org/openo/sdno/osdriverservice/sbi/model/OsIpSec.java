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

package org.openo.sdno.osdriverservice.sbi.model;

import org.openo.sdno.osdriverservice.openstack.client.model.VpnIkePolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecPolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecSiteConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnService;

/**
 * Model class for IpSec.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class OsIpSec {

    private String overlayId;

    private VpnIkePolicy vpnIkePolicy;

    private VpnIpSecPolicy vpnIpSecPolicy;

    private VpnIpSecSiteConnection vpnIpSecSiteConnection;

    private VpnService vpnService;

    private OsIpSec.Underlays attributes = new OsIpSec.Underlays();

    public VpnIkePolicy getVpnIkePolicy() {
        return this.vpnIkePolicy;
    }

    public void setVpnIkePolicy(VpnIkePolicy vpnIkePolicy) {
        this.vpnIkePolicy = vpnIkePolicy;
    }

    public VpnIpSecPolicy getVpnIpSecPolicy() {
        return this.vpnIpSecPolicy;
    }

    public void setVpnIpSecPolicy(VpnIpSecPolicy vpnIpSecPolicy) {
        this.vpnIpSecPolicy = vpnIpSecPolicy;
    }

    public VpnService getVpnService() {
        return this.vpnService;
    }

    public void setVpnService(VpnService vpnService) {
        this.vpnService = vpnService;
    }

    public VpnIpSecSiteConnection getVpnIpSecSiteConnection() {
        return this.vpnIpSecSiteConnection;
    }

    public void setVpnIpSecSiteConnection(VpnIpSecSiteConnection vpnIpSecSiteConnection) {
        this.vpnIpSecSiteConnection = vpnIpSecSiteConnection;
    }


    public OsIpSec.Underlays getAttributes() {
        return this.attributes;
    }

    public void setAttributes(OsIpSec.Underlays attributes) {
        this.attributes = attributes;
    }


    public String getOverlayId() {
        return this.overlayId;
    }

    public void setOverlayId(String overlayId) {
        this.overlayId = overlayId;
    }


    /**
     * Underlays class
     * <br>
     * <p>
     * </p>
     *
     * @author
     * @version     SDNO 0.5  Aug 8, 2016
     */
    public static class Underlays extends BaseUnderlays{

        private String vpnServiceId = null;

        private String vpnIkePolicyId = null;

        private String vpnIpSecPolicyId = null;

        private String vpnIpSecSiteConnectionId = null;

        public String getVpnServiceId() {
            return this.vpnServiceId;
        }

        /**
         * Set vpn service ID
         * <br>
         *
         * @param vpnServiceId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpnServiceId(String vpnServiceId, String action) {
            this.vpnServiceId = vpnServiceId;
            this.put("vpnServiceId", vpnServiceId, action);
        }


        public String getVpnIkePolicyId() {
            return this.vpnIkePolicyId;
        }

        /**
         * Set vpn ike policy ID
         * <br>
         *
         * @param vpnIkePolicyId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpnIkePolicyId(String vpnIkePolicyId, String action) {
            this.vpnIkePolicyId = vpnIkePolicyId;
            this.put("vpnIkePolicyId", vpnIkePolicyId, action);
        }


        public String getVpnIpSecPolicyId() {
            return this.vpnIpSecPolicyId;
        }

        /**
         * Set vpn ipsec policy ID
         * <br>
         *
         * @param vpnIpSecPolicyId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpnIpSecPolicyId(String vpnIpSecPolicyId, String action) {
            this.vpnIpSecPolicyId = vpnIpSecPolicyId;
            this.put("vpnIpSecPolicyId", vpnIpSecPolicyId, action);
        }


        public String getVpnIpSecSiteConnectionId() {
            return this.vpnIpSecSiteConnectionId;
        }

        /**
         * Set vpn ip sec site connection ID
         * <br>
         *
         * @param vpnIpSecSiteConnectionId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpnIpSecSiteConnectionId(String vpnIpSecSiteConnectionId, String action) {
            this.vpnIpSecSiteConnectionId = vpnIpSecSiteConnectionId;
            this.put("vpnIpSecSiteConnectionId", vpnIpSecSiteConnectionId, action);
        }
    }
}
