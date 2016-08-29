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
 * Model class for dead peer detection.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class Dpd {

    /**
     * DPD actions controls the use of Dead Peer Detection Protocol. ( clear, hold, restart,
     * disabled, restart-by-peer). Default value "hold".
     */
    private String action;

    /**
     * Seconds for DPD delay(>0).
     */
    private int interval;

    /**
     * Seconds for DPD timeout(timeout>0 & timeout>dpd_interval).
     */
    private int timeout;

    /**
     * Constructor.<br/>
     * 
     * @since  SDNO 0.5
     */
    public Dpd(){
        // Default constructor for formatting from json.
    }
    
    /**
     * Constructor<br/>
     *
     * @since SDNO 0.5
     * @param action DPD action
     * @param interval Seconds for DPD delay
     * @param timeout DPD timeout
     */
    public Dpd(String action, int interval, int timeout) {
        this.action = action;
        this.interval = interval;
        this.timeout = timeout;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
