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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenStack Network Configuration Operation Class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
public class OSNetworkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSDriverConfig.class);

    private static final String OS_NETWORK_CONFIG_FILE = "etc/OsNetworkConfig.properties";

    private Properties properties = new Properties();

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public OSNetworkConfig() {
        loadProperties();
    }

    /**
     * Get network type.<br>
     * 
     * @return network type
     * @since SDNO 0.5
     */
    public String getNetworkType() {
        return properties.getProperty("networktype");
    }

    /**
     * Get physical network name.<br>
     * 
     * @return physical network name
     * @since SDNO 0.5
     */
    public String getPhysicalNetwork() {
        return properties.getProperty("physicalnetwork");
    }

    /**
     * Get segment id.<br>
     * 
     * @return segment id
     * @since SDNO 0.5
     */
    public String getSegmentId() {
        return properties.getProperty("segmentid");
    }

    private void loadProperties() {
        try {
            FileInputStream fin = new FileInputStream(OS_NETWORK_CONFIG_FILE);
            properties.load(fin);
        } catch(IOException e) {
            LOGGER.error("Load Os Network Property File failed!", e);
        }
    }
}
