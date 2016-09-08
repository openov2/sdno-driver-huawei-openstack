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

package org.openo.sdno.osdriverservice.openstack.utils;

import java.io.IOException;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JsonUtil class
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version     SDNO 0.5  Aug 8, 2016
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    private JsonUtil() {
        // Default constructor made private
    }

    /**
     * <br>
     * 
     * @param obj Object to be JSON stringified.
     * @return
     * @since SDNO 0.5
     */
    public static String toJson(Object obj) {
        return toJson(obj, true);
    }

    /**
     * <br>
     * 
     * @param obj Object to be JSON stringified.
     * @param considerRootName true if the rot value is to be considered.
     * @return
     * @since SDNO 0.5
     */
    public static String toJson(Object obj, boolean considerRootName) {
        return toJson(obj, considerRootName, false);
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
    public static String toJson(Object obj, boolean considerRootName, boolean indentOutput) {
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
            LOGGER.error("Parser to json error.", arg1);
            throw new IllegalArgumentException("Parser obj to json error, obj = " + obj, arg1);
        }
    }

    /**
     * FromJson
     * <br>
     *
     * @param jsonStr
     * @param objClass
     * @return
     * @since  SDNO 0.5
     */
    public static <T> T fromJson(String jsonStr, Class<T> objClass) {
        return fromJson(jsonStr, objClass, false);
    }

    /**
     * FromJson
     * <br>
     *
     * @param jsonStr
     * @param objClass
     * @param considerRootName
     * @return
     * @since  SDNO 0.5
     */
    public static <T> T fromJson(String jsonStr, Class<T> objClass, boolean considerRootName) {
        try {
            // TODO(mrkanag) ObjectMapper is taking more time during runtime, fix it !!
            ObjectMapper ex = new ObjectMapper();
            ex.disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            if(considerRootName) {
                ex.enable(Feature.UNWRAP_ROOT_VALUE);
            } else {
                ex.disable(Feature.UNWRAP_ROOT_VALUE);
            }
            return ex.readValue(jsonStr, objClass);
        } catch(IOException arg2) {
            throw new IllegalArgumentException(
                    "Parser json to object error, json = " + jsonStr + ", expect class = " + objClass, arg2);
        }
    }
}
