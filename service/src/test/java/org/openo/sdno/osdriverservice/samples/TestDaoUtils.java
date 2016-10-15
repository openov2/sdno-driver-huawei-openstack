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

package org.openo.sdno.osdriverservice.samples;

import java.io.IOException;

import org.openo.sdno.osdriverservice.dao.model.OverlayUnderlayMapping;
import org.openo.sdno.osdriverservice.openstack.utils.JsonUtil;

/**
 * DAO Utility tests.<br>
 *
 * @author
 * @version SDNO 0.5 September 20, 2016
 */
public class TestDaoUtils {

    public void testDao() throws IOException {
        String json = Utils.getSampleJson(TestDaoUtils.class, "sample.json");
        JsonUtil.fromJson(json, OverlayUnderlayMapping.class);

        // DaoUtils.insert(mapping);

    }

    public static void main(String args[]) {
        TestDaoUtils utils = new TestDaoUtils();
        try {
            utils.testDao();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
