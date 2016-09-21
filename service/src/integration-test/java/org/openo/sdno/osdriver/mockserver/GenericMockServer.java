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

package org.openo.sdno.osdriver.mockserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;

public class GenericMockServer extends MocoHttpServer {

    private List<String> mockJsons = new ArrayList();

    public GenericMockServer(){
        super();
    }

    public void addMockJsons(String [] jsons) {
        this.mockJsons.addAll(Arrays.asList(jsons));
    }

    @Override
    public void addRequestResponsePairs() {
        for(String file : mockJsons) {
            this.addRequestResponsePair(file, new MockResponseHandler());
        }
    }

    private class MockResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {
            HttpRequest req = httpObject.getRequest();
            HttpResponse res = httpObject.getResponse();

            System.out.println(req);
            System.out.println(res);

        }
    }

}
