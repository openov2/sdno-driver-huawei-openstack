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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;

public class CloseableHttpResponseMock implements CloseableHttpResponse{

    private int statusCode;
    StatusLine statusline;
    @Override
    public StatusLine getStatusLine() {
        // TODO Auto-generated method stub
        
        return statusline;
    }

    @Override
    public void setStatusLine(StatusLine statusline) {
        // TODO Auto-generated method stub
        this.statusline = statusline;
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code, String reason) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatusCode(int code) throws IllegalStateException {
        // TODO Auto-generated method stub
        this.statusCode = code;
    }
    
    public int getStatusCode() throws IllegalStateException {
        return statusCode;
        
    }

    @Override
    public void setReasonPhrase(String reason) throws IllegalStateException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HttpEntity getEntity() {
        // TODO Auto-generated method stub
        return new HttpEntity() {
            
            @Override
            public void writeTo(OutputStream outstream) throws IOException {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean isStreaming() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isRepeatable() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean isChunked() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public Header getContentType() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public long getContentLength() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Header getContentEncoding() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public InputStream getContent() throws IOException, IllegalStateException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public void consumeContent() throws IOException {
                // TODO Auto-generated method stub
                
            }
        };
    }

    @Override
    public void setEntity(HttpEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLocale(Locale loc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsHeader(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Header[] getHeaders(String name) {
        // TODO Auto-generated method stub
        
        
        return null;
    }

    @Override
    public Header getFirstHeader(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Header getLastHeader(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Header[] getAllHeaders() {
        // TODO Auto-generated method stub
        Header[] headers = {
                        new BasicHeader("Content-type", "application/x-www-form-urlencoded")
                        ,new BasicHeader("Content-type", "application/x-www-form-urlencoded")
                        ,new BasicHeader("Accep", "text/html,text/xml,application/xml")
                        ,new BasicHeader("Connection", "keep-alive")
                        ,new BasicHeader("keep-alive", "115")
                        ,new BasicHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                    };
        return headers;
    }

    @Override
    public void addHeader(Header header) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addHeader(String name, String value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeader(Header header) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeader(String name, String value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeaders(Header[] headers) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeHeader(Header header) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeHeaders(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HeaderIterator headerIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HeaderIterator headerIterator(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpParams getParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParams(HttpParams params) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        
    }

}
