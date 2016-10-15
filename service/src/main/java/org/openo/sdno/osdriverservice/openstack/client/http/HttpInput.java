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

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.osdriverservice.openstack.client.OpenStackServiceContext;

/**
 * Captures HTTP request URI, body and request parameters
 * <br>
 *
 * @author
 * @version SDNO 0.5 July 31, 2016
 */
public class HttpInput {

    private String uri;

    private String body;

    private String method;

    private Map<String, String> reqHeaders = new HashMap<>();

    private OpenStackServiceContext context;

    public String getUri() {
        return this.uri;
    }

    public HttpInput setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public HttpInput setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getReqHeaders() {
        return this.reqHeaders;
    }

    public HttpInput setReqHeaders(Map<String, String> reqHeaders) {
        this.reqHeaders = reqHeaders;
        return this;
    }

    public OpenStackServiceContext getContext() {
        return this.context;
    }

    public HttpInput setContext(OpenStackServiceContext context) {
        this.context = context;
        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public HttpInput setMethod(String method) {
        this.method = method;
        return this;
    }
}
