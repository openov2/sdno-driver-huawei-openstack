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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.openo.sdno.osdriverservice.openstack.client.exception.AlreadyExistException;
import org.openo.sdno.osdriverservice.openstack.client.exception.NotFoundException;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpInput;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpResult;
import org.openo.sdno.osdriverservice.openstack.client.http.OpenStackHttpConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.Network;
import org.openo.sdno.osdriverservice.openstack.client.model.Project;
import org.openo.sdno.osdriverservice.openstack.client.model.Router;
import org.openo.sdno.osdriverservice.openstack.client.model.Subnet;
import org.openo.sdno.osdriverservice.openstack.client.model.SubnetAttachment;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIkePolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecPolicy;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnIpSecSiteConnection;
import org.openo.sdno.osdriverservice.openstack.client.model.VpnService;
import org.openo.sdno.osdriverservice.openstack.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * OpenStack client.<br>
 *
 * @version SDNO 0.5 2016-6-20
 */
public class OpenStackClient {

    private static final String URI_NETWORK = "/networks";

    private static final String URI_SUBNET = "/subnets";

    private static final String URI_ROUTER = "/routers";

    private static final String URI_ROUTER_INTERFACE_ADD = URI_ROUTER + "/{0}/add_router_interface";

    private static final String URI_ROUTER_INTERFACE_REMOVE = URI_ROUTER + "/{0}/remove_router_interface";

    private static final String URI_PROJECT = "/projects";

    private static final String URI_AUTH_PROJECT = "/auth/projects";

    private static final String URI_IKE_POLICY = "/vpn/ikepolicies";

    private static final String URI_IPSEC_POLICY = "/vpn/ipsecpolicies";

    private static final String URI_VPN_SERVICE = "/vpn/vpnservices";

    private static final String URI_IPSEC_SITE_CONN = "/vpn/ipsec-site-connections";

    private static final OpenStackServiceContext NEWORK_V2 = new OpenStackServiceContext()
            .setVersion(OpenStackServiceApiVersion.V2).setServiceType(OpenStackServiceType.NETOWRK);

    private static final OpenStackServiceContext IDENTITY_V3 = new OpenStackServiceContext()
            .setVersion(OpenStackServiceApiVersion.V3).setServiceType(OpenStackServiceType.IDENTITY);

    /**
     * OpenStack HTTP connection used to connect with
     * given OpenStack using the credentials.
     */
    private OpenStackHttpConnection osConnection = null;

    /**
     * OpenStack region name.
     */
    private String regionName = null;

    /**
     * Constructor.<br>
     *
     * @param credentials OpenStackCredentials
     * @param regionName OpenStack region name
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public OpenStackClient(OpenStackCredentials credentials, String regionName) throws OpenStackException {
        this.osConnection = new OpenStackHttpConnection(credentials);
        this.setRegionName(regionName);
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    private void validateResponse(HttpResult response) throws OpenStackException {
        if(response.getStatus() >= HttpStatus.SC_OK && response.getStatus() <= HttpStatus.SC_NO_CONTENT) {
            return;
        } else if(response.getStatus() == HttpStatus.SC_NOT_FOUND) {
            throw new NotFoundException(response.getBody());
        } else if(response.getStatus() == HttpStatus.SC_CONFLICT) {
            throw new AlreadyExistException(response.getBody());
        } else {
            throw new OpenStackException(response.getStatus(), response.getBody());
        }
    }

    /**
     * Login in OpenStack Controller.<br>
     *
     * @throws OpenStackException when login failed
     * @since SDNO 0.5
     */
    public void login() throws OpenStackException {
        this.osConnection.login();
    }

    /**
     * Login out OpenStack Controller.<br>
     *
     * @throws OpenStackException when logout failed
     * @since SDNO 0.5
     */
    public void logout() throws OpenStackException {
        // TODO(mrkanag) add logout!!
    }

    /**
     * Helps to make the HTTP POST action, in which
     * the entity is in the form of {"entity-name" : {...}}
     * Example {"network": {"name:" "sample-nw"}}
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack service resource Uri
     * @param entity OpenStack resource object
     * @return OpenStack resource created as part of this action.
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    private <T> T post(OpenStackServiceContext context, String uri, T entity) throws OpenStackException {
        String body = JsonUtil.toJson(entity);

        HttpResult result = this.post(context, uri, body);

        return (T)JsonUtil.fromJson(result.getBody(), entity.getClass(), true);
    }

    /**
     * Helps to make POST action with given body in string format.
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack service resource Uri
     * @param entity OpenStack resource object
     * @return resource created as part of this action.
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    private HttpResult post(OpenStackServiceContext context, String uri, String entity) throws OpenStackException {
        context.setRegion(this.regionName);

        HttpInput input = new HttpInput().setContext(context).setBody(entity).setUri(uri);
        HttpResult result = this.osConnection.post(input);
        this.validateResponse(result);

        return result;
    }

    /**
     * Helps to make PUT action with given body
     * in the form of "{....}", Here no entity-name
     * is given as part of the body.
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack resource uri
     * @param entity OpenStack resource entity object
     * @return updated OpenStack entity
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    private <T> T put(OpenStackServiceContext context, String uri, T entity) throws OpenStackException {
        context.setRegion(this.regionName);

        HttpInput input = new HttpInput().setContext(context).setBody(JsonUtil.toJson(entity, false)).setUri(uri);
        HttpResult result = this.osConnection.put(input);
        this.validateResponse(result);

        return (T)JsonUtil.fromJson(result.getBody(), entity.getClass());
    }

    /**
     * Helps to make GET action for listing the given Uri.
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack resource uri
     * @param cls OpenStack model entity class
     * @param collectionName Collection name reported in OpenStack
     *            list REST API
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    private <T> List<T> list(OpenStackServiceContext context, String uri, Class<T> cls, String collectionName)
            throws OpenStackException {
        context.setRegion(this.regionName);

        HttpInput input = new HttpInput().setContext(context).setUri(uri);
        HttpResult result = this.osConnection.get(input);
        this.validateResponse(result);

        JSONArray results = JSONObject.fromObject(result.getBody()).getJSONArray(collectionName);
        List<T> list = new ArrayList<>();

        for(int i = 0; i < results.size(); i++) {
            list.add(JsonUtil.fromJson(results.getJSONObject(i).toString(), cls));
        }
        return list;
    }

    /**
     * Helps to get a OpenStack entity, which is available in
     * only listing REST API.
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack resource uri with required query parameters
     * @param cls OpenStack model entity class
     * @param collectionName Collection name reported in OpenStack
     *            list REST API
     * @return return the given object which is one in number.
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    private <T> T getByList(OpenStackServiceContext context, String uri, Class<T> cls, String collectionName)
            throws OpenStackException {
        List<T> list = this.list(context, uri, cls, collectionName);

        if(list.size() == 1) {
            return list.get(0);
        } else if(list.isEmpty()) {
            throw new NotFoundException("Not Found");
        }

        throw new AlreadyExistException("Already exists");
    }

    /**
     * Helps to DELETE action on the given uri.
     * <br>
     *
     * @param context {@link OpenStackServiceContext}
     * @param uri OpenStack resource uri
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    private void delete(OpenStackServiceContext context, String uri) throws OpenStackException {
        context.setRegion(this.regionName);

        HttpInput input = new HttpInput().setContext(context).setUri(uri);
        HttpResult result = this.osConnection.delete(input);
        this.validateResponse(result);
    }

    /**
     * Creates OpenStack Project .
     * Version {@value #IDENTITY_V3}
     * URI: {@value #URI_PROJECT}
     * <br>
     *
     * @param project {@link Project}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Project createProject(Project project) throws OpenStackException {
        return this.post(OpenStackClient.IDENTITY_V3, OpenStackClient.URI_PROJECT, project);
    }

    /**
     * Get given OpenStack project.
     * Version {@value #IDENTITY_V3}
     * URI: {@value #URI_PROJECT}?name=<projectName>
     * <br>
     *
     * @param projectName project name
     * @return OpenStack project
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Project getProject(String projectName) throws OpenStackException {
        return this.getByList(OpenStackClient.IDENTITY_V3, OpenStackClient.URI_AUTH_PROJECT + "?name=" + projectName,
                Project.class, "projects");
    }

    /**
     * Deletes OpenStack project.
     * Version {@value #IDENTITY_V3}
     * URI: {@value #URI_PROJECT}/<projectId>
     * <br>
     *
     * @param projectId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteProject(String projectId) throws OpenStackException {
        this.delete(OpenStackClient.IDENTITY_V3, OpenStackClient.URI_PROJECT + "/" + projectId);
    }

    /**
     * Creates OpenStack network.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_NETWORK}
     * <br>
     *
     * @param network {@link Network}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Network createNetwork(Network network) throws OpenStackException {
        return this.post(OpenStackClient.NEWORK_V2, OpenStackClient.URI_NETWORK, network);
    }

    /**
     * Deletes OpenStack network.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_NETWORK}
     * <br>
     *
     * @param networkId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteNetwork(String networkId) throws OpenStackException {
        this.delete(OpenStackClient.NEWORK_V2, OpenStackClient.URI_NETWORK + "/" + networkId);
    }

    /**
     * Gets OpenStack network.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_NETWORK?name=<name>}
     * <br>
     *
     * @param name OpenStack network name.
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Network getNetwork(String name) throws OpenStackException {
        return this.getByList(OpenStackClient.NEWORK_V2, OpenStackClient.URI_NETWORK + "?name=" + name, Network.class,
                "networks");
    }

    /**
     * Gets OpenStack public network.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_NETWORK}?router:external=true
     * <br>
     *
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Network getPublicNetwork() throws OpenStackException {
        return this.getByList(OpenStackClient.NEWORK_V2, OpenStackClient.URI_NETWORK + "?router:external=true",
                Network.class, "networks");
    }

    /**
     * Creates OpenStack Subnet.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_SUBNET}
     * <br>
     *
     * @param subnet {@link Subnet}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Subnet createSubnet(Subnet subnet) throws OpenStackException {
        return this.post(OpenStackClient.NEWORK_V2, OpenStackClient.URI_SUBNET, subnet);
    }

    /**
     * Gets OpenStack Subnet.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_SUBNET}?name=<name>
     * <br>
     *
     * @param name OpenStack Subnet name
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Subnet getSubnet(String name) throws OpenStackException {
        return this.getByList(OpenStackClient.NEWORK_V2, OpenStackClient.URI_SUBNET + "?name=" + name, Subnet.class,
                "subnets");
    }

    /**
     * Deletes OpenStack Subnet
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_SUBNET}/<subnetId>
     * <br>
     *
     * @param subnetId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteSubnet(String subnetId) throws OpenStackException {
        this.delete(OpenStackClient.NEWORK_V2, OpenStackClient.URI_SUBNET + "/" + subnetId);
    }

    /**
     * Gets OpenStack router.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_ROUTER}
     * <br>
     *
     * @param name OpenStack router name.
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Router getRouter(String name) throws OpenStackException {
        List<Router> routers =
                this.list(OpenStackClient.NEWORK_V2, OpenStackClient.URI_ROUTER, Router.class, "routers");

        for(int i = 0; i < routers.size(); i++) {
            if(routers.get(i).getName().equals(name)) {
                return routers.get(i);
            }
        }

        throw new NotFoundException("Router " + name + " does not found");
    }

    /**
     * Creates OpenStack Router.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_ROUTER}
     * <br>
     *
     * @param router {@link Router}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public Router createRouter(Router router) throws OpenStackException {
        return this.post(OpenStackClient.NEWORK_V2, OpenStackClient.URI_ROUTER, router);
    }

    /**
     * Deletes OpenStack Router.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_ROUTER}/<routerId>
     * <br>
     *
     * @param routerId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteRouter(String routerId) throws OpenStackException {
        this.delete(OpenStackClient.NEWORK_V2, OpenStackClient.URI_ROUTER + "/" + routerId);
    }

    private void handleSubnetToRouterInterface(String uri, Router router, Subnet subnet) throws OpenStackException {
        this.put(OpenStackClient.NEWORK_V2, MessageFormat.format(uri, router.getId()),
                new SubnetAttachment().setSubnetId(subnet.getId()));
    }

    /**
     * Attaches given OpenStack Subnet with Router
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_ROUTER_INTERFACE_ADD}
     * <br>
     *
     * @param router {@link Router}
     * @param subnet {@link Subnet}
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void attachSubnetToRouter(Router router, Subnet subnet) throws OpenStackException {
        this.handleSubnetToRouterInterface(OpenStackClient.URI_ROUTER_INTERFACE_ADD, router, subnet);
    }

    /**
     * Detaches given OpenStack Subnet from Router.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_ROUTER_INTERFACE_REMOVE}
     * <br>
     *
     * @param router {@link Router}
     * @param subnet {@link Subnet}
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void detachSubnetFromRouter(Router router, Subnet subnet) throws OpenStackException {
        this.handleSubnetToRouterInterface(OpenStackClient.URI_ROUTER_INTERFACE_REMOVE, router, subnet);
    }

    /**
     * Creates OpenStack VPN IpSecPolicy.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IPSEC_POLICY}
     * <br>
     *
     * @param policy {@link VpnIpSecPolicy}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public VpnIpSecPolicy createVpnIpSecPolicy(VpnIpSecPolicy policy) throws OpenStackException {
        return this.post(NEWORK_V2, URI_IPSEC_POLICY, policy);

    }

    /**
     * Deletes OpenStack VPN IpSecPolicy.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IPSEC_POLICY}/<policyId>
     * <br>
     *
     * @param policyId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteVpnIpSecPolicy(String policyId) throws OpenStackException {
        this.delete(NEWORK_V2, URI_IPSEC_POLICY + "/" + policyId);
    }

    /**
     * Creates OpenStack VPN IkePolicy.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IKE_POLICY}
     * <br>
     *
     * @param policy {@link VpnIkePolicy}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public VpnIkePolicy createVpnIkePolicy(VpnIkePolicy policy) throws OpenStackException {
        return this.post(NEWORK_V2, URI_IKE_POLICY, policy);
    }

    /**
     * Deletes OpenStack VPN IkePolicy.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IKE_POLICY}/<policyId>
     * <br>
     *
     * @param policyId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteVpnIkePolicy(String policyId) throws OpenStackException {
        this.delete(NEWORK_V2, URI_IKE_POLICY + "/" + policyId);
    }

    /**
     * Creates OpenStack VPN Service.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_VPN_SERVICE}
     * <br>
     *
     * @param service {@link VpnService}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public VpnService createVpnService(VpnService service) throws OpenStackException {
        return this.post(NEWORK_V2, URI_VPN_SERVICE, service);
    }

    /**
     * Deletes OpenStack VPN Service.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_VPN_SERVICE}/<serviceId>
     * <br>
     *
     * @param serviceId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteVpnService(String serviceId) throws OpenStackException {
        this.delete(NEWORK_V2, URI_VPN_SERVICE + "/" + serviceId);
    }

    /**
     * Creates OpenStack VPN IpSec site connection.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IPSEC_SITE_CONN}
     * <br>
     *
     * @param connection {@link VpnIpSecSiteConnection}
     * @return
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public VpnIpSecSiteConnection createVpnIpSecSiteConnection(VpnIpSecSiteConnection connection)
            throws OpenStackException {
        return this.post(NEWORK_V2, URI_IPSEC_SITE_CONN, connection);

    }

    /**
     * Deletes OpenStack VPN IpSec site connection.
     * Version {@value #NETWORK_V2}
     * URI: {@value #URI_IPSEC_SITE_CONN}/<connId>
     * <br>
     *
     * @param connId
     * @throws OpenStackException Throws exception if the operation fails.
     * @since SDNO 0.5
     */
    public void deleteVpnIpSecSiteConnection(String connId) throws OpenStackException {
        this.delete(NEWORK_V2, URI_IPSEC_SITE_CONN + "/" + connId);
    }
}
