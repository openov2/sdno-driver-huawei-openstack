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

package org.openo.sdno.osdriverservice.sbi.endtoend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIkePolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecPolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecSiteConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnService;
import org.openo.sdno.osdriverservice.openstack.utils.HttpGateKeeper;
import org.openo.sdno.osdriverservice.openstack.utils.JsonUtil;
import org.openo.sdno.osdriverservice.samples.Utils;
import org.openo.sdno.osdriverservice.sbi.IpSecSbiService;
import org.openo.sdno.osdriverservice.sbi.VpcSbiService;
import org.openo.sdno.osdriverservice.sbi.model.OsIpSec;
import org.openo.sdno.osdriverservice.sbi.model.OsSubnet;
import org.openo.sdno.osdriverservice.sbi.model.OsVpc;

import net.sf.json.JSONObject;

/**
 * Test Both VPC and IpSec end to end.
 * @author 
 *
 */
public class EndToEndSbiTest {

    public static final String IP = "172.241.0.101";

    public static final String PORT = "5000";

    public static final String USERNAME = "admin";

    public static final String PASSWORD = "password";

    public static final String DOMAIN = "default";

    public static final String REGION = "RegionOne";

    public boolean validateBranch = false;
    
    public EndToEndSbiTest() throws OpenStackException {
    }

    public OpenStackCredentials getOpenStackCredentials() {
        return new OpenStackCredentials(IP, PORT, USERNAME, PASSWORD).setDomain(DOMAIN);
    }

    public void checkLogin() throws OpenStackException {
        new OpenStackClient(this.getOpenStackCredentials(), REGION).login();
    }

    public void checkVpc() throws OpenStackException, IOException {
        String json = Utils.getSampleJson(this.getClass(), "sample_vpc.json");
        OsVpc vpc = JsonUtil.fromJson(JSONObject.fromObject(json).toString(), OsVpc.class, false);

        json = Utils.getSampleJson(this.getClass(), "sample_subnet.json");
        OsSubnet subnet = JsonUtil.fromJson(JSONObject.fromObject(json).toString(), OsSubnet.class, false);

        VpcSbiService vpcSrv = new VpcSbiService(new OpenStackClient(this.getOpenStackCredentials(), REGION));

        vpcSrv.createVpc(vpc);
        subnet.getAttributes().setProjectId(vpc.getAttributes().getProjectId(), "u");
        subnet.getAttributes().setRouterId(vpc.getAttributes().getRouterId(), "u");
        vpcSrv.createSubnet(subnet);
        // Test the branch path where existing resources are used in SBI layer
        if (validateBranch) {
            vpcSrv.createVpc(vpc);
            vpcSrv.createSubnet(subnet);
        }
        
        this.checkIpSec(
                vpc.getAttributes().getRouterId(), 
                vpc.getAttributes().getProjectId(),
                subnet.getAttributes().getVpcSubnetId());
        vpcSrv.deleteSubnet(subnet.getAttributes());
        vpcSrv.deleteVpc(vpc.getAttributes());

        // Test the branch path where existing resources are used in SBI layer
        if (validateBranch) {
             vpcSrv.deleteSubnet(subnet.getAttributes());
             vpcSrv.deleteVpc(vpc.getAttributes());
        }
    }

    public void checkIpSec(String routerId, String tenantId, String subnetId) throws OpenStackException, IOException {
        String json = Utils.getSampleJson(this.getClass(), "sample_ipsec.json");
        OsIpSec ipsec = new OsIpSec();
        JSONObject jsonObj = JSONObject.fromObject(json);
        VpnIkePolicy ike = JsonUtil.fromJson(jsonObj.getJSONObject("ikepolicy").toString(), VpnIkePolicy.class, false);
        ike.setTenantId(tenantId);
        ipsec.setVpnIkePolicy(ike);

        VpnIpSecPolicy ipsecPlcy =
                JsonUtil.fromJson(jsonObj.getJSONObject("ipsecpolicy").toString(), VpnIpSecPolicy.class, false);
        ipsecPlcy.setTenantId(tenantId);
        ipsec.setVpnIpSecPolicy(ipsecPlcy);

        VpnService vpnSrv = JsonUtil.fromJson(jsonObj.getJSONObject("vpnservice").toString(), VpnService.class, false);
        vpnSrv.setRouterId(routerId);
        vpnSrv.setTenantId(tenantId);
        vpnSrv.setSubnetId(subnetId);
        ipsec.setVpnService(vpnSrv);

        VpnIpSecSiteConnection conn = JsonUtil.fromJson(jsonObj.getJSONObject("ipsec_site_connection").toString(),
                VpnIpSecSiteConnection.class, false);
        conn.setTenantId(tenantId);
        ipsec.setVpnIpSecSiteConnection(conn);

        IpSecSbiService ipsecSrv = new IpSecSbiService(new OpenStackClient(this.getOpenStackCredentials(), REGION));
        ipsecSrv.createIpSec(ipsec);
        ipsecSrv.deleteIpSec(ipsec.getAttributes());

    }

    public static void main(String args[]) {
        try {
            EndToEndSbiTest check = new EndToEndSbiTest();
            HttpGateKeeper.setEnable(true);
            HttpGateKeeper.setTargetDir(
                    "D:\\workspace\\OSDriver\\service\\src\\integration-test\\resources\\openstack_api_jsons");
            HttpGateKeeper.clean();
            HttpGateKeeper.cleanupMockFiles();
            check.checkVpc();
            Map<String, String> map = new HashMap<>();
            map.put("172.241.0.101", "localhost");
            HttpGateKeeper.generateMockFiles(map);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
