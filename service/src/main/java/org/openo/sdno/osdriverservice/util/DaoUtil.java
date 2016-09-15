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

package org.openo.sdno.osdriverservice.util;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.DaoInMemory;
import org.openo.sdno.osdriverservice.dao.inf.IDao;

/**
 *
 * DAO Util class<br>
 * <p>
 * </p>
 *
 * @param <T>
 * @author
 * @version     SDNO 0.5  Aug 6, 2016
 */
public class DaoUtil<T> {

    private static IDao dao = new DaoInMemory();

    public static IDao getDao() {
        return dao;
    }

    public static void setDao(IDao dao) {
        DaoUtil.dao = dao;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     *
     * @since SDNO 0.5
     */
    private DaoUtil() {
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
    public static <T> T  insert(T entity) throws ServiceException {
        return (T) dao.insert(entity);
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
    public static <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
        dao.delete(clazz, uuid);
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
    public static <T> T get(Class<T> clazz, String uuid) throws ServiceException {
        return (T) dao.get(clazz, uuid);
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
    public static <T> List<T> getChildren(Class<T> clazz, String overlayId)
            throws ServiceException {
        return dao.getChildren(clazz, overlayId);
    }
}
