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

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class HttpClientMock extends CloseableHttpClient{

    @Override
    public HttpParams getParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
            throws IOException, ClientProtocolException {
        // TODO Auto-generated method stub
        return null;
    }




}
