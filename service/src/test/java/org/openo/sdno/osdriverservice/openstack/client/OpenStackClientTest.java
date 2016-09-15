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

package org.openo.sdno.osdriverservice.openstack.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpInput;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpResult;
import org.openo.sdno.osdriverservice.openstack.client.http.OpenStackHttpConnection;
import org.openo.sdno.osdriverservice.openstack.utils.JsonUtil;

import mockit.Mock;
import mockit.MockUp;

public class OpenStackClientTest {

    OpenStackCredentials credentials = new OpenStackCredentials();

    OpenStackClient client = null;

    @Test(expected = OpenStackException.class)
    public void testGetRounterException() throws OpenStackException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult get(HttpInput input) throws OpenStackException {
                HttpResult response = new HttpResult();
                return response;
            }
        };
        this.client = new OpenStackClient(this.credentials, "test");

        this.client.getRounter("test123");
    }

    // TODO : check why JSONException is comming
    @Test(expected = Exception.class)
    public void testGetRounterException2() throws OpenStackException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult get(HttpInput input) throws OpenStackException {
                HttpResult response = new HttpResult();
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                Map map = new HashMap<String, String>();
                map.put("id", "test123");
                list.add(map);
                response.setBody(JsonUtil.toJson(list));
                response.setStatus(201);
                return response;
            }
        };
        this.client = new OpenStackClient(this.credentials, "test");

        this.client.getRounter("test123");
    }

    @Test(expected = Exception.class)
    public void testGetRounterException3() throws OpenStackException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult get(HttpInput input) throws OpenStackException {
                HttpResult response = new HttpResult();
                List<String> list = new ArrayList<String>();
                list.add("test");
                response.setBody(JsonUtil.toJson(list));
                response.setStatus(202);
                return response;
            }
        };
        this.client = new OpenStackClient(this.credentials, "test");

        this.client.getRounter("test123");
    }

    @Test(expected = Exception.class)
    public void testGetRounterException4() throws OpenStackException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult get(HttpInput input) throws OpenStackException {
                HttpResult response = new HttpResult();

                response.setStatus(204);
                return response;
            }
        };
        this.client = new OpenStackClient(this.credentials, "test");

        this.client.getRounter("test123");
    }

}
