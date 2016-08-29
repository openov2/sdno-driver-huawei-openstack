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

package org.openo.sdno.osdriverservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.osdriverservice.model.adapter.Dpd;
import org.openo.sdno.osdriverservice.model.adapter.LifeTime;
import org.openo.sdno.osdriverservice.model.adapter.NetIkePolicy;
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecModel;
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecPolicy;
import org.openo.sdno.osdriverservice.model.adapter.NetIpSecSiteConnection;
import org.openo.sdno.osdriverservice.model.adapter.NetVpnService;
import org.openo.sdno.osdriverservice.model.enums.DpdAction;
import org.openo.sdno.osdriverservice.model.enums.IpSecVpnStatus;
import org.openo.sdno.osdriverservice.model.enums.RouteMode;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;

/**
 * Model converting class, converting SDNO model to adapter model.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public class ModelConvertUtil {

    private static final String LIFE_TIME_UINTS = "seconds";

    private static final String PHASE_NEGOTIATION_MODE = "main";

    private static final String INITIATOR = "bi-directional";

    private static final String PEER_CIDRS = "peer_cidrs";

    private static final int DPD_INTERNVAL = 30;

    private static final int DPD_TIMEOUT = 120;

    private static final int MTU = 1500;

    private ModelConvertUtil() {
    }

    /**
     * Convent DC gateway IpSec connection to network IpSec model.<br/>
     *
     * @param dcGwIpSecConnection DC gateway IpSec connection
     * @return Network IpSec model
     * @since  SDNO 0.5
     */
    public static NetIpSecModel convertFromIpsecService(DcGwIpSecConnection dcGwIpSecConnection) {

        NetIpSecModel netIpSecModel = new NetIpSecModel();

        netIpSecModel.setNetIkePolicy(convertForNetIkePolicy(dcGwIpSecConnection.getIkePolicy()));
        netIpSecModel.setNetIpSecPolicy(convertForNetIpSecPolicy(dcGwIpSecConnection.getIpSecPolicy()));
        netIpSecModel.setNetVpnService(convertForNetVpnService(dcGwIpSecConnection));
        netIpSecModel.setNetIpSecSiteConnection(convertForNetIpSecSiteConnection(dcGwIpSecConnection));

        return netIpSecModel;
    }

    private static NetIkePolicy convertForNetIkePolicy(IkePolicy ikePolicy) {

        LifeTime lifeTime = new LifeTime(LIFE_TIME_UINTS, Integer.parseInt(ikePolicy.getLifeTime()));
        NetIkePolicy netIkePolicy = new NetIkePolicy();
        netIkePolicy.setTenantId(ikePolicy.getTenantId());
        netIkePolicy.setName(ikePolicy.getName());
        netIkePolicy.setDescription(ikePolicy.getDescription());
        netIkePolicy.setAuthAlgorithm(ikePolicy.getAuthAlgorithm());
        netIkePolicy.setEncryptionAlgorithm(ikePolicy.getEncryptionAlgorithm());
        netIkePolicy.setPhase1NegotiationMode(PHASE_NEGOTIATION_MODE);
        netIkePolicy.setPfs(ikePolicy.getPfs());
        netIkePolicy.setIkeVersion(ikePolicy.getIkeVersion());
        netIkePolicy.setLifetime(lifeTime);

        return netIkePolicy;
    }

    private static NetIpSecPolicy convertForNetIpSecPolicy(IpSecPolicy ipSecPolicy) {

        LifeTime lifeTime = new LifeTime(LIFE_TIME_UINTS, Integer.parseInt(ipSecPolicy.getLifeTime()));
        NetIpSecPolicy netIpSecPolicy = new NetIpSecPolicy();
        netIpSecPolicy.setTenantId(ipSecPolicy.getTenantId());
        netIpSecPolicy.setName(ipSecPolicy.getName());
        netIpSecPolicy.setDescription(ipSecPolicy.getDescription());
        ipSecPolicy.setTransformProtocol(ipSecPolicy.getTransformProtocol().toUpperCase());
        netIpSecPolicy.setEncapsulationMode(ipSecPolicy.getEncapsulationMode());
        netIpSecPolicy.setAuthAlgorithm(ipSecPolicy.getAuthAlgorithm());
        netIpSecPolicy.setEncryptionAlgorithm(ipSecPolicy.getEncryptionAlgorithm());
        netIpSecPolicy.setPfs(ipSecPolicy.getPfs());
        netIpSecPolicy.setLifetime(lifeTime);

        return netIpSecPolicy;
    }

    private static NetVpnService convertForNetVpnService(DcGwIpSecConnection dcGwIpSecConnection) {

        NetVpnService netVpnService = new NetVpnService();
        netVpnService.setTenantId(dcGwIpSecConnection.getVpcId());
        netVpnService.setName(null);
        netVpnService.setDescription(null);
        netVpnService.setSubnetId(dcGwIpSecConnection.getSubnetId());
        netVpnService.setRouterId(dcGwIpSecConnection.getRouterId());
        netVpnService.setStatus(IpSecVpnStatus.PENDING_CREATE.getName());
        if (IpSecVpnStatus.ACTIVE.getName().equals(dcGwIpSecConnection.getAdminStatus())) {
            netVpnService.setAdminStateUp(true);
        }

        return netVpnService;
    }

    private static NetIpSecSiteConnection convertForNetIpSecSiteConnection(DcGwIpSecConnection dcGwIpSecConnection) {

        String[] peerCidrArr = dcGwIpSecConnection.getPeerSubnetCidrs().split(",");
        List<String> peerCidrList = new ArrayList<>();
        for(String peerCidr : peerCidrArr) {
            peerCidrList.add(peerCidr);
        }

        Map<String, List<String>> peerCidrs = new HashMap<String, List<String>>();
        peerCidrs.put(PEER_CIDRS, peerCidrList);

        Dpd dpd = new Dpd(DpdAction.HOLD.getName(), DPD_INTERNVAL, DPD_TIMEOUT);
        NetIpSecSiteConnection connection = new NetIpSecSiteConnection();
        connection.setTenantId(dcGwIpSecConnection.getVpcId());
        connection.setName(null);
        connection.setDescription(null);
        connection.setPeerAddress(dcGwIpSecConnection.getPeerAddress());
        connection.setPeerId(dcGwIpSecConnection.getPeerAddress());
        connection.setPeerCidrs(peerCidrs);
        connection.setRouteMode(RouteMode.STATIC.getName());
        connection.setMtu(MTU);
        connection.setAuthMode(dcGwIpSecConnection.getAuthMode().toLowerCase());
        connection.setPsk(dcGwIpSecConnection.getPsk());
        connection.setInitiator(INITIATOR);
        connection.setStatus(IpSecVpnStatus.PENDING_CREATE.getName());
        connection.setDpd(dpd);
        connection.setSubnets(null);
        if (IpSecVpnStatus.ACTIVE.getName().equals(dcGwIpSecConnection.getAdminStatus())) {
            connection.setAdminStateUp(true);
        }

        return connection;
    }

}
