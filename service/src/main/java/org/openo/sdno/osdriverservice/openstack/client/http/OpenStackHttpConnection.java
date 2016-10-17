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

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackCredentials;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceContext;
import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceType;
import org.openo.sdno.osdriverservice.openstack.client.exception.OpenStackException;
import org.openo.sdno.osdriverservice.openstack.utils.HttpGateKeeper;
import org.openo.sdno.osdriverservice.util.TrustAllX509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Helps to login/logout and sending restful requests to OpenStack.<br>
 * By default, its uses keystone v3 and http connection.
 *
 * @author
 * @version SDNO 0.5 2016-6-22
 */
public class OpenStackHttpConnection {

    private static final String URI_LOGIN = "/v3/auth/tokens";

    private static final String SSLCONTEST_TLS = "TLSV1.2";

    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenStackHttpConnection.class);

    // TODO(mrkanag) Pls keep cache of clients

    /*
     * Captures the list of endpoints from the auth token as part of login
     */
    static class EndpointCache {

        private Map<OpenStackServiceType, String> endpoints = new EnumMap<>(OpenStackServiceType.class);

        public EndpointCache() {
            // Empty constructor kept empty on purpose...
        }

        /**
         * Adds end point
         * <br>
         *
         * @param serviceType
         * @param url
         * @return
         * @since SDNO 0.5
         */
        public OpenStackHttpConnection.EndpointCache addEndpoint(OpenStackServiceType serviceType, String url) {
            this.endpoints.put(serviceType, url);
            return this;
        }

        /**
         * Returns End point url for given service type
         * <br>
         *
         * @param serviceType
         * @return
         * @since SDNO 0.5
         */
        public String getEndpointFor(OpenStackServiceType serviceType) {
            return this.endpoints.get(serviceType);
        }
    }

    /*
     * Captures the endpoints for given region(s)
     */
    static class RegionCache {

        private Map<String, OpenStackHttpConnection.EndpointCache> regions = new HashMap<>();

        public RegionCache() {
            // Kept blank on purpose...
        }

        /**
         * Adds given end point cache for a given region.
         * <br>
         *
         * @param region
         * @param epCache
         * @return
         * @since SDNO 0.5
         */
        public OpenStackHttpConnection.RegionCache addEndpointCache(String region,
                OpenStackHttpConnection.EndpointCache epCache) {
            this.regions.put(region, epCache);
            return this;
        }

        /**
         * Retrieves end point cache for a given region.
         * <br>
         *
         * @param region
         * @return
         * @since SDNO 0.5
         */
        public OpenStackHttpConnection.EndpointCache getEndpointCacheFor(String region) {
            if(this.regions.containsKey(region)) {
                return this.regions.get(region);
            }
            OpenStackHttpConnection.EndpointCache epCache = new OpenStackHttpConnection.EndpointCache();
            this.addEndpointCache(region, epCache);

            return epCache;
        }
    }

    private HttpClient httpClient = null;

    private OpenStackCredentials credentials = null;

    private OpenStackHttpConnection.RegionCache regionCache = null;

    // TODO(mrkanag) create adaptor for this one instead.
    private static String V3_TOKEN_BODY_DOMAIN_SCOPE =
            "{\"auth\": {\"identity\": {\"methods\": [\"password\"],\"password\": {"
                    + "\"user\": {\"name\": \"%s\",\"password\": \"%s\",\"domain\": {"
                    + "\"id\": \"%s\"} }, \"scope\": {\"domain\": {\"id\": \"%s\"} } } } } }";

    /**
     * Constructor<br>
     *
     * @param creds {@link OpenStackCredentials}
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public OpenStackHttpConnection(OpenStackCredentials creds) throws OpenStackException {
        try {
            if(creds.isSecured()) {
                SSLContext sslContext = SSLContext.getInstance(SSLCONTEST_TLS);
                sslContext.init(null, new TrustManager[] {new TrustAllX509TrustManager()},
                        new java.security.SecureRandom());
                X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                        .<ConnectionSocketFactory> create()
                        .register("https", new SSLConnectionSocketFactory(sslContext, hostnameVerifier)).build();
                HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

                this.httpClient = HttpClients.custom().setConnectionManager(connManager)
                        .setRedirectStrategy(new LaxRedirectStrategy()).build();
            } else {
                this.httpClient = HttpClients.createDefault();
            }
        } catch(Exception e) {
            LOGGER.error("Excepton failed. ", e);
            throw new OpenStackException(e);
        }

        this.credentials = creds;
        this.regionCache = new OpenStackHttpConnection.RegionCache();
    }

    /**
     * Login to OpenStack
     * <br>
     *
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public void login() throws OpenStackException {
        if(!this.credentials.isValid()) {
            LOGGER.error("invalid credentials failed. ");
            throw new OpenStackException("Invalid credentials");
        }

        HttpInput input = new HttpInput().setUri(this.getAuthUrl() + URI_LOGIN).setMethod("post")
                .setBody(String.format(V3_TOKEN_BODY_DOMAIN_SCOPE, this.credentials.getUsername(),
                        this.credentials.getPassword(), this.credentials.getDomain(), this.credentials.getDomain()));
        HttpResult result = this.commonRequest(input, true);

        HttpGateKeeper.add(input, result);

        if((result.getStatus() == HttpStatus.SC_OK) || (result.getStatus() == HttpStatus.SC_CREATED)) {
            this.credentials.setToken(result.getRespHeaders().get("X-Subject-Token"));
            this.populateEndpointCache(result.getBody());
            LOGGER.info("Login Successful.");
        } else {
            LOGGER.error("Login failed. " + result.getBody());
            throw new OpenStackException(result.getStatus(), "Login failed");
        }
    }

    private void populateEndpointCache(String token) throws OpenStackException {
        JSONArray catalogs = JSONObject.fromObject(token).getJSONObject("token").getJSONArray("catalog");

        for(int i = 0; i < catalogs.size(); i++) {
            OpenStackServiceType supportedServiceType =
                    OpenStackServiceType.find(catalogs.getJSONObject(i).getString("type"));

            if(supportedServiceType != null) {
                JSONArray endpoints = catalogs.getJSONObject(i).getJSONArray("endpoints");
                for(int j = 0; j < endpoints.size(); j++) {
                    String interf = endpoints.getJSONObject(j).getString("interface");

                    if("public".equals(interf)) {
                        String url = endpoints.getJSONObject(j).getString("url");
                        String region = endpoints.getJSONObject(j).getString("region_id");

                        this.regionCache.getEndpointCacheFor(region).addEndpoint(supportedServiceType, url);
                        break;
                    }
                }
            }
        }
    }

    private Map<String, String> getHttpHeaders(HttpResponse resp) {
        Map<String, String> result = new HashMap<>();

        Header[] hs = resp.getAllHeaders();
        for(int i = 0; i < hs.length; i++) {
            result.put(hs[i].getName(), hs[i].getValue());
        }

        return result;
    }

    private String getResponseBody(HttpResponse resp) throws OpenStackException {
        if(resp.getEntity() == null) {
            return null;
        }
        try {
            String body = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
            EntityUtils.consume(resp.getEntity());
            return body;
        } catch(IOException e) {
            LOGGER.error("IOExcepton failed. ", e);
            throw new OpenStackException(e);
        }
    }

    private String getEndpointUrl(OpenStackServiceContext ctx) {
        return this.regionCache.getEndpointCacheFor(ctx.getRegion()).getEndpointFor(ctx.getServiceType());
    }

    private String getAbsoluteUrl(OpenStackServiceContext ctx, String restUri) {
        return this.getEndpointUrl(ctx) + "/" + ctx.getVersion().getVersion() + restUri;
    }

    private String getAuthUrl() {
        String protocol = "http";
        if(this.credentials.isSecured()) {
            protocol += "s";
        }
        return protocol + "://" + this.credentials.getIp() + ":" + this.credentials.getPort();
    }

    private StringEntity getStringEntity(HttpInput input) {
        return new StringEntity(input.getBody(), StandardCharsets.UTF_8);
    }

    /**
     * Post
     * <br>
     *
     * @param input
     * @return
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public HttpResult post(final HttpInput input) throws OpenStackException {
        input.setMethod("post");
        return this.commonRequest(input);
    }

    /**
     * Get
     * <br>
     *
     * @param input
     * @return
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public HttpResult get(final HttpInput input) throws OpenStackException {
        input.setMethod("get");
        return this.commonRequest(input);
    }

    /**
     * Put
     * <br>
     *
     * @param input
     * @return
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public HttpResult put(final HttpInput input) throws OpenStackException {
        input.setMethod("put");
        return this.commonRequest(input);
    }

    /**
     * Delete
     * <br>
     *
     * @param input
     * @return
     * @throws OpenStackException
     * @since SDNO 0.5
     */
    public HttpResult delete(final HttpInput input) throws OpenStackException {
        input.setMethod("delete");
        return this.commonRequest(input);
    }

    private void addCommonHeaders(HttpInput input) {
        input.getReqHeaders().put("Content-Type", APPLICATION_JSON);
        input.getReqHeaders().put("Accept", APPLICATION_JSON);
        input.getReqHeaders().put("X-Auth-Token", this.credentials.getToken());
    }

    private HttpResult commonRequest(HttpInput input) throws OpenStackException {
        return this.commonRequest(input, false);
    }

    private HttpResult commonRequest(HttpInput input, boolean fromLogin) throws OpenStackException {
        LOGGER.info("HTTP Request :" + input);

        if(!fromLogin) {
            if(this.credentials.getToken() == null) {
                this.login();
            }
            input.setUri(this.getAbsoluteUrl(input.getContext(), input.getUri()));
        }

        this.addCommonHeaders(input);

        HttpRequestBase requestBase = null;
        if("post".equals(input.getMethod())) {
            HttpPost httpPost = new HttpPost();
            httpPost.setEntity(this.getStringEntity(input));
            requestBase = httpPost;
        } else if("put".equals(input.getMethod())) {
            HttpPut httpPut = new HttpPut();
            httpPut.setEntity(this.getStringEntity(input));
            requestBase = httpPut;
        } else if("get".equals(input.getMethod())) {
            requestBase = new HttpGet();
        } else if("delete".equals(input.getMethod())) {
            requestBase = new HttpDelete();
        } else {
            LOGGER.error("IllegalArgumentException failed. ");
            throw new IllegalArgumentException("Invalid HTTP method");
        }

        requestBase.setURI(URI.create(input.getUri()));

        for(Entry<String, String> h : input.getReqHeaders().entrySet()) {
            requestBase.addHeader(h.getKey(), h.getValue());
        }

        HttpResult result = new HttpResult();

        try {
            HttpResponse resp = this.httpClient.execute(requestBase);
            String respContent = this.getResponseBody(resp);
            LOGGER.info("HTTP Request body: " + requestBase);
            result.setBody(respContent);
            result.setStatus(resp.getStatusLine().getStatusCode());
            result.setRespHeaders(this.getHttpHeaders(resp));
        } catch(ParseException | IOException e) {
            LOGGER.error("HTTP " + requestBase + " failed due to " + e.getMessage());
            throw new OpenStackException(e);
        } finally {
            HttpGateKeeper.add(input, result);
        }

        LOGGER.info("HTTP Response" + result);
        return result;
    }
}
