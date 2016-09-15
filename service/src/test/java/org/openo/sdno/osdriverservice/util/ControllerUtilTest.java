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

package org.openo.sdno.osdriverservice.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.CommParamDao;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.model.AuthInfo;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;

import mockit.Mock;
import mockit.MockUp;

public class ControllerUtilTest {

    @Test
    public void testCreateOpenStackClientException() throws ServiceException {
        new MockUp<ControllerDao>() {

            @Mock
            public ControllerMO getController(String uuid) {
                ControllerMO mo = new ControllerMO();
                mo.setHostName("abc.xyz");
                mo.setDescription("test123");

                return mo;
            }
        };
        new MockUp<CommParamDao>() {

            @Mock
            public List<CommParamMO> getCommParam(String uuid) {
                List<CommParamMO> list = new ArrayList<CommParamMO>();
                CommParamMO mo = new CommParamMO();
                mo.setHostName("test");
                AuthInfo info = new AuthInfo();
                info.setPassword("pwd");
                info.setPort("123");
                info.setUserName("uname");
                mo.setAuthInfo(info);
                list.add(mo);
                return list;
            }
        };
        ControllerUtil.createOpenStackClient("test123");

    }
}
