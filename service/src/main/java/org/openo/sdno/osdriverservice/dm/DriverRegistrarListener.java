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

import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.osdriverservice.util.OSDriverConfig;
import java.net.UnknownHostException;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Driver Registration.<br>
 *
 * @author
 * @version SDNO 0.5 Sep 20, 2016
 */
public class DriverRegistrarListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverRegistrarListener.class);

    private static final String VERSION = "V100R002";

    private static final String URL = "/openoapi/extsys/v1/vims";

    private static final String SERVICE_VPC_URL = "/openoapi/sbi-vpc/v1";

    private static final String SERVICE_IPSEC_URL = "/openoapi/sbi-ipsec/v1";

    private static final String TYPE = "Agile Controller-WAN";

    private OSDriverConfig config = new OSDriverConfig();

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        try {
            String vpcDriverName = "sdno-driver-vpc-" + VERSION;
            String ipsecDriverName = "sdno-driver-ipsec-" + VERSION;
            deRegisterDriver(vpcDriverName);
            deRegisterDriver(ipsecDriverName);

        } catch(Exception e) {
            LOGGER.warn("Driver failed unregistered from driver manager", e);
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String vpcDriverName = "sdno-driver-vpc-" + VERSION;
            String ipsecDriverName = "sdno-driver-ipsec-" + VERSION;
            registerDriver(vpcDriverName, SERVICE_VPC_URL);
            registerDriver(ipsecDriverName, SERVICE_IPSEC_URL);
        } catch(Exception e) {
            LOGGER.warn("Driver failed registered with driver manager", e);
        }

    }

    private void registerDriver(String driverName, String serviceUrl) {
        String driverinfo = getDriverInfo(driverName, serviceUrl);

        RestfulParametes restParametes = new RestfulParametes();
        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
            restParametes.setRawData(JsonUtil.toJson(driverinfo));
            RestfulResponse response = RestfulProxy.post(URL, restParametes);
            if(response.getStatus() == 200) {
                LOGGER.info(driverName + " successfully registered with driver manager");
            } else {
                LOGGER.warn(driverName + "failed registered with driver manager");
            }

        } catch(Exception ex) {
            LOGGER.warn(driverName + "Driver failed unregistered from driver manager", ex);
        }
    }

    private void deRegisterDriver(String instanceId) {
        String uri = URL + "/" + instanceId;

        RestfulParametes restParametes = new RestfulParametes();

        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            RestfulResponse response = RestfulProxy.delete(uri, restParametes);

            if(response.getStatus() == 200) {
                LOGGER.info(instanceId +" successfully unregistered from driver manager");
            } else {
                LOGGER.warn(instanceId + " failed unregistered from driver manager");
            }

        } catch(Exception e) {
            LOGGER.warn(instanceId + " failed unregistered from driver manager");
        }
    }

    private String getSystemIP() {
        String ipAddress = "127.0.0.1";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ipAddress = addr.getHostAddress();
        } catch(UnknownHostException ex) {
            LOGGER.info("Unable to get IP address of the driver");
        }
        return ipAddress;
    }

    private String getDriverInfo(String driverName, String serviceUrl) {
        DriverInfo driverInfo = new DriverInfo();
        List<DriverInfo.Service> serviceList = new ArrayList<DriverInfo.Service>();

        DriverInfo.Service service = new DriverInfo.Service();
        service.setServiceUrl(serviceUrl);
        List<DriverInfo.Service.SupportSys> supportSysList = new ArrayList<>();
        DriverInfo.Service.SupportSys supportSys = new DriverInfo.Service.SupportSys();
        supportSys.setType(TYPE);
        supportSys.setVersion(VERSION);
        supportSysList.add(supportSys);
        service.setSupportSysList(supportSysList);

        serviceList.add(service);
        driverInfo.setDriverName(driverName);
        driverInfo.setIp(getSystemIP());
        driverInfo.setPort(config.getDriverPort());
        driverInfo.setProtocol(config.getDriverProtocol());
        driverInfo.setServices(serviceList);
        return JsonUtil.toJson(driverInfo);
    }

}
