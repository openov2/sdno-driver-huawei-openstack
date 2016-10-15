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

package org.openo.sdno.osdriverservice.sbi.model;

import java.util.HashMap;
import java.util.Map;
/**
 * BaseUnderlays class
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
public class BaseUnderlays {

    private Map<String, String> allAction = new HashMap<>();
    private Map<String, String> allRsc = new HashMap<>();

    protected void put(String key, String value, String action) {
        this.allAction.put(value, action);
        this.allRsc.put(value, key);
    }

    public Map<String, String> getResourceIds() {
        return this.allRsc;
    }

    public Map<String, String> getResourceActions() {
        return this.allAction;
    }
}
