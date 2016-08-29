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

package org.openo.sdno.osdriverservice.service.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.model.OsSubnet;
import org.openo.sdno.osdriverservice.model.OsVpc;

/**
 * Class for creating VPC service.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public interface IVpcService {

    /**
     * To create VPC service.
     * <br/>
     * 
     * @param ctrlUuid
     * @param vpc
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public OsVpc createVpc(String ctrlUuid, OsVpc vpc) throws ServiceException;

    /**
     * To delete VPC service.
     * <br/>
     * 
     * @param ctrlUuid
     * @param vpcId
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public void deleteVpc(String ctrlUuid, String vpcId) throws ServiceException;

    /**
     * To create Subnet.
     * <br/>
     * 
     * @param ctrlUuid
     * @param vpcId
     * @param subnet
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public OsSubnet createSubnet(String ctrlUuid, String vpcId, OsSubnet subnet) throws ServiceException;

    /**
     * To delete subnet.
     * <br/>
     * 
     * @param ctrlUuid
     * @param subnetId
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public void deleteSubnet(String ctrlUuid, String subnetId) throws ServiceException;
}
