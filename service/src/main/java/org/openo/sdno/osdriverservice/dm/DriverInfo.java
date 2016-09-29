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

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Driver registration information class
 *
 * @author
 * @version SDNO 0.5 2016-9-21
 */
@JsonRootName("driverInfo")
public class DriverInfo {

    private String driverName;

    private String instanceID;

    private String ip;

    private String port;

    private String protocol;

    @JsonProperty("services")
    private List<DriverInfo.Service> services;

    public String getDriverName() {
        return driverName;
    }

    public String getInstanceID() {
        return instanceID;
    }


    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<DriverInfo.Service> getServices() {
        return services;
    }

    public void setServices(List<DriverInfo.Service> services) {
        this.services = services;
    }

    @JsonRootName("service")
    public static class Service {

        @JsonProperty("service_url")
        private String serviceUrl;

        @JsonProperty("support_sys")
        private List<DriverInfo.Service.SupportSys> supportSysList;

        public String getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public List<DriverInfo.Service.SupportSys> getSupportSysList() {
            return supportSysList;
        }

        public void setSupportSysList(List<DriverInfo.Service.SupportSys> supportSysList) {
            this.supportSysList = supportSysList;
        }

        public static class SupportSys {

            private String type = null;

            private String version = null;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }
        }
    }

}
