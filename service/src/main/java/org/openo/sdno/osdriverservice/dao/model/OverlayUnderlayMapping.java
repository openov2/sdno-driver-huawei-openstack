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

package org.openo.sdno.osdriverservice.dao.model;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Overlay to Underlay resource mapping.
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 July 31, 2016
 */
@MOResType(infoModelName = "osdriverunderlays")
public class OverlayUnderlayMapping extends AbstUuidModel {

    /**
     * Overlay UUID such as VPC, Subnet, etc.
     */
    @AString(require = true)
    private String overlayId;

    /**
     * Overlay resource type such as VPC, SUBNET, etc.
     */
    @AString(require = true)
    private String overlayType;

    /**
     * OpenStack Controller UUID.
     */
    @AString(require = true)
    private String controllerId;

    /**
     * OpenStack resource UUID, which is created, used or deleted
     * as part of Overlay resource.
     */
    @AString(require = true)
    private String underlayId;

    /**
     * OpenStack resource type such as networkId, routerId, etc.
     */
    @AString(require = true)
    private String underlayType;

    /**
     * It could be one of "c" for created, "u" for used
     * and "d" for deleted as part of Overlay resource provision
     * or de-provision.
     */
    @AString(require = true)
    private String action;

    // TODO(mrkanag) Add underlay URL to the underlayId

    /**
     * OpenStack tenant UUID
     */
    private String underlayTenantId;

    /**
     *
     * <br>
     *
     * @param string
     * @return
     * @since   SDNO 0.5
     */
    @Override
    public boolean equals(Object string){
        //TODO(mrkanag) Fix me, when its required, now its helps to fix static error !
        return true;
    }

    /**
     *
     * <br>
     *
     * @return
     * @since   SDNO 0.5
     */
    @Override
    public int hashCode(){
        //TODO(mrkanag) Fix me, when its required, now its helps to fix static error !
        return 1;
    }

    public String getOverlayId() {
        return this.overlayId;
    }


    public void setOverlayId(String overlayId) {
        this.overlayId = overlayId;
    }


    public String getOverlayType() {
        return this.overlayType;
    }


    public void setOverlayType(String overlayType) {
        this.overlayType = overlayType;
    }


    public String getControllerId() {
        return this.controllerId;
    }


    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }


    public String getUnderlayId() {
        return this.underlayId;
    }


    public void setUnderlayId(String underlayId) {
        this.underlayId = underlayId;
    }


    public String getUnderlayType() {
        return this.underlayType;
    }


    public void setUnderlayType(String underlayType) {
        this.underlayType = underlayType;
    }


    public String getUnderlayTenantId() {
        return this.underlayTenantId;
    }


    public void setUnderlayTenantId(String underlayTenantId) {
        this.underlayTenantId = underlayTenantId;
    }


    public String getAction() {
        return this.action;
    }


    public void setAction(String action) {
        this.action = action;
    }
}
