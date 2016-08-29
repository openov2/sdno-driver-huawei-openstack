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
 * Model class for IpSec in adapter layer.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class NetIpSecModel {

    private NetIkePolicy netIkePolicy;

    private NetIpSecPolicy netIpSecPolicy;

    private NetVpnService netVpnService;

    private NetIpSecSiteConnection netIpSecSiteConnection;

    public NetIkePolicy getNetIkePolicy() {
        return netIkePolicy;
    }

    public void setNetIkePolicy(NetIkePolicy netIkePolicy) {
        this.netIkePolicy = netIkePolicy;
    }

    public NetIpSecPolicy getNetIpSecPolicy() {
        return netIpSecPolicy;
    }

    public void setNetIpSecPolicy(NetIpSecPolicy netIpSecPolicy) {
        this.netIpSecPolicy = netIpSecPolicy;
    }

    public NetVpnService getNetVpnService() {
        return netVpnService;
    }

    public void setNetVpnService(NetVpnService netVpnService) {
        this.netVpnService = netVpnService;
    }

    public NetIpSecSiteConnection getNetIpSecSiteConnection() {
        return netIpSecSiteConnection;
    }

    public void setNetIpSecSiteConnection(NetIpSecSiteConnection netIpSecSiteConnection) {
        this.netIpSecSiteConnection = netIpSecSiteConnection;
    }
}
