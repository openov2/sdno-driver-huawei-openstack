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

import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;

public class OpenStackVpcSuccessServer extends MocoHttpServer {

    private static String[] vpcMockJsons =
            new String[] {"src/integration-test/resources/openstack_api_jsons/auth_token.json",
                            "src/integration-test/resources/openstack_api_jsons/project_create.json",
                            "src/integration-test/resources/openstack_api_jsons/project_list.json",
                            "src/integration-test/resources/openstack_api_jsons/project_delete.json",
                            "src/integration-test/resources/openstack_api_jsons/project_create.json",
                            "src/integration-test/resources/openstack_api_jsons/network_list.json",
                            "src/integration-test/resources/openstack_api_jsons/network_delete.json",
                            "src/integration-test/resources/openstack_api_jsons/network_create.json",
                            "src/integration-test/resources/openstack_api_jsons/router_list.json",
                            "src/integration-test/resources/openstack_api_jsons/router_delete.json",
                            "src/integration-test/resources/openstack_api_jsons/router_create.json",
                            "src/integration-test/resources/openstack_api_jsons/subnet_list.json",
                            "src/integration-test/resources/openstack_api_jsons/subnet_delete.json",
                            "src/integration-test/resources/openstack_api_jsons/subnet_create.json",
                            "src/integration-test/resources/openstack_api_jsons/subnet_attach.json",
                            "src/integration-test/resources/openstack_api_jsons/subnet_detach.json"};

    public OpenStackVpcSuccessServer() {
        super();
    }

    @Override
    public void addRequestResponsePairs() {
        for(String file : vpcMockJsons) {
            this.addRequestResponsePair(file, new VpcResponseHandler());

        }
    }

    private class VpcResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {
            HttpRequest req = httpObject.getRequest();
            HttpResponse res = httpObject.getResponse();

            System.out.println(req);
            System.out.println(res);

        }
    }
}
