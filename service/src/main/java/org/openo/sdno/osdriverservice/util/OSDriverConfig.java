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
 * OsDriver Configuration Operation Class.<br/>
 *
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public class OSDriverConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSDriverConfig.class);

    private static final String OS_DRIVER_CONFIG = "src/main/resources/osdriver.properties";

    private Properties properties = new Properties();

    public OSDriverConfig() {
        loadProperties();
    }

    /**
     * Get OSDriver Password.<br/>
     *
     * @return OSDriver Password
     * @since SDNO 0.5
     */
    public String getOSPassword() {
        return properties.getProperty("os_password");
    }

    /**
     * Get OSDriver UserName.<br/>
     *
     * @return OS UserName
     * @since SDNO 0.5
     */
    public String getOSUserName() {
        return properties.getProperty("os_username");
    }

    /**
     * Get OSDriver Port.<br/>
     *
     * @return OSDriver Port
     * @since SDNO 0.5
     */
    public String getOSPort() {
        return properties.getProperty("os_port");
    }

    /**
     * Get OSDriver IpAddress.<br/>
     *
     * @return OSDriver IpAddress
     * @since SDNO 0.5
     */
    public String getOSIpAddress() {
        return properties.getProperty("os_ip");
    }

    /**
     * Get OSDriver Domain name.<br/>
     *
     * @return OSDriver Domain name
     * @since SDNO 0.5
     */
    public String getOSDomainName() {
        return properties.getProperty("os_domainname");
    }

    /**
     * Get OSDriver Region name.<br/>
     *
     * @return OSDriver Region name
     * @since SDNO 0.5
     */
    public String getOSRegionName() {
        return properties.getProperty("os_regionname");
    }

    private void loadProperties() {
        try {
            FileInputStream fin = new FileInputStream(OS_DRIVER_CONFIG);
            properties.load(fin);
        } catch(IOException e) {
            LOGGER.error("Load Property File failed!", e);
        }
    }
}
