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

package org.openo.sdno.osdriverservice.openstack.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Client model tests.<br>
 *
 * @author
 * @version SDNO 0.5 September 20, 2016
 */
public class ClientModelTest {

    @Test
    public void NetworkTest() {
        Network obj = new Network();

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setProjectId("10");
        assertEquals("10", obj.getProjectId());
    }

    @Test
    public void RouterTest() {
        Router obj = new Router();

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setProjectId("10");
        assertEquals("10", obj.getProjectId());
    }

    @Test
    public void SubnetTest() {
        Subnet obj = new Subnet();

        obj.setNetworkId("10.1.1.1");
        assertEquals("10.1.1.1", obj.getNetworkId());

        obj.setCidr("cidr");
        assertEquals("cidr", obj.getCidr());

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setProjectId("10");
        assertEquals("10", obj.getProjectId());

        obj.setIpVersion("6");
        assertEquals("6", obj.getIpVersion());
    }

    @Test
    public void SubnetAttachmentTest() {
        SubnetAttachment obj = new SubnetAttachment();

        obj.setSubnetId("24");
        assertEquals("24", obj.getSubnetId());
    }

    @Test
    public void VpnDpdTest() {
        VpnDpd obj = new VpnDpd();

        obj.setAction("clear");
        assertEquals("clear", obj.getAction());

        obj.setInterval(10);
        assertEquals(10, obj.getInterval());

        obj.setTimeout(10);
        assertEquals(10, obj.getTimeout());
    }

    @Test
    public void VpnIkePolicyTest() {
        VpnIkePolicy obj = new VpnIkePolicy();

        obj.setTenantId("10");
        assertEquals("10", obj.getTenantId());

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setDescription("sdno");
        assertEquals("sdno", obj.getDescription());

        obj.setAuthAlgorithm("md5");
        assertEquals("md5", obj.getAuthAlgorithm());

        obj.setEncryptionAlgorithm("rsa");
        assertEquals("rsa", obj.getEncryptionAlgorithm());

        obj.setPhase1NegotiationMode("mode1");
        assertEquals("mode1", obj.getPhase1NegotiationMode());

        obj.setPfs("pfs");
        assertEquals("pfs", obj.getPfs());

        obj.setIkeVersion("1.1");
        assertEquals("1.1", obj.getIkeVersion());

        VpnPolicyLifeTime vpnPolLifTm = new VpnPolicyLifeTime();
        vpnPolLifTm.setUnits("10");
        obj.setLifetime(vpnPolLifTm);
        assertEquals("10", obj.getLifetime().getUnits());
    }

    @Test
    public void VpnIpSecPolicyTest() {
        VpnIpSecPolicy obj = new VpnIpSecPolicy();

        obj.setTenantId("10");
        assertEquals("10", obj.getTenantId());

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setDescription("sdno");
        assertEquals("sdno", obj.getDescription());

        obj.setAuthAlgorithm("md5");
        assertEquals("md5", obj.getAuthAlgorithm());

        obj.setEncryptionAlgorithm("rsa");
        assertEquals("rsa", obj.getEncryptionAlgorithm());

        obj.setTransformProtocol("proto");
        assertEquals("proto", obj.getTransformProtocol());

        obj.setPfs("pfs");
        assertEquals("pfs", obj.getPfs());

        obj.setEncapsulationMode("ipsec");
        assertEquals("ipsec", obj.getEncapsulationMode());

        VpnPolicyLifeTime vpnPolLifTm = new VpnPolicyLifeTime();
        vpnPolLifTm.setUnits("10");
        obj.setLifetime(vpnPolLifTm);
        assertEquals("10", obj.getLifetime().getUnits());
    }

    @Test
    public void VpnIpSecSiteConnectionTest() {
        VpnIpSecSiteConnection obj = new VpnIpSecSiteConnection();

        obj.setTenantId("10");
        assertEquals("10", obj.getTenantId());

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setDescription("sdno");
        assertEquals("sdno", obj.getDescription());

        obj.setPeerAddress("1.1.1.1");
        assertEquals("1.1.1.1", obj.getPeerAddress());

        obj.setPeerId("10");
        assertEquals("10", obj.getPeerId());
    }

    @Test
    public void VpnPolicyLifeTimeTest() {
        VpnPolicyLifeTime obj = new VpnPolicyLifeTime();

        obj.setValue(10);
        assertEquals(10, obj.getValue());
    }

    @Test
    public void VpnServiceTest() {
        VpnService obj = new VpnService();

        obj.setTenantId("10");
        assertEquals("10", obj.getTenantId());

        obj.setName("sdno");
        assertEquals("sdno", obj.getName());

        obj.setDescription("sdno");
        assertEquals("sdno", obj.getDescription());

        obj.setSubnetId("24");
        assertEquals("24", obj.getSubnetId());

        obj.setRouterId("10");
        assertEquals("10", obj.getRouterId());

        obj.setStatus("active");
        assertEquals("active", obj.getStatus());

        assertFalse(obj.isAdminStateUp());
    }
}
