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

/**
 * OpenStack credentials required to connect to OpenStack
 * cloud.
 * By default, it uses HTTP port 5000 on default OpenStack domain.
 * <br>
 *
 * @author
 * @version SDNO 0.5 July 31, 2016
 */
public class OpenStackCredentials {

    private String ip = null;

    private String port = "5000";

    private String username = null;

    private String password = null;

    private String domain = "default";

    private boolean secured = false;

    private String token = null;

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    public OpenStackCredentials() {
        // Default constructor.
    }

    /**
     * Constructor<br>
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     * @since SDNO 0.5
     */
    public OpenStackCredentials(String ip, String port, String username, String password) {
        super();
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getIp() {
        return this.ip;
    }

    public OpenStackCredentials setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getPort() {
        return this.port;
    }

    public OpenStackCredentials setPort(String port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public OpenStackCredentials setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public OpenStackCredentials setPassword(String password) {
        this.password = password.replace("\\", "\\\\").replace("\"", "\\\"");
        return this;
    }

    public String getDomain() {
        return this.domain;
    }

    public OpenStackCredentials setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public boolean isSecured() {
        return this.secured;
    }

    public OpenStackCredentials setSecured(boolean secured) {
        this.secured = secured;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public OpenStackCredentials setToken(String token) {
        this.token = token;
        return this;
    }

    public boolean isValid() {
        return (null != this.ip) && (null != this.username) && (null != this.password) && (null != this.port)
                && (null != this.domain);
    }
}
