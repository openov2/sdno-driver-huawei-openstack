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

package org.openo.sdno.osdriverservice.rest.endtoend;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.osdriverservice.dm.DriverRegistration;
import org.openo.sdno.osdriverservice.openstack.mock.GenericMockServer;

/**
 * Driver Registration tests.<br>
 *
 * @author
 * @version SDNO 0.5 Sep 20, 2016
 */
public class DriverRegistrationTest {

    private static GenericMockServer mockServer = new GenericMockServer();
    private static String[] mockJsonsDriverManager ={
                    "src/integration-test/resources/dm_mock_jsons/DMregistration.json",
                    "src/integration-test/resources/dm_mock_jsons/DMUnregistration-vpc.json",
                    "src/integration-test/resources/dm_mock_jsons/DMUnregistration-ipsec.json"
            };

    public static void main(String[] args){
        mockServer.addMockJsons(mockJsonsDriverManager);

        try{
            mockServer.start();
            DriverRegistrationTest test = new DriverRegistrationTest();
            test.verifyRegistration();
            test.verifyDeRegistration();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        mockServer.stop();
    }

    private void verifyRegistration() throws ServiceException{
        DriverRegistration listener = new DriverRegistration();
        listener.contextInitialized(null);
    }

    private void verifyDeRegistration() throws ServiceException{
        DriverRegistration listener = new DriverRegistration();
        listener.contextDestroyed(null);;
    }

}
