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
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecModel;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Class for creating VPC service.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-20
 */
public interface IIpSecService {

    /**
     * Create IpSec connection.<br/>
     *
     * @param dcGwIpSecConn DC gateway IpSec connection
     * @param netIpSecModel IpSec model in adapter layer
     * @param ctrlUuid controller UUID
     * @param ipSecConnId IpSec connection id
     * @return ResultRsp object with NetIpSecModel data
     * @throws ServiceException if creating IpSec connection failed.
     * @since SDNO 0.5
     */
    public ResultRsp<NetIpSecModel> createIpSec(DcGwIpSecConnection dcGwIpSecConn, NetIpSecModel netIpSecModel,
            String ctrlUuid, String ipSecConnId) throws ServiceException;

    /**
     * Delete IpSec connection.<br/>
     *
     * @param ipSecConnId IpSec id
     * @param ctrlUuid controller UUID
     * @return ResultRsp object with deleting result data
     * @throws ServiceException if deleting IpSec connection failed.
     * @since SDNO 0.5
     */
    public ResultRsp<String> deleteIpSec(String ipSecConnId, String ctrlUuid) throws ServiceException;
}
