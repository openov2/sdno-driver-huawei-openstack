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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.osdriverservice.util.OSDriverConfig;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Driver Registration.<br>
 *
 * @author
 * @version SDNO 0.5 Sep 20, 2016
 */
public class DriverRegistration implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverRegistration.class);

    private static final OSDriverConfig config = new OSDriverConfig();

    private static final String VERSION = "V100R002";

    private static final String URL = "/openoapi/drivermgr/v1/drivers";

    private static final String SERVICE_VPC_URL = "/openoapi/sbi-vpc/v1";

    private static final String SERVICE_IPSEC_URL = "/openoapi/sbi-ipsec/v1";

    private static final String VPC_TYPE = "DC Gateway";

    private static final String IPSEC_TYPE = "Overlay IpSec";

    private static final String INSTANCE = "sdno-driver-openstack-" + VERSION;

    private static final int DRIVER_REGISTRATION_DELAY = config.getDriverRegistrationReattemptInterval();

    private static final int DRIVER_REGISTRATION_INITIAL_DELAY = config.getDriverRegistrationInitialDelay();

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        try {
            deRegisterDriver(INSTANCE);
        } catch(Exception e) {
            LOGGER.warn("Driver failed to unregister from driver manager", e);
        }
        scheduledExecutorService.shutdown();

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            registerDriver(INSTANCE);
        } catch(Exception e) {
            LOGGER.warn("Driver registration failed with driver manager", e);
        }

    }

    private void registerDriver(String driverName) {
        String driverinfo = getDriverInfo(driverName);

        RestfulParametes restParametes = new RestfulParametes();
        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
            restParametes.setRawData(driverinfo);
            scheduler = scheduledExecutorService.scheduleAtFixedRate(() -> {
                try {
                    RestfulResponse response = RestfulProxy.post(URL, restParametes);
                    if(response.getStatus() == HttpStatus.SC_CREATED) {
                        LOGGER.info(driverName + " successfully registered with driver manager. Now Stop the scheduler.");
                        this.scheduler.cancel(false);
                    } else {
                        LOGGER.warn(
                                driverName + " failed registration with driver manager. Will re-attempt the connection after "
                        +DRIVER_REGISTRATION_DELAY+ "seconds."+response.getStatus());
                    }
                } catch(ServiceException e) {
                    LOGGER.warn(
                            driverName + " registration failed with driver manager, connection will be re-attempted after "
                    +DRIVER_REGISTRATION_DELAY+ " seconds :"+ e.toString());
                }
            }, DRIVER_REGISTRATION_INITIAL_DELAY, DRIVER_REGISTRATION_DELAY, TimeUnit.SECONDS);

        } catch(Exception ex) {
            LOGGER.warn(driverName + " failed registration from driver manager", ex);
        }
    }

    private void deRegisterDriver(String instanceId) {
        String uri = URL + "/" + instanceId;

        RestfulParametes restParametes = new RestfulParametes();

        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            RestfulResponse response = RestfulProxy.delete(uri, restParametes);

            if(response.getStatus() == HttpStatus.SC_CREATED) {
                LOGGER.info(instanceId +" successfully unregistered from driver manager");
            } else {
                LOGGER.warn(instanceId + " failed to unregister from driver manager");
            }

        } catch(Exception e) {
            LOGGER.warn(instanceId + " failed to unregister from driver manager");
        }
    }

    private String getSystemIP() {
        String ipAddress = config.getDriverIp();
        if (ipAddress == null) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                ipAddress = addr.getHostAddress();
            } catch(UnknownHostException ex) {
                LOGGER.info("Unable to get IP address of the driver");
            }
        }

        return ipAddress;
    }

    private String getDriverInfo(String driverName) {
        DriverInfo driverInfo = new DriverInfo();
        List<DriverInfo.Service> serviceList = new ArrayList<DriverInfo.Service>();

        //Creating Vpc service
        DriverInfo.Service vpcService = new DriverInfo.Service();
        vpcService.setServiceUrl(SERVICE_VPC_URL);
        List<DriverInfo.Service.SupportSys> vpcSupportSysList = new ArrayList<>();
        DriverInfo.Service.SupportSys vpcSupportSys = new DriverInfo.Service.SupportSys();
        vpcSupportSys.setType(VPC_TYPE);
        vpcSupportSys.setVersion(VERSION);
        vpcSupportSysList.add(vpcSupportSys);
        vpcService.setSupportSysList(vpcSupportSysList);

        //creating IpSec service
        DriverInfo.Service ipsecService = new DriverInfo.Service();
        ipsecService.setServiceUrl(SERVICE_IPSEC_URL);
        List<DriverInfo.Service.SupportSys> ipsecSupportSysList = new ArrayList<>();
        DriverInfo.Service.SupportSys ipsecSupportSys = new DriverInfo.Service.SupportSys();
        ipsecSupportSys.setType(IPSEC_TYPE);
        ipsecSupportSys.setVersion(VERSION);
        ipsecSupportSysList.add(ipsecSupportSys);
        ipsecService.setSupportSysList(ipsecSupportSysList);

        serviceList.add(vpcService);
        serviceList.add(ipsecService);

        driverInfo.setDriverName(driverName);
        driverInfo.setInstanceID(driverName);
        driverInfo.setIp(getSystemIP());
        driverInfo.setPort(config.getDriverPort());
        driverInfo.setProtocol(config.getDriverProtocol());
        driverInfo.setServices(serviceList);
        return toJson(driverInfo,true,true);

    }


    /**
     * ToJson
     * <br>
     *
     * @param obj
     * @param considerRootName
     * @param indentOutput
     * @return
     * @since  SDNO 0.5
     */
    @SuppressWarnings("deprecation")
    private static String toJson(Object obj, boolean considerRootName, boolean indentOutput) {
        try {
            ObjectMapper ex = new ObjectMapper();
            if(considerRootName) {
                ex.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
            } else {
                ex.disable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
            }

            if(indentOutput) {
                ex.enable(SerializationConfig.Feature.INDENT_OUTPUT);
            } else {
                ex.disable(SerializationConfig.Feature.INDENT_OUTPUT);
            }

            ex.disable(SerializationConfig.Feature.WRITE_NULL_PROPERTIES);
            return ex.writeValueAsString(obj);
        } catch(IOException arg1) {
            String errorMsg = "Parsing to json error";
            LOGGER.error(errorMsg, arg1);
            throw new IllegalArgumentException(errorMsg + ", obj = " + obj, arg1);
        }
    }

}
