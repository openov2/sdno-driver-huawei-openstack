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

package org.openo.sdno.osdriverservice.nbi;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.osdriverservice.rest.OsDriverSvcVpcRoaResourceTest;
import org.openo.sdno.osdriverservice.samples.Utils;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;;

public class Test {

    public static final String URL =
            "http://localhost:8080/org.openo.sdno.osdriverservice/rest/svc/drivers/dc-gateway/v1/TEST";

    private HttpClient httpClient = HttpClients.createDefault();

    @SuppressWarnings("deprecation")
    public HttpResponse post(String uri, String body) throws ClientProtocolException, IOException {
        HttpPost req = new HttpPost(URL + uri);
        req.addHeader("Accept", "application/json");
        req.addHeader("Content-Type", "application/json");

        StringEntity reqEntity = new StringEntity(body, HTTP.UTF_8);
        req.setEntity(reqEntity);

        HttpResponse resp = this.httpClient.execute(req);
        return resp;
    }

    @SuppressWarnings("deprecation")
    public HttpResponse delete(String uri) throws ClientProtocolException, IOException {
        HttpDelete req = new HttpDelete(URL + uri);

        HttpResponse resp = this.httpClient.execute(req);
        return resp;
    }

   /* public void verifyOSDDirect() throws ServiceException {
        try {
            String vpcJson = Utils.getSampleJson(Test.class, "sample_vpc.json");
            OsDriverSvcVpcRoaResourceTest rsc = new OsDriverSvcVpcRoaResourceTest();
            Vpc vpc = rsc.createVpc(null, null, "TEST", JsonUtil.fromJson(vpcJson, Vpc.class));

            String subnetJson = Utils.getSampleJson(Test.class, "sample_subnet.json");

            Subnet subnet = rsc.createSubnet(null, null, "TEST", JsonUtil.fromJson(subnetJson, Subnet.class));

            rsc.deleteSubnet(null, null, "TEST", subnet.getUuid());
            rsc.deleteVpc(null, null, "TEST", vpc.getUuid());
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
*/
    public void verifyOSD() throws ParseException, IOException {
        String vpcJson = Utils.getSampleJson(Test.class, "sample_vpc.json");
        HttpResponse resp = this.post("/vpcs", vpcJson);
        if(resp.getStatusLine().getStatusCode() != 200) {
            System.out.println("Failed to created VPC");
            System.out.println(EntityUtils.toString(resp.getEntity(), HTTP.UTF_8));
            return;
        }

        String subnetJson = Utils.getSampleJson(Test.class, "sample_subnet.json");

        resp = this.post("/subnets", subnetJson);
        if(resp.getStatusLine().getStatusCode() != 200) {
            System.out.println("Failed to created Subnet");
            System.out.println(EntityUtils.toString(resp.getEntity(), HTTP.UTF_8));
            return;
        }

        subnetJson = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
        Subnet subnet = JsonUtil.fromJson(subnetJson, Subnet.class);
        this.delete("/subnets/" + subnet.getUuid());

        Vpc vpc = JsonUtil.fromJson(vpcJson, Vpc.class);
        this.delete("/vpcs/" + vpc.getUuid());
    }

    public void testDirect() {
        OpenStackVpcSuccessServer server = new OpenStackVpcSuccessServer();
        Test verify = new Test();
        try {
            server.start();
           // .getClass().verify.verifyOSDDirect();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    public static void main(String[] args) {
        new Test().testDirect();
    }

}
