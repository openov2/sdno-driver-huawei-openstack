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

package org.openo.sdno.osdriverservice.sbi;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.rest.CloseableHttpResponseMock;
import org.openo.sdno.osdriverservice.sbi.IpSecSbiService;
import org.openo.sdno.osdriverservice.sbi.model.OsIpSec;
import org.openo.sdno.osdriverservice.sbi.model.OsIpSec.Underlays;

import mockit.Mock;
import mockit.MockUp;

/**
 * IpSec Service tests.<br>
 *
 * @author
 * @version SDNO 0.5 September 20, 2016
 */
public class IpSecServiceTest {

    @Test
    public void testCreateIpSec() throws ServiceException, OpenStackException {

        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}}";
            }
        };
        new MockUp<CloseableHttpClient>() {

            @Mock
            public CloseableHttpResponse execute(final HttpUriRequest request)
                    throws IOException, ClientProtocolException {
                CloseableHttpResponseMock resp = new CloseableHttpResponseMock();
                org.apache.http.message.BasicStatusLine bs =
                        new org.apache.http.message.BasicStatusLine(new ProtocolVersion("bgp", 2, 1), 200, "success");
                resp.setStatusLine(bs);
                return (CloseableHttpResponse)resp;
            }
        };
        IpSecSbiService ipSecService = new  IpSecSbiService(new OpenStackClient(new OpenStackCredentials("192.168.1.1", "1923", "user","pwd"), "IN"));
        org.openo.sdno.osdriverservice.sbi.model.OsIpSec ipsec = new org.openo.sdno.osdriverservice.sbi.model.OsIpSec();
        Underlays attributes = new Underlays();
        attributes.setVpnIkePolicyId("test", "d");
        attributes.setVpnIpSecPolicyId("vpnIpSecPolicyId", "d");
        attributes.setVpnIpSecSiteConnectionId("vpnIpSecSiteConnectionId", "action");
        attributes.setVpnServiceId("vpnServiceId", "action");
        ipsec.setAttributes(attributes);
        OsIpSec resultOsIpSec = ipSecService.createIpSec(ipsec );
        assertNotNull(resultOsIpSec);
    }
}
