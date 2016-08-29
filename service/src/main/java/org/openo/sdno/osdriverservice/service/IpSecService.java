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

package org.openo.sdno.osdriverservice.service;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecModel;
import org.openo.sdno.osdriverservice.service.inf.IIpSecService;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class IpSecService implements IIpSecService {

    @Override
    public ResultRsp<NetIpSecModel> createIpSec(DcGwIpSecConnection dcGwIpSecConn, NetIpSecModel netIpSecModel,
            String ctrlUuid, String ipSecConnId) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultRsp<String> deleteIpSec(String ipSecConnId, String ctrlUuid) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
