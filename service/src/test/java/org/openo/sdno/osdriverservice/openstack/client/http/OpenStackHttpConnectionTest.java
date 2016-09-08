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

package org.openo.sdno.osdriverservice.openstack.client.http;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceApiVersion;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceContext;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceType;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;

public class OpenStackHttpConnectionTest {

    @Test
    public void testAddEndpoint() throws OpenStackException {
        OpenStackHttpConnection.EndpointCache ecache = new OpenStackHttpConnection.EndpointCache();

        OpenStackHttpConnection.EndpointCache endpoint =
                ecache.addEndpoint(OpenStackServiceType.NETOWRK, "http://1.1.1.1/xyz");
        assertTrue(null != endpoint);
    }

    @Test
    public void testAddEndpoint2() throws OpenStackException {
        OpenStackHttpConnection.EndpointCache ecache = new OpenStackHttpConnection.EndpointCache();

        OpenStackHttpConnection.EndpointCache endpoint =
                ecache.addEndpoint(OpenStackServiceType.NETOWRK, "http://1.1.1.1/xyz/");
        assertTrue(null != endpoint);
    }

    @Test(expected = OpenStackException.class)
    public void testLoginException() throws OpenStackException {
        OpenStackCredentials creds = new OpenStackCredentials(null, "8080", "username", "password");
        OpenStackHttpConnection conn = new OpenStackHttpConnection(creds);

        conn.login();

    }

    @Test(expected = OpenStackException.class)
    public void testLoginValidData() throws OpenStackException {

        OpenStackCredentials creds = new OpenStackCredentials("1.1.1.1", "8080", "username", "password");
        OpenStackHttpConnection conn = new OpenStackHttpConnection(creds);
        conn.login();

    }

    @Test(expected = Exception.class)
    public void testPost() throws OpenStackException {

        OpenStackCredentials creds = new OpenStackCredentials("1.1.1.1", "8080", "username", "password");
        OpenStackHttpConnection conn = new OpenStackHttpConnection(creds);
        HttpInput input = new HttpInput();
        OpenStackServiceContext context = new OpenStackServiceContext();
        context.setVersion(OpenStackServiceApiVersion.V2);
        input.setContext(context);
        input.setUri("test.com");
        conn.post(input);

    }

    @Test(expected = Exception.class)
    public void testGet() throws OpenStackException {

        OpenStackCredentials creds = new OpenStackCredentials("1.1.1.1", "8080", "username", "password");
        OpenStackHttpConnection conn = new OpenStackHttpConnection(creds);
        HttpInput input = new HttpInput();
        OpenStackServiceContext context = new OpenStackServiceContext();
        context.setVersion(OpenStackServiceApiVersion.V2);
        input.setContext(context);
        input.setUri("test.com");
        conn.get(input);

    }

}
