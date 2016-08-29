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

package org.openo.sdno.osdriverservice.util;

import org.openo.sdno.osdriverservice.model.OsSubnet;
import org.openo.sdno.osdriverservice.model.OsVpc;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Vpc;

/**
 * Model converting class, converting SDNO model to adapter model.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public class MigrateModelUtil {

    private MigrateModelUtil() {

    }

    /**
     * convert
     * <br/>
     *
     * @param vpc
     * @return
     * @since SDNO 0.5
     */
    public static OsVpc convert(Vpc vpc) {

        OsVpc osVpc = new OsVpc();
        osVpc.setProjectName(vpc.getProjectName());
        osVpc.setDomainName(vpc.getDomainName());
        osVpc.setOverlayId(vpc.getUuid());
        return osVpc;
    }

    /**
     * convert
     * <br/>
     *
     * @param subnet
     * @return
     * @since SDNO 0.5
     */
    public static OsSubnet convert(Subnet subnet) {
        OsSubnet osSubnet = new OsSubnet();
        osSubnet.setCidr(subnet.getCidr());
        osSubnet.setOverlayId(subnet.getUuid());
        return osSubnet;
    }

}
