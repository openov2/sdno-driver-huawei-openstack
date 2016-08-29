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

package org.openo.sdno.osdriverservice.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecModel;
import org.openo.sdno.osdriverservice.service.IpSecService;
import org.openo.sdno.osdriverservice.service.inf.IIpSecService;
import org.openo.sdno.osdriverservice.util.ModelConvertUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Restful interface class for OS driver.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-23
 */
@Service
@Path("/sbi-ipsec/v1")
public class OsDriverSvcIpSecRoaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsDriverSvcIpSecRoaResource.class);

    IIpSecService service = new IpSecService();

    /**
     * Create IpSec connection.<br/>
     * 
     * @param request HttpServletRequest Object
     * @param ctrlUuidParam Controller Id Parameter
     * @param dcGwIpSecConnList List of IpSec connections
     * @return ResultRsp object with IpSec connection list data
     * @throws ServiceException when create IpSec connection failed
     * @since SDNO 0.5
     */
    @POST
    @Path("/dc-gateway/batch-create-ipsecs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultRsp<List<DcGwIpSecConnection>> createIpSec(@Context HttpServletRequest request,
            @HeaderParam("X-Driver-Parameter") String ctrlUuidParam, List<DcGwIpSecConnection> dcGwIpSecConnList)
            throws ServiceException {
        long infterEnterTime = System.currentTimeMillis();
        String ctrlUuid = ctrlUuidParam.substring(ctrlUuidParam.indexOf('=') + 1);

        for(DcGwIpSecConnection dcGwIpSecConn : dcGwIpSecConnList) {
            ValidationUtil.validateModel(dcGwIpSecConn);

            NetIpSecModel netIpSecModel = ModelConvertUtil.convertFromIpsecService(dcGwIpSecConn);

            ResultRsp<NetIpSecModel> createRsp =
                    service.createIpSec(dcGwIpSecConn, netIpSecModel, ctrlUuid, dcGwIpSecConn.getUuid());
            if(!createRsp.isSuccess()) {
                LOGGER.error("Create IpSec error.");
                return new ResultRsp<List<DcGwIpSecConnection>>(createRsp, dcGwIpSecConnList);
            }
        }

        LOGGER.info("Exit create method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        return new ResultRsp<List<DcGwIpSecConnection>>(ErrorCode.OVERLAYVPN_SUCCESS, dcGwIpSecConnList);
    }

    /**
     * Delete IpSec connection.<br/>
     * 
     * @param request HttpServletRequest Object
     * @param ctrlUuidParam Controller Id Parameter
     * @param ipSecConnId IpSec connection id
     * @return ResultRsp object with deleting result data
     * @throws ServiceException when delete IpSec connection failed
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/dc-gateway/ipsecs/{ipsecconnectionid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultRsp<String> deleteIpSec(@Context HttpServletRequest request,
            @HeaderParam("X-Driver-Parameter") String ctrlUuidParam, @PathParam("ipsecconnectionid") String ipSecConnId)
            throws ServiceException {
        long infterEnterTime = System.currentTimeMillis();
        String ctrlUuid = ctrlUuidParam.substring(ctrlUuidParam.indexOf('=') + 1);

        ResultRsp<String> result = service.deleteIpSec(ipSecConnId, ctrlUuid);

        LOGGER.info("Exit delete method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        return result;
    }
}
