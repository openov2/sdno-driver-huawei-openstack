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

package org.openo.sdno.osdriver.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;

import org.openo.sdno.osdriver.mockserver.OpenStackSuccessMockServer;

import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.testmanager.TestManager;

public class ITSuccessTest extends TestManager {

    private static final String CREATE_VPC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/createvpcsuccess.json";

    private static final String CREATE_SUBNET_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/createsubnetsuccess.json";

    private static final String DELETE_VPC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/deletevpcsuccess.json";

    private static final String DELETE_SUBNET_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/deletesubnetsuccess.json";

    private static final String CREATE_IPSEC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/createipsecsuccess.json";

    private static final String DELETE_IPSEC_SUCCESS_TESTCASE =
            "src/integration-test/resources/testcase_jsons/deleteipsecsuccess.json";

    private static OpenStackSuccessMockServer sbiAdapterServer = new OpenStackSuccessMockServer();

    @BeforeClass
    public static void setup() throws ServiceException {
        sbiAdapterServer.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        sbiAdapterServer.stop();
    }

    private void checkVpcCreate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPC_SUCCESS_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());
    }

    private void checkVpcDelete() throws ServiceException {
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPC_SUCCESS_TESTCASE);
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        execTestCase(deleteRequest, new SuccessChecker());
    }

    private void checkSubnetCreate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_SUBNET_SUCCESS_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());
    }

    private void checkSubnetDelete() throws ServiceException {
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_SUBNET_SUCCESS_TESTCASE);
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        execTestCase(deleteRequest, new SuccessChecker());
    }

    private void checkIpSecCreate() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_IPSEC_SUCCESS_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());
    }

    private void checkIpSecDelete() throws ServiceException {
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_IPSEC_SUCCESS_TESTCASE);
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        execTestCase(deleteRequest, new SuccessChecker());
    }
    @Test
    public void testOsdriverSuccessOperations() throws ServiceException {

        try {
            this.checkVpcCreate();
            this.checkSubnetCreate();
            this.checkIpSecCreate();
            this.checkIpSecDelete();
            this.checkSubnetDelete();
            this.checkVpcDelete();
        } finally {

        }
    }

    private class SuccessChecker implements IChecker {

        @Override
        public boolean check(HttpResponse response) {
            if(response.getStatus() >= 200 && response.getStatus() <= 204) {
                return true;
            }
            return false;
        }
    }
}
