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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpInput;
import org.openo.sdno.osdriverservice.openstack.client.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 August 5, 2016
 */
public class HttpGateKeeper {

    private static Map<HttpInput, HttpResult> records = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpGateKeeper.class);

    private static String BASE_LOC = ".";

    private static boolean enable = false;

    /**
     * Constructor<br>
     * <p>
     * </p>
     *
     * @since SDNO 0.5
     */
    private HttpGateKeeper() {

    }

    /**
     * <br>
     *
     * @since SDNO 0.5
     */
    public static void clean() {
        records = new HashMap<>();
    }

    /**
     * <br>
     *
     * @param request
     * @param response
     * @since SDNO 0.5
     */
    public static void add(HttpInput request, HttpResult response) {
        if(enable) {
            records.put(request, response);
        }
    }

    /**
     * <br>
     *
     * @param baseDir
     * @since SDNO 0.5
     */
    public static void setTargetDir(String baseDir) {
        BASE_LOC = baseDir;
    }

    static class JsonMock implements Serializable {

        private static final long serialVersionUID = 1L;

        private Request request = new Request();

        private Response response = new Response();

        static class Request {

            private String uri;

            private String method;

            private Map<String, String> headers = new HashMap<>();

            private String json;
        }

        static class Response {

            private String status;

            private Map<String, String> headers = new HashMap<>();

            private String json;
        }
    }

    private static void writeFile(String fileName, String jsonString) {
        try {
            File file = new File(fileName);
            if(!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jsonString);
            bw.close();
        } catch(IOException e) {
            LOGGER.error("IO Exception in the write File", e);
        }
    }

    /**
     * <br>
     *
     * @since SDNO 0.5
     */
    public static void cleanupMockFiles() {
        File[] listFiles = new File(BASE_LOC).listFiles();
        if(listFiles != null) {
            for(File f : listFiles) {
                f.delete();
            }
        }

    }

    /**
     * <br>
     *
     * @param replaceMap
     * @since SDNO 0.5
     */
    public static void generateMockFiles(Map<String, String> replaceMap) {
        for(Entry<HttpInput, HttpResult> e : records.entrySet()) {
            JsonMock mock = new JsonMock();
            mock.request.uri = e.getKey().getUri();
            mock.request.method = e.getKey().getMethod();
            mock.request.json = "@@@" + StringEscapeUtils.unescapeJava(e.getKey().getBody()) + "@@@";
            mock.request.headers = e.getKey().getReqHeaders();
            mock.response.status = "" + e.getValue().getStatus();
            mock.response.json = "@@@" + StringEscapeUtils.unescapeJava(e.getValue().getBody()) + "@@@";
            mock.response.headers = e.getValue().getRespHeaders();

            String port = "";
            try {
                URL uri = new URL(mock.request.uri);
                port = "" + uri.getPort();
            } catch(MalformedURLException e1) {
                LOGGER.error("MalformedURLException  in the generateMockFiles", e1);
            }

            File dir = new File(BASE_LOC + "\\" + port);
            if(!dir.exists()) {
                dir.mkdir();
            }

            String jsonString = JsonUtil.toJson(mock, false, true);
            String fileName = BASE_LOC + "\\" + port + "\\" + UUID.randomUUID().toString() + ".json";
            jsonString = StringEscapeUtils.unescapeJava(jsonString);

            for(Entry<String, String> map : replaceMap.entrySet()) {
                jsonString = jsonString.replaceAll(map.getKey(), map.getValue());
            }

            writeFile(fileName, jsonString.replaceAll("@@@\"", "").replaceAll("\"@@@", ""));
        }
    }

    /**
     * @return Returns the enable.
     */
    public static boolean isEnable() {
        return enable;
    }

    /**
     * @param enable The enable to set.
     */
    public static void setEnable(boolean enable) {
        HttpGateKeeper.enable = enable;
    }

    /**
     * <br>
     *
     * @param args
     * @since SDNO 0.5
     */
    public static void main(String[] args) {
        // main function for the entry point..
        // is kept empty on purpose...
    }

}
