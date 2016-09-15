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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackClient;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpInput;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpResult;
import org.openo.sdno.osdriverservice.openstack.client.http.OpenStackHttpConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.Network;
import org.openo.sdno.osdriverservice.openstack.client.model.Project;
import org.openo.sdno.osdriverservice.openstack.client.model.Router;
import org.openo.sdno.osdriverservice.openstack.client.model.Subnet;
import org.openo.sdno.osdriverservice.util.ControllerUtil;
import org.openo.sdno.osdriverservice.util.DaoUtil;

import mockit.Mock;
import mockit.MockUp;

public class VpcSbiServiceTest {

    /*IVpcNbiService service = null;

    Network network = null;

    @Before
    public void setUp() throws Exception {
        this.network = new Network();
        this.network.setAdminStateUp(true);
        this.network.setId("id1234");
        this.network.setName("network123");
        this.network.setProjectId("test/osdriver");
        this.service = new VpcNbiService();
    }

    @Test
    public void testCreateVpcNormal() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getPublicNetwork() throws OpenStackException {
                return VpcSbiServiceTest.this.network;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Project getProject(String projectName) throws OpenStackException {
                Project proj = new Project();
                proj.setDescription("test123");
                proj.setId("test123");
                proj.setName("test123");
                return proj;
            }

            @Mock
            public Router getRounter(String name) throws OpenStackException {
                Router router = new Router();
                Router.ExternalGatewayInfo externalGatewayInfo = new Router.ExternalGatewayInfo();
                List<Map<String, String>> externalFixedIps = new ArrayList<Map<String, String>>();
                Map<String, String> map = new HashMap<String, String>();
                map.put("ip_address", "1.1.1.1");
                externalFixedIps.add(map);
                externalGatewayInfo.setExternalFixedIps(externalFixedIps);
                externalGatewayInfo.setEnableSnat(true);
                externalGatewayInfo.setNetworkId("1234");
                router.setExternalGatewayInfo(externalGatewayInfo);
                router.setId("router123");
                router.setName("router123");
                router.setProjectId("test/osdriver");
                return router;
            }

        };

        OsVpc vpc = new OsVpc();

        OsVpc.Underlays attributes = new OsVpc.Underlays();
        attributes.setProjectId("test/osdriver", "active");
        attributes.setPublicNetworkId("test123", "active");
        attributes.setRouterId("router123", "active");
        vpc.setAttributes(attributes);
        vpc.setCidr("testcidr");
        vpc.setDomainName("com.test");
        vpc.setGatewayIp("1.1.1.1");
        vpc.setOverlayId("test123");
        vpc.setProjectName("test/osdriver");
        OsVpc createdVpc = this.service.createVpc("TEST", vpc);
        assertTrue(createdVpc.getGatewayIp().equals("1.1.1.1"));
    }

    @Test(expected = ServiceException.class)
    public void testCreateVpcException() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getPublicNetwork() throws OpenStackException {
                return VpcSbiServiceTest.this.network;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Project getProject(String projectName) throws OpenStackException {
                Project proj = new Project();
                proj.setDescription("test123");
                proj.setId("test123");
                proj.setName("test123");
                return proj;
            }

            @Mock
            public Router getRounter(String name) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

        };

        OsVpc vpc = new OsVpc();

        OsVpc.Underlays attributes = new OsVpc.Underlays();
        attributes.setProjectId("test/osdriver", "active");
        attributes.setPublicNetworkId("test123", "active");
        attributes.setRouterId("router123", "active");
        vpc.setAttributes(attributes);
        vpc.setCidr("testcidr");
        vpc.setDomainName("com.test");
        vpc.setGatewayIp("1.1.1.1");
        vpc.setOverlayId("test123");
        vpc.setProjectName("test/osdriver");
        this.service.createVpc("TEST", vpc);
    }

    @Test(expected = ServiceException.class)
    public void testCreateVpcExceptionInProjectId() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Project getProject(String projectName) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }
        };

        OsVpc vpc = new OsVpc();

        OsVpc.Underlays attributes = new OsVpc.Underlays();
        attributes.setProjectId("test/osdriver", "active");
        attributes.setPublicNetworkId("test123", "active");
        attributes.setRouterId("router123", "active");
        vpc.setAttributes(attributes);
        vpc.setCidr("testcidr");
        vpc.setDomainName("com.test");
        vpc.setGatewayIp("1.1.1.1");
        vpc.setOverlayId("test123");
        vpc.setProjectName("test/osdriver");
        this.service.createVpc("TEST", vpc);
    }

    @Test
    public void testDeleteVpcNormal() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult delete(final HttpInput input) throws OpenStackException {
                HttpResult result = new HttpResult();
                result.setStatus(200);
                return result;
            }

        };
        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("d"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

            @Mock
            public void delete(Class<OverlayUnderlayMapping> clazz, String uuid) throws ServiceException {
                return;
            }
        };
        new MockUp<ControllerUtil>() {

            @Mock
            public OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException, OpenStackException {
                return new OpenStackClient(new OpenStackCredentials(), "test123");
            }
        };
        this.service.deleteVpc("controller123", "vpc123");
        assertTrue(true);
    }

    @Test
    public void testDeleteVpcInvalidAction() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult delete(final HttpInput input) throws OpenStackException {
                HttpResult result = new HttpResult();
                result.setStatus(200);
                return result;
            }

        };
        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("aa"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

            @Mock
            public void delete(Class<OverlayUnderlayMapping> clazz, String uuid) throws ServiceException {
                return;
            }
        };
        new MockUp<ControllerUtil>() {

            @Mock
            public OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException, OpenStackException {
                return new OpenStackClient(new OpenStackCredentials(), "test123");
            }
        };
        this.service.deleteVpc("controller123", "vpc123");
        assertTrue(true);
    }

    @Test(expected = ServiceException.class)
    public void testDeleteVpcExceptionInDeleteRouter() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void deleteRouter(String routerId) throws OpenStackException {
                throw new OpenStackException(400, "Not found");
            }
        };
        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("aa"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }
        };
        new MockUp<ControllerUtil>() {

            @Mock
            public OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException, OpenStackException {
                return new OpenStackClient(new OpenStackCredentials(), "test123");
            }
        };
        this.service.deleteVpc("controller123", "vpc123");
    }

    @Test
    public void testDeleteVpcExceptionInDeleteRouter2() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void deleteRouter(String routerId) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }
        };
        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("aa"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }
        };
        new MockUp<ControllerUtil>() {

            @Mock
            public OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException, OpenStackException {
                return new OpenStackClient(new OpenStackCredentials(), "test123");
            }
        };
        this.service.deleteVpc("controller123", "vpc123");
        assertTrue(true);
    }

    @Test
    public void testDeleteVpcException() throws ServiceException {
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public HttpResult delete(final HttpInput input) throws OpenStackException {
                HttpResult result = new HttpResult();
                result.setStatus(200);
                return result;
            }

        };
        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for create
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

            @Mock
            public void delete(Class<OverlayUnderlayMapping> clazz, String uuid) throws ServiceException {
                return;
            }
        };
        new MockUp<ControllerUtil>() {

            @Mock
            public OpenStackClient createOpenStackClient(String ctrlUuid) throws ServiceException, OpenStackException {
                return new OpenStackClient(new OpenStackCredentials(), "test123");
            }
        };
        this.service.deleteVpc("controller123", "vpc123");
        assertTrue(true);
    }

    @Test
    public void testCreateSubnetNormal() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) {

                 * Network network = new Network();
                 * network.setAdminStateUp(true); network.setId("id1234");
                 * network.setName("network123");
                 * network.setProjectId("test/osdriver");

                return VpcSbiServiceTest.this.network;
            }

            @Mock
            public Subnet getSubnet(String name) throws OpenStackException {
                Subnet subnet = new Subnet();
                subnet.setCidr("cidr/test");
                subnet.setGatewayIp("1.1.1.1");
                subnet.setId("test123");
                subnet.setName("test123");
                subnet.setNetworkId("network123");
                subnet.setProjectId("org/osdriver");
                return subnet;
            }

            @Mock
            public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        OsSubnet createdSubnet = this.service.createSubnet("TEST", "vpcid", subnet);
        assertTrue(createdSubnet.getGatewayIp().equals("1.1.1.1"));
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubnetException() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.createSubnet("TEST", "vpcid", subnet);
    }

    @Test
    public void testCreateSubnetNormalAdminStateDown() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) {
                Network network = new Network();
                network.setAdminStateUp(false);
                network.setId("id1234");
                network.setName("network123");
                network.setProjectId("test/osdriver");
                return network;
            }

            @Mock
            public Subnet getSubnet(String name) throws OpenStackException {
                Subnet subnet = new Subnet();
                subnet.setCidr("cidr/test");
                subnet.setGatewayIp("1.1.1.1");
                subnet.setId("test123");
                subnet.setName("test123");
                subnet.setNetworkId("network123");
                subnet.setProjectId("org/osdriver");
                return subnet;
            }

            @Mock
            public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        OsSubnet createdSubnet = this.service.createSubnet("TEST", "vpcid", subnet);
        assertTrue(createdSubnet.getGatewayIp().equals("1.1.1.1"));
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubnetExceptionInGetSubnet() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) {
                Network network = new Network();
                network.setAdminStateUp(false);
                network.setId("id1234");
                network.setName("network123");
                network.setProjectId("test/osdriver");
                return network;
            }

            @Mock
            public Subnet getSubnet(String name) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

            @Mock
            public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.createSubnet("TEST", "vpcid", subnet);
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubnetExceptionInAttachSubnet() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) {
                Network network = new Network();
                network.setAdminStateUp(false);
                network.setId("id1234");
                network.setName("network123");
                network.setProjectId("test/osdriver");
                return network;
            }

            @Mock
            public Subnet getSubnet(String name) throws OpenStackException {
                Subnet subnet = new Subnet();
                subnet.setCidr("cidr/test");
                subnet.setGatewayIp("1.1.1.1");
                subnet.setId("test123");
                subnet.setName("test123");
                subnet.setNetworkId("network123");
                subnet.setProjectId("org/osdriver");
                return subnet;
            }

            @Mock
            public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.createSubnet("TEST", "vpcid", subnet);
    }

    @Test
    public void testCreateSubnetExceptionInAttachSubnet2() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public Network getNetwork(String name) {
                Network network = new Network();
                network.setAdminStateUp(false);
                network.setId("id1234");
                network.setName("network123");
                network.setProjectId("test/osdriver");
                return network;
            }

            @Mock
            public Subnet getSubnet(String name) throws OpenStackException {
                Subnet subnet = new Subnet();
                subnet.setCidr("cidr/test");
                subnet.setGatewayIp("1.1.1.1");
                subnet.setId("test123");
                subnet.setName("test123");
                subnet.setNetworkId("network123");
                subnet.setProjectId("org/osdriver");
                return subnet;
            }

            @Mock
            public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
                throw new OpenStackException(400, "Not found");
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        OsSubnet createdSubnet = this.service.createSubnet("TEST", "vpcid", subnet);
        assertTrue(createdSubnet.getGatewayIp().equals("1.1.1.1"));
    }

    @Test
    public void testDeleteSubnetNormal() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
        assertTrue(true);
    }

    @Test
    public void testDeleteSubnetExceptionInDeleteSubnetFromRouter() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                throw new OpenStackException(400, "Not found");
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
        assertTrue(true);
    }

    @Test(expected = ServiceException.class)
    public void testDeleteSubnetExceptionInDeleteSubnetFromRouter2() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
        assertTrue(true);
    }

    @Test
    public void testDeleteSubnetExceptionInDeleteSubnet() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
        assertTrue(true);
    }

    @Test(expected = ServiceException.class)
    public void testDeleteSubnetExceptionInDeleteSubnet2() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                throw new OpenStackException(400, "Not found");
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                return;
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteSubnetExceptionInDeleteNetwork() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                throw new OpenStackException(400, "Not found");
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
    }

    @Test
    public void testDeleteSubnetExceptionInDeleteNetwork2() throws ServiceException {

        new MockUp<DaoUtil>() {

            @Mock
            public List<OverlayUnderlayMapping> getChildren(Class<OverlayUnderlayMapping> clazz, String overlayId)
                    throws ServiceException {
                List<OverlayUnderlayMapping> mappingList = new ArrayList<OverlayUnderlayMapping>();
                OverlayUnderlayMapping mapping = new OverlayUnderlayMapping();
                mapping.setAction("c"); // for delete
                mapping.setControllerId("TEST");
                mapping.setOverlayId("overlay123");
                mapping.setUnderlayId("underlay123");
                mapping.setUnderlayTenantId("test123");
                mapping.setUnderlayType("None");
                mapping.setUuid("uuid123");
                mappingList.add(mapping);
                return mappingList;
            }

        };
        new MockUp<OpenStackHttpConnection>() {

            @Mock
            public void login() throws OpenStackException {
                return;
            }

        };
        new MockUp<OpenStackClient>() {

            @Mock
            public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteSubnet(String subnetId) throws OpenStackException {
                return;
            }

            @Mock
            public void deleteNetwork(String networkId) throws OpenStackException {
                throw new OpenStackException(404, "Not found");
            }

        };

        OsSubnet subnet = new OsSubnet();

        OsSubnet.Underlays attributes = new OsSubnet.Underlays();
        attributes.setVpcNetworkId("vpcNetworkId", "active");
        attributes.setRouterId("router123", "active");
        attributes.setProjectId("projectId", "active");
        attributes.setVpcSubnetId("vpcsubnetid", "active");

        subnet.setAttributes(attributes);
        subnet.setCidr("testcidr");
        subnet.setGatewayIp("1.1.1.1");
        subnet.setOverlayId("test123");
        subnet.setAdminStatus("active");

        this.service.deleteSubnet("TEST", "subnetid");
        assertTrue(true);
    }
*/
}
