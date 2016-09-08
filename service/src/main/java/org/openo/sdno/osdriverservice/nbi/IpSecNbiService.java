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

package org.openo.sdno.osdriverservice.nbi;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.sbi.IpSecSbiService;
import org.openo.sdno.osdriverservice.sbi.model.OsIpSec;
import org.openo.sdno.osdriverservice.util.ControllerUtil;
import org.openo.sdno.osdriverservice.util.DaoUtil;
import org.openo.sdno.osdriverservice.util.MigrateModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for creating IpSec service.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public class IpSecNbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpSecNbiService.class);

    public IpSecNbiService() {

    }

    /**
     * Create IpSec
     * @param ctrlUuid
     * @param ipsec
     * @return
     * @throws ServiceException
     */
    public OsIpSec createIpSec(
            String ctrlUuid,
            OsIpSec  ipsec) throws ServiceException {

        try {
            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);

            IpSecSbiService vpcSrv = new IpSecSbiService(client);
            ipsec = vpcSrv.createIpSec(ipsec);
        } catch(OpenStackException e) {
            throw new ServiceException(e);
        } finally {
            for (OverlayUnderlayMapping mapping: MigrateModelUtil.convert(
                    ipsec.getAttributes().getResourceIds(),
                    ipsec.getAttributes().getResourceActions(),
                    ipsec.getVpnIpSecSiteConnection().getTenantId(),
                    "IPSEC",
                    ctrlUuid,
                    ipsec.getOverlayId())) {
                if (("c").equals(mapping.getAction()) ||
                        ("u").equals(mapping.getAction()))
                    DaoUtil.insert(mapping);
            }
        }

        return ipsec;
    }

    /**
     * Delete IpSec
     * @param ctrlUuid
     * @param ipSecId
     * @throws ServiceException
     */
    public void deleteIpSec(
            String ctrlUuid,
            String ipSecId) throws ServiceException {
        List <OverlayUnderlayMapping> mappings = DaoUtil.getChildren(
                OverlayUnderlayMapping.class,
                ipSecId);
        OsIpSec.Underlays underlays = MigrateModelUtil.convert2(mappings);
        try {
            OpenStackClient client = ControllerUtil.createOpenStackClient(ctrlUuid);

            IpSecSbiService vpcSrv = new IpSecSbiService(client);
            vpcSrv.deleteIpSec(underlays);
        } catch(OpenStackException e) {
            throw new ServiceException(e);
        } finally {
            for (OverlayUnderlayMapping mapping: mappings) {
                String action = underlays.getResourceActions().get(mapping.getUnderlayId());
                if("d".equals(action)) {
                    DaoUtil.delete(OverlayUnderlayMapping.class, mapping.getUuid());
                }
            }
        }
    }
}