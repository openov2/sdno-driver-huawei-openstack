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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnDpd;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIkePolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecPolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecSiteConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnPolicyLifeTime;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnService;
import org.openo.sdno.osdriverservice.openstack.client.model.enums.DpdAction;
import org.openo.sdno.osdriverservice.openstack.client.model.enums.IpSecVpnStatus;
import org.openo.sdno.osdriverservice.sbi.model.OsIpSec;
import org.openo.sdno.osdriverservice.sbi.model.OsSubnet;
import org.openo.sdno.osdriverservice.sbi.model.OsVpc;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;

/**
 * Model converting class, converting SDNO model to adapter model.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public class MigrateModelUtil {

    private static final String LIFE_TIME_UINTS = "seconds";

    private static final String PHASE_NEGOTIATION_MODE = "main";

    private static final String INITIATOR = "bi-directional";

    private static final String PEER_CIDRS = "peer_cidrs";

    private static final int DPD_INTERNVAL = 30;

    private static final int DPD_TIMEOUT = 120;

    private static final int MTU = 1500;

    private MigrateModelUtil() {

    }

    /**
     * convert
     * <br>
     *
     * @param dcGwIpSecConnection
     * @return
     * @since  SDNO 0.5
     */
    public static OsIpSec convert(DcGwIpSecConnection dcGwIpSecConnection) {

        OsIpSec ipsec = new OsIpSec();
        ipsec.setOverlayId(dcGwIpSecConnection.getUuid());

        ipsec.setVpnIkePolicy(convert(dcGwIpSecConnection.getIkePolicy()));
        ipsec.setVpnIpSecPolicy(convert(dcGwIpSecConnection.getIpSecPolicy()));
        ipsec.setVpnService(convert1(dcGwIpSecConnection));
        ipsec.setVpnIpSecSiteConnection(convert2(dcGwIpSecConnection));

        return ipsec;
    }

    /**
     * convert
     * <br>
     *
     * @param ikePolicy
     * @return
     * @since  SDNO 0.5
     */
    private static VpnIkePolicy convert(IkePolicy ikePolicy) {

        VpnPolicyLifeTime lifeTime = new VpnPolicyLifeTime();
        lifeTime.setUnits(LIFE_TIME_UINTS);
        lifeTime.setValue(Integer.parseInt(ikePolicy.getLifeTime()));

        VpnIkePolicy ike = new VpnIkePolicy();
        ike.setTenantId(ikePolicy.getTenantId());
        ike.setName(ikePolicy.getName());
        ike.setDescription(ikePolicy.getDescription());
        ike.setAuthAlgorithm(ikePolicy.getAuthAlgorithm());
        ike.setEncryptionAlgorithm(ikePolicy.getEncryptionAlgorithm());
        ike.setPhase1NegotiationMode(PHASE_NEGOTIATION_MODE);
        ike.setPfs(ikePolicy.getPfs());
        ike.setIkeVersion(ikePolicy.getIkeVersion());
        ike.setLifetime(lifeTime);

        return ike;
    }

    /**
     * convert
     * <br>
     *
     * @param ipSecPolicy
     * @return
     * @since  SDNO 0.5
     */
    private static VpnIpSecPolicy convert(IpSecPolicy ipSecPolicy) {

        VpnPolicyLifeTime lifeTime = new VpnPolicyLifeTime();
        lifeTime.setUnits(LIFE_TIME_UINTS);
        lifeTime.setValue(Integer.parseInt(ipSecPolicy.getLifeTime()));

        VpnIpSecPolicy ipsec = new VpnIpSecPolicy();
        ipsec.setAuthAlgorithm(ipSecPolicy.getAuthAlgorithm());
        ipsec.setDescription(ipSecPolicy.getDescription());
        ipsec.setEncapsulationMode(ipSecPolicy.getEncapsulationMode());
        ipsec.setEncryptionAlgorithm(ipSecPolicy.getEncryptionAlgorithm());
        ipsec.setLifetime(lifeTime);
        ipsec.setName(ipSecPolicy.getName());
        ipsec.setPfs(ipSecPolicy.getPfs());
        ipsec.setTenantId(ipSecPolicy.getTenantId());
        ipsec.setTransformProtocol(ipSecPolicy.getTransformProtocol().toLowerCase());

        return ipsec;
    }

    /**
     * convert1
     * <br>
     *
     * @param dcGwIpSecConnection
     * @return
     * @since  SDNO 0.5
     */
    private static VpnService convert1(DcGwIpSecConnection dcGwIpSecConnection) {

        VpnService vpn = new VpnService();
        vpn.setTenantId(dcGwIpSecConnection.getTenantId());
        vpn.setRouterId(dcGwIpSecConnection.getRouterId());
        vpn.setSubnetId(dcGwIpSecConnection.getSubnetId());
        vpn.setAdminStateUp(IpSecVpnStatus.ACTIVE.getName().equals(dcGwIpSecConnection.getAdminStatus()));
        vpn.setName(dcGwIpSecConnection.getName());
        vpn.setDescription(dcGwIpSecConnection.getName());

        return vpn;
    }

    /**
     * convert2
     * <br>
     *
     * @param dcGwIpSecConnection
     * @return
     * @since  SDNO 0.5
     */
    private static VpnIpSecSiteConnection convert2(DcGwIpSecConnection dcGwIpSecConnection) {
        String[] peerCidrArr = dcGwIpSecConnection.getPeerSubnetCidrs().split(",");
        List<String> peerCidrList = new ArrayList<>();
        for(String peerCidr : peerCidrArr) {
            peerCidrList.add(peerCidr);
        }

        VpnDpd dpd = new VpnDpd();
        dpd.setAction(DpdAction.HOLD.getName());
        dpd.setInterval(DPD_INTERNVAL);
        dpd.setTimeout(DPD_TIMEOUT);

        VpnIpSecSiteConnection conn = new VpnIpSecSiteConnection();
        conn.setTenantId(dcGwIpSecConnection.getVpcId());
        conn.setName(dcGwIpSecConnection.getName());
        conn.setDescription(dcGwIpSecConnection.getName());
        conn.setPeerAddress(dcGwIpSecConnection.getPeerAddress());
        conn.setPeerCidrs(peerCidrList);
        conn.setPeerId(dcGwIpSecConnection.getPeerAddress());
        conn.setMtu(MTU);
        conn.setPsk(dcGwIpSecConnection.getPsk());
        conn.setInitiator(INITIATOR);
        conn.setAdminStateUp(IpSecVpnStatus.ACTIVE.getName().equals(dcGwIpSecConnection.getAdminStatus()));
        conn.setDpd(dpd);
        conn.setSubnets(null);

        return conn;
    }

    /**
     * convert
     * <br>
     *
     * @param vpc
     * @return
     * @since  SDNO 0.5
     */
    public static OsVpc convert(Vpc vpc) {
        OsVpc osVpc = new OsVpc();
        osVpc.setProjectName(vpc.getProjectName());
        osVpc.setDomainName(vpc.getDomainName());
        osVpc.setOverlayId(vpc.getUuid());
        return osVpc;
    }

    /**
     * convert
     * <br>
     *
     * @param subnet
     * @return
     * @since  SDNO 0.5
     */
    public static OsSubnet convert(Subnet subnet) {
        OsSubnet osSubnet = new OsSubnet();
        osSubnet.setCidr(subnet.getCidr());
        osSubnet.setOverlayId(subnet.getUuid());
        return osSubnet;
    }

    /**
     * convert
     * <br>
     *
     * @param mappings
     * @return
     * @since  SDNO 0.5
     */
    public static OsVpc.Underlays convert(List<OverlayUnderlayMapping> mappings) {
        OsVpc.Underlays underlays = new OsVpc.Underlays();
        for(OverlayUnderlayMapping mapping : mappings) {
            if("routerId".equals(mapping.getUnderlayType())) {
                underlays.setRouterId(mapping.getUnderlayId(), mapping.getAction());
            } else if("projectId".equals(mapping.getUnderlayType())) {
                underlays.setProjectId(mapping.getUnderlayId(), mapping.getAction());
            } else if("publicNetworkId".equals(mapping.getUnderlayType())) {
                underlays.setPublicNetworkId(mapping.getUnderlayId(), mapping.getAction());
            }
        }

        return underlays;
    }

    /**
     * convert1
     * <br>
     *
     * @param mappings
     * @return
     * @since  SDNO 0.5
     */
    public static OsSubnet.Underlays convert1(List<OverlayUnderlayMapping> mappings) {
        OsSubnet.Underlays underlays = new OsSubnet.Underlays();
        for(OverlayUnderlayMapping mapping : mappings) {
            if("routerId".equals(mapping.getUnderlayType())) {
                underlays.setRouterId(mapping.getUnderlayId(), mapping.getAction());
            } else if("networkId".equals(mapping.getUnderlayType())) {
                underlays.setVpcNetworkId(mapping.getUnderlayId(), mapping.getAction());
            } else if("subnetId".equals(mapping.getUnderlayType())) {
                underlays.setVpcSubnetId(mapping.getUnderlayId(), mapping.getAction());
            } else if("projectId".equals(mapping.getUnderlayType())) {
                underlays.setProjectId(mapping.getUnderlayId(), mapping.getAction());
            }
        }

        return underlays;
    }

    /**
     * convert2
     * <br>
     *
     * @param mappings
     * @return
     * @since  SDNO 0.5
     */
    public static OsIpSec.Underlays convert2(List<OverlayUnderlayMapping> mappings) {
        OsIpSec.Underlays underlays = new OsIpSec.Underlays();
        for(OverlayUnderlayMapping mapping : mappings) {
            if("vpnIkePolicyId".equals(mapping.getUnderlayType())) {
                underlays.setVpnIkePolicyId(mapping.getUnderlayId(), mapping.getAction());
            } else if("vpnIpSecPolicyId".equals(mapping.getUnderlayType())) {
                underlays.setVpnIpSecPolicyId(mapping.getUnderlayId(), mapping.getAction());
            } else if("vpnServiceId".equals(mapping.getUnderlayType())) {
                underlays.setVpnServiceId(mapping.getUnderlayId(), mapping.getAction());
            } else if("vpnIpSecSiteConnectionId".equals(mapping.getUnderlayType())) {
                underlays.setVpnIpSecSiteConnectionId(mapping.getUnderlayId(), mapping.getAction());
            }
        }

        return underlays;
    }

    /**
     * convert
     * <br>
     *
     * @param resources
     * @param actions
     * @param tenantId
     * @param overlayType
     * @param controllerId
     * @param overlayId
     * @return
     * @throws ServiceException
     * @since  SDNO 0.5
     */
    public static List<OverlayUnderlayMapping> convert(Map<String, String> resources, Map<String, String> actions,
            String tenantId, String overlayType, String controllerId, String overlayId) throws ServiceException {
        List<OverlayUnderlayMapping> mappings = new ArrayList<>();

        for(Entry<String, String> entry : actions.entrySet()) {
            OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
            mapping.setUuid(UUID.randomUUID().toString());
            mapping.setControllerId(controllerId);
            mapping.setOverlayId(overlayId);
            mapping.setOverlayType(overlayType);
            mapping.setUnderlayTenantId(tenantId);
            mapping.setUnderlayId(entry.getKey());
            mapping.setAction(entry.getValue());
            mapping.setUnderlayType(resources.get(entry.getKey()));
            mappings.add(mapping);
        }
        return mappings;
    }
}
