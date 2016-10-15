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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.inf.IControllerDao;
import org.openo.sdno.osdriverservice.dao.inf.IDao;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.util.OSDriverConfig;

/**
 *
 * DAO In Memory class<br>
 * <p>
 * </p>
 *
 * @param <T>
 * @author
 * @version SDNO 0.5 August 6, 2016
 */
public class DaoInMemory<T> implements IDao<T>, IControllerDao{

    private Map<String, OverlayUnderlayMapping> cache = new HashMap<>();
    private OSDriverConfig config = new OSDriverConfig();

    /**
     * Constructor<br>
     * <p>
     * </p>
     *
     * @since SDNO 0.5
     */
    public DaoInMemory() {
        // default constructor...
    }

    /**
     * Adds OverlayUnderlayMapping
     * <br>
     *
     * @param entity
     * @return
     * @throws ServiceException
     * @since  SDNO 0.5
     */
    public T insert(T entity) throws ServiceException {
        String uuid = UUID.randomUUID().toString();
        ((OverlayUnderlayMapping)entity).setUuid(uuid);
        cache.put(uuid, (OverlayUnderlayMapping)entity);
        return entity;
    }

    /**
     * Delete OverlayUnderlayMapping
     * <br>
     *
     * @param clazz
     * @param uuid
     * @throws ServiceException
     * @since  SDNO 0.5
     */
    public void delete(Class<T> clazz, String uuid) throws ServiceException {
        cache.remove(uuid);
    }

    /**
     * Get OverlayUnderlayMapping
     * <br>
     *
     * @param clazz
     * @param uuid
     * @return
     * @throws ServiceException
     * @since  SDNO 0.5
     */
    public T get(Class<T> clazz, String uuid) throws ServiceException {
        return (T) cache.get(uuid);
    }

    /**
     * Find children of given OverlayUnderlayMapping's overlay id
     * <br>
     *
     * @param clazz
     * @param overlayId
     * @return
     * @throws ServiceException
     * @since  SDNO 0.5
     */
    public List<T> getChildren(Class<T> clazz, String overlayId)
            throws ServiceException {
        List<T> list = new ArrayList<>();
        for(Entry<String, OverlayUnderlayMapping> e : cache.entrySet()) {
            if(e.getValue().getOverlayId().equals(overlayId)) {
                list.add((T) e.getValue());
            }
        }

        return list;
    }

    @Override
    public OpenStackCredentials getOpenStackCredentials(String ctrlUuid) throws ServiceException {
        return new OpenStackCredentials().setDomain(
                config.getOSDomainName()).setIp(config.getOSIpAddress()).setPort(
                        config.getOSPort()).setPassword(
                        config.getOSUserName()).setUsername(config.getOSPassword());
    }

    @Override
    public String getOpenStackRegion(String ctrlUuid) throws ServiceException {
        return config.getOSRegionName();
    }
}

