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

package org.openo.sdno.osdriverservice.dao.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;

/**
 *
 * DAO  class interface<br>
 * <p>
 * </p>
 *
 * @param <T>
 * @author
 * @version     SDNO 0.5  Aug 6, 2016
 */
public interface IControllerDao {

    /**
     * Get OpenStack credentials
     * <br>
     *
     * @param ctrlUuid Controller UUID
     * @return OpenStackCredentials Object created
     * @throws ServiceException when OpenStackCredentials retrieval failed
     * @since SDNO 0.5
     */
    OpenStackCredentials getOpenStackCredentials(String ctrlUuid) throws ServiceException;
    

    /**
     * Get OpenStack region
     * <br>
     *
     * @param ctrlUuid Controller UUID
     * @return String region
     * @throws ServiceException when region retrieval failed
     * @since SDNO 0.5
     */
    String getOpenStackRegion(String ctrlUuid) throws ServiceException;
}
