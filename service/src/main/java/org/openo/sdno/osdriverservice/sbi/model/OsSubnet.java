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

/**
 * Model class for OsSubnet.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class OsSubnet {

    private String overlayId;

    private String cidr;

    private String gatewayIp;

    private String adminStatus;

    private OsSubnet.Underlays attributes = new OsSubnet.Underlays();

    public String getCidr() {
        return this.cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public OsSubnet.Underlays getAttributes() {
        return this.attributes;
    }


    public void setAttributes(OsSubnet.Underlays attributes) {
        this.attributes = attributes;
    }


    public String getOverlayId() {
        return this.overlayId;
    }

    public void setOverlayId(String overlayId) {
        this.overlayId = overlayId;
    }

    public String getGatewayIp() {
        return this.gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getAdminStatus() {
        return this.adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    /**
     * Subnet Undrelays
     * <br>
     * <p>
     * </p>
     *
     * @author
     * @version     SDNO 0.5  Aug 6, 2016
     */
    public static class Underlays extends BaseUnderlays{

        private String vpcNetworkId;

        private String vpcSubnetId;

        private String routerId;

        private String projectId;

        public String getProjectId() {
            return this.projectId;
        }

        /**
         * Sets project id
         * <br>
         *
         * @param projectId
         * @param action
         * @since  SDNO 0.5
         */
        public void setProjectId(String projectId, String action) {
            this.put("projectId", projectId, action);
            this.projectId = projectId;
        }


        public String getRouterId() {
            return this.routerId;
        }

        /**
         * Set router id
         * <br>
         *
         * @param routerId
         * @param action
         * @since  SDNO 0.5
         */

        public void setRouterId(String routerId, String action) {
            this.routerId = routerId;
            this.put("routerId", routerId, action);
        }
        public String getVpcNetworkId() {
            return this.vpcNetworkId;
        }


        /**
         * Set VPC network id
         * <br>
         *
         * @param vpcNetworkId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpcNetworkId(String vpcNetworkId, String action) {
            this.vpcNetworkId = vpcNetworkId;
            this.put("networkId", vpcNetworkId, action);
        }


        public String getVpcSubnetId() {
            return this.vpcSubnetId;
        }

        /**
         * Sets subnet id
         * <br>
         *
         * @param vpcSubnetId
         * @param action
         * @since  SDNO 0.5
         */
        public void setVpcSubnetId(String vpcSubnetId, String action) {
            this.vpcSubnetId = vpcSubnetId;
            this.put("subnetId", vpcSubnetId, action);
        }
    }
}
