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

package org.openo.sdno.osdriverservice.dao;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.osdriverservice.dao.inf.IControllerDao;
import org.openo.sdno.osdriverservice.dao.inf.IDao;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.util.ESRutil;
import org.openo.sdno.osdriverservice.util.OSDriverConfig;
import org.openo.sdno.overlayvpn.brs.invdao.CommParamDao;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.model.AuthInfo;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.inventory.sdk.util.InventoryDaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BRS DAO class<br>
 * <p>
 * </p>
 *
 * @param <T>
 * @author
 * @version SDNO 0.5 August 6, 2016
 */
public class DaoBrs<T> implements IDao<T>, IControllerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoBrs.class);

    /**
     * Constructor<br>
     * <p>
     * </p>
     *
     * @since SDNO 0.5
     */
    public DaoBrs() {
        // default constructor...
    }

    /**
     * Adds OverlayUnderlayMapping <br>
     *
     * @param entity
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public T insert(T entity) throws ServiceException {
        return new InventoryDaoUtil<T>().getInventoryDao().insert(entity).getData();
    }

    /**
     * Delete OverlayUnderlayMapping <br>
     *
     * @param clazz
     * @param uuid
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void delete(Class<T> clazz, String uuid) throws ServiceException {
        new InventoryDaoUtil<T>().getInventoryDao().delete(clazz, uuid);
    }

    /**
     * Get OverlayUnderlayMapping <br>
     *
     * @param clazz
     * @param uuid
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public T get(Class<T> clazz, String uuid) throws ServiceException {
        return new InventoryDaoUtil<T>().getInventoryDao().query(clazz, uuid, null).getData();
    }

    /**
     * Find children of given OverlayUnderlayMapping's overlay id <br>
     *
     * @param clazz
     * @param overlayId
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public List<T> getChildren(Class<T> clazz, String overlayId) throws ServiceException {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("overlayId", Arrays.asList(overlayId));

        return new InventoryDaoUtil<T>().getInventoryDao().batchQuery(clazz, JsonUtil.toJson(filter)).getData();
    }

    @Override
    public OpenStackCredentials getOpenStackCredentials(String ctrlUuid) throws ServiceException {

        OSDriverConfig config = new OSDriverConfig();
        if(config.isEsrEnabled()) {
            Map<String, Object> controllerMap = ESRutil.getControllerDetails(ctrlUuid);
            try {
                URL url = new URL((String)controllerMap.get("url"));
                return new OpenStackCredentials().setIp(url.getHost()).setPort(Integer.toString(url.getPort()))
                        .setUsername((String)controllerMap.get("userName"))
                        .setPassword((String)controllerMap.get("password"))
                        .setDomain((String)controllerMap.get("domain"))
                        .setSecured(url.getProtocol().equalsIgnoreCase("https"));
            } catch(Exception ex) {
                LOGGER.error("Error in getting controller", ex);
                throw new ServiceException("Error in getting controller", ex);
            }
        }
        ControllerMO controller = (new ControllerDao()).getController(ctrlUuid);

        List<CommParamMO> commPrarmList = (new CommParamDao()).getCommParam(controller.getObjectId());
        AuthInfo authInfo = commPrarmList.get(0).getAuthInfo();

        return new OpenStackCredentials().setIp(controller.getHostName()).setPort(authInfo.getPort())
                .setUsername(authInfo.getUserName()).setPassword(authInfo.getPassword())
                .setDomain(authInfo.getExtra().get("domain"));
    }

    @Override
    public String getOpenStackRegion(String ctrlUuid) throws ServiceException {
        OSDriverConfig config = new OSDriverConfig();
        if(config.isEsrEnabled()) {
            Map<String, Object> controllerMap = ESRutil.getControllerDetails(ctrlUuid);
            //TODO(mrkanag) Remove the hard-coding if region, once ESR allows to have region as configurable
            return controllerMap.get("region") == null ? "regionOne" : (String)controllerMap.get("region");
        }
        ControllerMO controller = (new ControllerDao()).getController(ctrlUuid);

        List<CommParamMO> commPrarmList = (new CommParamDao()).getCommParam(controller.getObjectId());
        AuthInfo authInfo = commPrarmList.get(0).getAuthInfo();

        return authInfo.getExtra().get("region");
    }
}
