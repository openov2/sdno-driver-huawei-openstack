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

package org.openo.sdno.osdriverservice.util.error;

/**
 * Error message class.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-29
 */
//TODO(mrkanag) incorporate these error codes services
public class ErrorMsg {

    public static final String CREATE_IKEPOLICY_ERROR = "create ikepolicy error";

    public static final String CREATE_IPSECPOLICY_ERROR = "create ipsecpolicy error";

    public static final String CREATE_VPNSERVICE_ERROR = "create vpnservice error";

    public static final String CREATE_IPSECSITECONN_ERROR = "create ipsecsiteconn error";

    public static final String DELETE_IKEPOLICY_ERROR = "delete ikepolicy error";

    public static final String DELETE_IPSECPOLICY_ERROR = "delete ipsecpolicy error";

    public static final String DELETE_VPNSERVICE_ERROR = "delete vpnservice error";

    public static final String DELETE_IPSECSITECONN_ERROR = "delete ipsecsiteconn error";

    public static final String PARSE_TO_JSONOBJ_ERROR = "parse to json object error";

    public static final String GET_IPSEC_MAPPING_ERROR = "get ipsec external id mapping error";

    public static final String CREATE_ROUTER_ERROR = "create router error";

    public static final String CREATE_NETWORK_ERROR = "create network error";

    public static final String CREATE_SUBNET_ERROR = "create subnet error";

    public static final String CREATE_ROUTER_INTERFACE_ERROR = "create router interface error";

    public static final String DELETE_ROUTER_ERROR = "delete router error";

    public static final String DELETE_NETWORK_ERROR = "delete network error";

    public static final String DELETE_SUBNET_ERROR = "delete subnet error";

    public static final String DELETE_ROUTER_INTERFACE_ERROR = "delete router interface error";

    private ErrorMsg() {

    }
}
