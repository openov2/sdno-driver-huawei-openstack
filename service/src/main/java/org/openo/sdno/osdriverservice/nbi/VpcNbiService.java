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

package org.openo.sdno.osdriverservice.nbi;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.sbi.VpcSbiService;
import org.openo.sdno.osdriverservice.sbi.model.OsSubnet;
import org.openo.sdno.osdriverservice.sbi.model.OsVpc;
import org.openo.sdno.osdriverservice.util.ControllerUtil;
import org.openo.sdno.osdriverservice.util.DaoUtil;
import org.openo.sdno.osdriverservice.util.MigrateModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for creating VPC service.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public class VpcNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcNbiService.class);

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    public VpcNbiService() {
        // Default Constructor
    }

    /**
     * To create VPC service.
     * <br>
     *
     * @param ctrlUuid
     * @param vpc
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public OsVpc createVpc(String ctrlUuid, OsVpc vpc) throws ServiceException {

        OsVpc vpcLocal = null;
        try {
            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);
            VpcSbiService vpcSrv = new VpcSbiService(client);
            vpcLocal = vpcSrv.createVpc(vpc);
        } catch(OpenStackException e) {
            LOGGER.error("Exception in create VPC", e);
            throw new ServiceException(e);
        } finally {
            if(vpcLocal != null) {
                for(OverlayUnderlayMapping mapping : MigrateModelUtil.convert(vpc.getAttributes().getResourceIds(),
                        vpcLocal.getAttributes().getResourceActions(), vpcLocal.getAttributes().getProjectId(), "VPC",
                        ctrlUuid, vpcLocal.getOverlayId())) {
                    if("c".equals(mapping.getAction()) || "u".equals(mapping.getAction())) {
                        DaoUtil.insert(mapping);
                    }
                }
            }
        }

        return vpcLocal;
    }

    /**
     * To delete VPC service.
     * <br>
     *
     * @param ctrlUuid
     * @param vpcId
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public void deleteVpc(String ctrlUuid, String vpcId) throws ServiceException {
        List<OverlayUnderlayMapping> mappings = DaoUtil.getChildren(OverlayUnderlayMapping.class, vpcId);
        OsVpc.Underlays underlays = MigrateModelUtil.convert(mappings);
        try {
            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);

            VpcSbiService vpcSrv = new VpcSbiService(client);
            vpcSrv.deleteVpc(underlays);
        } catch(OpenStackException e) {
            LOGGER.error("Exception in deleteVpc", e);
            throw new ServiceException(e);
        } finally {
            for(OverlayUnderlayMapping mapping : mappings) {
                DaoUtil.delete(OverlayUnderlayMapping.class, mapping.getUuid());
            }
        }
    }

    /**
     * To create Subnet.
     * <br>
     *
     * @param ctrlUuid
     * @param vpcId
     * @param subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public OsSubnet createSubnet(String ctrlUuid, String vpcId, OsSubnet subnet) throws ServiceException {

        OsSubnet subnetLocal = null;
        try {
            List<OverlayUnderlayMapping> mappings = DaoUtil.getChildren(OverlayUnderlayMapping.class, vpcId);

            OsVpc.Underlays underlays = MigrateModelUtil.convert(mappings);
            subnet.getAttributes().setRouterId(underlays.getRouterId(), "u");
            subnet.getAttributes().setProjectId(underlays.getProjectId(), "u");

            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);
            VpcSbiService vpcSrv = new VpcSbiService(client);
            subnetLocal = vpcSrv.createSubnet(subnet);
        } catch(OpenStackException e) {
            LOGGER.error("Exception in createSubnet", e);
            throw new ServiceException(e);
        } finally {
            if(subnetLocal != null) {
                for(OverlayUnderlayMapping mapping : MigrateModelUtil.convert(
                        subnetLocal.getAttributes().getResourceIds(), subnetLocal.getAttributes().getResourceActions(),
                        subnetLocal.getAttributes().getProjectId(), "SUBNET", ctrlUuid, subnetLocal.getOverlayId())) {
                    if("c".equals(mapping.getAction()) || "u".equals(mapping.getAction())) {
                        DaoUtil.insert(mapping);
                    }
                }
            }

        }

        return subnet;
    }

    /**
     * To delete Subnet.
     * <br>
     *
     * @param ctrlUuid
     * @param subnetId
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public void deleteSubnet(String ctrlUuid, String subnetId) throws ServiceException {
        List<OverlayUnderlayMapping> mappings = DaoUtil.getChildren(OverlayUnderlayMapping.class, subnetId);
        OsSubnet.Underlays underlays = MigrateModelUtil.convert1(mappings);
        try {
            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);

            VpcSbiService vpcSrv = new VpcSbiService(client);
            vpcSrv.deleteSubnet(underlays);
        } catch(OpenStackException e) {
            LOGGER.error("Exception in deleteSubnet", e);
            throw new ServiceException(e);
        } finally {
            for(OverlayUnderlayMapping mapping : mappings) {
                DaoUtil.delete(OverlayUnderlayMapping.class, mapping.getUuid());
            }
        }
    }

}
