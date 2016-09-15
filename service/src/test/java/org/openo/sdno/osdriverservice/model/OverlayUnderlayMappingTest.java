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

package org.openo.sdno.osdriverservice.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;

public class OverlayUnderlayMappingTest {

    @Test
    public void OverlayUnderlaySetGetTest() {
        OverlayUnderlayMapping obj = new OverlayUnderlayMapping();

        obj.setOverlayId("1");
        assertEquals("1", obj.getOverlayId());

        obj.setOverlayType("ovType");
        assertEquals("ovType", obj.getOverlayType());

        obj.setControllerId("10");
        assertEquals("10", obj.getControllerId());

        obj.setUnderlayId("11");
        assertEquals("11", obj.getUnderlayId());

        obj.setUnderlayType("unType");
        assertEquals("unType", obj.getUnderlayType());

        obj.setUnderlayTenantId("1");
        assertEquals("1", obj.getUnderlayTenantId());

        obj.setAction("1");
        assertEquals("1", obj.getAction());
    }

}
