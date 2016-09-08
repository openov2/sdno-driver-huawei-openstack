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
 * Model class for OsVpc.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class OsVpc {

    private String overlayId;

    private String domainName;

    private String projectName;

    private String gatewayIp;

    private String cidr;

    //TODO(mrkanag) is public network cidr required.

    private OsVpc.Underlays attributes = new OsVpc.Underlays();


    public String getGatewayIp() {
        return this.gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getDomainName() {
        return this.domainName;
    }


    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }



    public String getProjectName() {
        return this.projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getCidr() {
        return this.cidr;
    }


    public void setCidr(String cidr) {
        this.cidr = cidr;
    }


    public OsVpc.Underlays getAttributes() {
        return this.attributes;
    }


    public void setAttributes(OsVpc.Underlays attributes) {
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
    public static class Underlays extends BaseUnderlays {

        private String projectId;

        private String publicNetworkId;

        private String routerId;

        public String getProjectId() {
            return this.projectId;
        }

        /**
         * Set project ID
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


        public String getPublicNetworkId() {
            return this.publicNetworkId;
        }

        /**
         * Set public network ID
         * <br>
         *
         * @param publicNetworkId
         * @param action
         * @since  SDNO 0.5
         */
        public void setPublicNetworkId(String publicNetworkId, String action) {
            this.publicNetworkId = publicNetworkId;
            this.put("publicNetworkId", publicNetworkId, action);
        }


        public String getRouterId() {
            return this.routerId;
        }

        /**
         * Set router ID
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
    }
}
