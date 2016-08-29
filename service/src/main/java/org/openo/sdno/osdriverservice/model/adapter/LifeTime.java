/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.osdriverservice.model.adapter;

/**
 * Lifetime model class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class LifeTime {

    /**
     * Lifetime of the SA unit in 'seconds'.
     */
    private String units;

    /**
     * Lifetime value in seconds(value>=60).
     */
    private int value;
    
    /**
     * Constructor.<br/>
     * 
     * @since  SDNO 0.5
     */
    public LifeTime(){
        // Default constructor for formatting from json.
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param units Lifetime of the SA unit in 'seconds'
     * @param value Lifetime value in seconds
     */
    public LifeTime(String units, int value) {
        this.units = units;
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
