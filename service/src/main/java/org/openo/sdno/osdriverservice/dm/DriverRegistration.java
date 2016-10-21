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

package org.openo.sdno.osdriverservice.dm;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The driver registrar listener class.<br>
 *
 * @author
 * @version SDNO 0.5 2016-9-27
 */
public class DriverRegistration implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverRegistration.class);

    private static final String DRIVER_MANAGER_URL = "/openoapi/drivermgr/v1/drivers";

    private static final String DM_REGISTRATION_FILE = "generalconfig/driver.json";

    private static final String DRIVER_INFO_KEY = "driverInfo";

    private static final String INSTANCE_ID_KEY = "instanceID";

    private static final String IP_KEY = "ip";

    private String instanceId = "sdnoopenstackdriver-0-1";

    private static final int DRIVER_REGISTRATION_DELAY = 30;

    private static final int DRIVER_REGISTRATION_INITIAL_DELAY = 5;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        String url = DRIVER_MANAGER_URL + "/" + instanceId;

        LOGGER.info("Start unregister to driver manager, url: " + url);

        RestfulParametes restParametes = new RestfulParametes();

        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
            RestfulResponse response = RestfulProxy.delete(url, restParametes);

            if(HttpCode.isSucess(response.getStatus())) {
                LOGGER.info("Driver successfully un-registration with driver manager");
            } else {
                LOGGER.warn("Driver failed un-registration with driver manager");
            }
        } catch(Exception e) {
            LOGGER.warn("Driver failed unregistered from driver manager", e);
        }

        scheduledExecutorService.shutdown();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        File file = new File(DM_REGISTRATION_FILE);
        if(!file.exists()) {
            LOGGER.info("Stop registering as can't find driver manager registion file");
            return;
        }

        Map<?, ?> dmRegistrationBodyMap = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = Files.readAllBytes(Paths.get(DM_REGISTRATION_FILE));
            dmRegistrationBodyMap = mapper.readValue(bytes, Map.class);
        } catch(IOException e) {
            LOGGER.error("Failed to get driver manager registration body, " + e);
            return;
        }

        @SuppressWarnings("unchecked")
        Map<String, String> driverInfoMap = (Map<String, String>)dmRegistrationBodyMap.get(DRIVER_INFO_KEY);

        instanceId = driverInfoMap.get(INSTANCE_ID_KEY);
        replaceLocalIp(driverInfoMap);

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        restParametes.setRawData(JsonUtil.toJson(dmRegistrationBodyMap));

        LOGGER.info("Registering body: " + JsonUtil.toJson(dmRegistrationBodyMap));

        // If the registration is unsuccessful re-attempt the driver registration.
        // If the registration is successful then finish the task by canceling it.
        scheduler = scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                LOGGER.info("Start registering to driver manager");
                RestfulResponse response = RestfulProxy.post(DRIVER_MANAGER_URL, restParametes);
                if(HttpCode.isSucess(response.getStatus())) {
                    LOGGER.info("Driver successfully registered with driver manager. Now Stop the scheduler.");
                    this.scheduler.cancel(false);
                } else {
                    LOGGER.warn("Driver failed registered with driver manager will reattempt the connection after "
                            + DRIVER_REGISTRATION_DELAY + " seconds.");
                }
            } catch(ServiceException e) {
                LOGGER.warn("Driver registration failed with driver manager, connection will be reattempted after "
                        + DRIVER_REGISTRATION_DELAY + "seconds : " + e.toString());
            }
        }, DRIVER_REGISTRATION_INITIAL_DELAY, DRIVER_REGISTRATION_DELAY, TimeUnit.SECONDS);
    }

    private void replaceLocalIp(Map<String, String> driverInfoMap) {
        if(StringUtils.isNotEmpty(driverInfoMap.get(IP_KEY))) {
            return;
        }

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ipAddress = addr.getHostAddress();
            driverInfoMap.put(IP_KEY, ipAddress);

            LOGGER.info("Local ip: " + ipAddress);
        } catch(UnknownHostException e) {
            LOGGER.error("Unable to get IP address, " + e);
        }
    }
}
