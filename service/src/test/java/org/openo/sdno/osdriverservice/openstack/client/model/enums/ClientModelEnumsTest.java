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

package org.openo.sdno.osdriverservice.openstack.client.model.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Client model enumeration tests.<br>
 *
 * @author
 * @version SDNO 0.5 September 20, 2016
 */
public class ClientModelEnumsTest {

    @Test
    public void DpdActionEnumTest() {
        assertEquals("clear", DpdAction.CLEAR.getName());
        assertEquals("hold", DpdAction.HOLD.getName());
        assertEquals("restart", DpdAction.RESTART.getName());
        assertEquals("disabled", DpdAction.DISABLED.getName());
        assertEquals("restart-by-peer", DpdAction.RESTART_BY_PEER.getName());
    }

    @Test
    public void IpSecVpnStatusEnumTest() {
        assertEquals("ACTIVE", IpSecVpnStatus.ACTIVE.getName());
        assertEquals("DOWN", IpSecVpnStatus.DOWN.getName());
        assertEquals("PENDING_CREATE", IpSecVpnStatus.PENDING_CREATE.getName());
        assertEquals("ERROR", IpSecVpnStatus.ERROR.getName());
    }

    @Test
    public void RouteModeEnumTest() {
        assertEquals("static", RouteMode.STATIC.getName());
        assertEquals("dynamic", RouteMode.DYNAMIC.getName());
    }
}
