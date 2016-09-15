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

package org.openo.sdno.osdriverservice.rest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.client.model.Network;
import org.openo.sdno.osdriverservice.openstack.client.model.Project;
import org.openo.sdno.osdriverservice.openstack.client.model.Router;
import org.openo.sdno.osdriverservice.openstack.client.model.Router.ExternalGatewayInfo;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecSiteConnection;
import org.openo.sdno.osdriverservice.util.DaoUtil;
import org.openo.sdno.overlayvpn.brs.invdao.CommParamDao;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.model.AuthInfo;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;

import mockit.Mock;
import mockit.MockUp;

public class OsDriverSvcVpcRoaResourceTest {

    OsDriverSvcVpcRoaResource roaVPC = new OsDriverSvcVpcRoaResource();

    @Test
    public void testCreateVpc() throws ServiceException {
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                } else if(valueType.getName().contains("VpnIpSecSiteConnection")) {
                    VpnIpSecSiteConnection vpn = new VpnIpSecSiteConnection();
                    vpn.setId("id");
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> T insert(T data) throws ServiceException {
                return null;
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        Vpc vpc = new Vpc();
        vpc.setName("name/name");
        vpc.setUuid("uuid");
        Vpc vpcReturn = roaVPC.createVpc(request, response, "name=id", vpc);
        assertTrue(vpcReturn != null);
    }

    @Test
    public void testCreateVpcBranch() throws ServiceException {
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid1");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                } else if(valueType.getName().contains("VpnIpSecSiteConnection")) {
                    VpnIpSecSiteConnection vpn = new VpnIpSecSiteConnection();
                    vpn.setId("id");
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> T insert(T data) throws ServiceException {
                return null;
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        Vpc vpc = new Vpc();
        vpc.setName("name/name");
        vpc.setUuid("uuid");
        Vpc vpcReturn = roaVPC.createVpc(request, null, "name=id", vpc);
        assertTrue(vpcReturn != null);
    }

    @Test(expected = Exception.class)
    public void testCreateVpcException() throws ServiceException {
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid1");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                } else if(valueType.getName().contains("VpnIpSecSiteConnection")) {
                    VpnIpSecSiteConnection vpn = new VpnIpSecSiteConnection();
                    vpn.setId("id");
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> T insert(T data) throws ServiceException {
                return null;
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        Vpc vpc = new Vpc();
        vpc.setName("name/name");
        vpc.setUuid("uuid");
        Vpc vpcReturn = roaVPC.createVpc(request, response, "name=id", vpc);
        assertTrue(vpcReturn != null);
    }

    @Test
    public void testDeleteVpc() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("publicNetworkId");
                obj4.setUnderlayType("extra");
                obj1.setAction("a");
                obj2.setAction("d");
                obj3.setAction("action");
                obj4.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        roaVPC.deleteVpc(request, response, "name=id", "vpcID");
        assertTrue(true);
    }

    @Test
    public void testDeleteVpcBranch() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("publicNetworkId");
                obj4.setUnderlayType("extra");
                obj1.setAction("d");
                obj2.setAction("a");
                obj3.setAction("action");
                obj4.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        roaVPC.deleteVpc(request, null, "name=id", "vpcID");
        assertTrue(true);
    }

    @Test
    public void testDeleteVpcException() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException, OpenStackException {
                throw new OpenStackException("404");
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        roaVPC.deleteVpc(request, null, "name=id", "vpcID");
        assertTrue(true);
    }

    @Test
    public void testCreateSubnet() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("publicNetworkId");
                obj4.setUnderlayType("extra");
                obj1.setAction("d");
                obj2.setAction("a");
                obj3.setAction("action");
                obj4.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}], \"subnets\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Subnet")) {
                    org.openo.sdno.osdriverservice.openstack.client.model.Subnet vpn =
                            new org.openo.sdno.osdriverservice.openstack.client.model.Subnet();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setAdminStateUp(true);
                    vpn.setId("id");
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> T insert(T data) throws ServiceException {
                return null;
            }
        };
        Subnet subnet = new Subnet();
        subnet.setCidr("cidr");
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        Subnet subnetReturn = roaVPC.createSubnet(request, response, "ctrlUUID", subnet);
        assertTrue(subnetReturn != null);
    }

    @Test
    public void testCreateSubnetBranch() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("publicNetworkId");
                obj4.setUnderlayType("extra");
                obj1.setAction("d");
                obj2.setAction("a");
                obj3.setAction("action");
                obj4.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}], \"subnets\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Subnet")) {
                    org.openo.sdno.osdriverservice.openstack.client.model.Subnet vpn =
                            new org.openo.sdno.osdriverservice.openstack.client.model.Subnet();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setAdminStateUp(false);
                    vpn.setId("id");
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> T insert(T data) throws ServiceException {
                return null;
            }
        };
        Subnet subnet = new Subnet();
        subnet.setCidr("cidr");
        HttpServletRequest request = new HttpRequest();
        Subnet subnetReturn = roaVPC.createSubnet(request, null, "ctrlUUID", subnet);
        assertTrue(subnetReturn != null);
    }

    @Test
    public void testDeleteSubnet() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj5 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("networkId");
                obj4.setUnderlayType("subnetId");
                obj5.setUnderlayType("extra");
                obj1.setAction("d");
                obj2.setAction("a");
                obj3.setAction("action");
                obj4.setAction("action");
                obj5.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                list.add(obj5);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        roaVPC.deleteSubnet(request, response, "ctrlUUID", "12345");
        assertTrue(true);
    }

    @Test
    public void testDeleteSubnetBranch() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj5 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("networkId");
                obj4.setUnderlayType("subnetId");
                obj5.setUnderlayType("extra");
                obj1.setAction("a");
                obj2.setAction("d");
                obj3.setAction("action");
                obj4.setAction("action");
                obj5.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                list.add(obj5);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException {
                if(valueType.getName().contains("Project")) {
                    Project vpn = new Project();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Network")) {
                    Network vpn = new Network();
                    vpn.setId("id");
                    return (T)vpn;
                } else if(valueType.getName().contains("Router")) {
                    Router vpn = new Router();
                    Router vpn2 = new Router();
                    vpn.setName("sdno_uuid");
                    vpn.setId("id");
                    ExternalGatewayInfo externalGatewayInfo = new ExternalGatewayInfo();
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> externalFixedIps = new HashMap();
                    externalFixedIps.put("ip_address", "1.1.1.1");
                    list.add(externalFixedIps);
                    externalGatewayInfo.setExternalFixedIps(list);
                    vpn.setExternalGatewayInfo(externalGatewayInfo);
                    return (T)vpn;
                }
                return null;
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        HttpServletResponse response = new HttpResponse();
        roaVPC.deleteSubnet(request, null, "ctrlUUID", "12345");
        assertTrue(true);
    }

    @Test(expected = Exception.class)
    public void testDeleteSubnetBranchException() throws ServiceException {
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> List<OverlayUnderlayMapping> getChildren(Class<T> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> list = new ArrayList<>();
                OverlayUnderlayMapping obj1 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj2 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj3 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj4 = new OverlayUnderlayMapping();
                OverlayUnderlayMapping obj5 = new OverlayUnderlayMapping();
                obj1.setUnderlayType("routerId");
                obj2.setUnderlayType("projectId");
                obj3.setUnderlayType("networkId");
                obj4.setUnderlayType("subnetId");
                obj5.setUnderlayType("extra");
                obj1.setAction("d");
                obj2.setAction("a");
                obj3.setAction("action");
                obj4.setAction("action");
                obj5.setAction("action");
                list.add(obj1);
                list.add(obj2);
                list.add(obj3);
                list.add(obj4);
                list.add(obj5);
                return list;
            }
        };
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        new MockUp<EntityUtils>() {

            @Mock
            public String toString(final HttpEntity entity, final Charset defaultCharset)
                    throws IOException, ParseException {
                return "{\"token\": {\"catalog\": [{\"type\":\"network\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}, {\"type\":\"type\",\"endpoints\":[{\"interface\":\"public\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]},{\"type\":\"network\",\"endpoints\":[{\"interface\":\"publics\", \"url\":\"huawai.com\", \"region_id\":\"123\"}]}]}, \"projects\": [], \"networks\": [{\"name\":\"hi\"}], \"routers\": [{\"name\":\"hi\"}]}";
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
        new MockUp<org.openo.sdno.framework.container.util.JsonUtil>() {

            @Mock
            public AuthInfo fromJson(String arg0, Class<AuthInfo> arg1) {
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                return info;
            }
        };
        new MockUp<ObjectMapper>() {

            @Mock
            public <T> T readValue(String content, Class<T> valueType)
                    throws IOException, JsonParseException, JsonMappingException, OpenStackException {
                throw new OpenStackException("404");
            }
        };
        new MockUp<DaoUtil<T>>() {

            @Mock
            public <T> void delete(Class<T> clazz, String uuid) throws ServiceException {
            }
        };
        HttpServletRequest request = new HttpRequest();
        roaVPC.deleteSubnet(request, null, "ctrlUUID", "12345");
    }
}
