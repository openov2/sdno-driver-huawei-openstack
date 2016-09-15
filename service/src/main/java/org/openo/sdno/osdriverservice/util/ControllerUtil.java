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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.DaoInMemory;
import org.openo.sdno.osdriverservice.dao.inf.IControllerDao;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;

/**
 * Class for getting controller related information.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-22
 */
public class ControllerUtil {

    static IControllerDao dao = new DaoInMemory();

    public static IControllerDao getDao() {
        return dao;
    }


    public static void setDao(IControllerDao dao) {
        ControllerUtil.dao = dao;
    }

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    private ControllerUtil() {
        // Private constructor
    }


    /**
     * Creates OpenStackClient
     * <br>
     *
     * @param ctrlUuid Controller UUID
     * @return OpenStackClient Object created
     * @throws ServiceException when OpenStackClient create failed
     * @since SDNO 0.5
     */
    public static OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException {
        OpenStackCredentials creds = dao.getOpenStackCredentials(ctrlUuid);
        String region = dao.getOpenStackRegion(ctrlUuid);
        try {
            return new OpenStackClient(creds, region);
        } catch(OpenStackException e) {
            throw new ServiceException(e);
        }
    }
}
