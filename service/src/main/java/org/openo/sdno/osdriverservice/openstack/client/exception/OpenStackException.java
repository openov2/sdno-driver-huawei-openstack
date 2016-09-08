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

package org.openo.sdno.osdriverservice.openstack.client.exception;

/**
 * OpenStack Exception used to capture error code, http status code
 * and error message.
 * <br>
 * <p>
 * </p>
 *
 * @version SDNO 0.5 Jul 31, 2016
 */
public class OpenStackException extends Exception {

    private static final long serialVersionUID = -6909893366730368336L;

    private int httpCode = 500;

    // TODO(mrkanag) Add error code support
    private int errorCode = -1;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param message exception message.
     * @since SDNO 0.5
     */
    public OpenStackException(String message) {
        super(message);
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param cause Cause of exception.
     * @since SDNO 0.5
     */
    public OpenStackException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param httpCode the status code of HTTP.
     * @param message Response message.
     * @since SDNO 0.5
     */
    public OpenStackException(int httpCode, String message) {
        super(message);
        this.setHttpCode(httpCode);
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param httpCode the status code of HTTP.
     * @param errorCode The error code for the operation.
     * @param message The message for the Exception.
     * @since SDNO 0.5
     */
    public OpenStackException(int httpCode, int errorCode, String message) {
        super(message);
        this.setHttpCode(httpCode);
        this.setErrorCode(errorCode);
    }

    /**
     * <br>
     * 
     * @return
     * @since SDNO 0.5
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * <br>
     * 
     * @param errorCode The error code for the operation.
     * @since SDNO 0.5
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * <br>
     * 
     * @return
     * @since SDNO 0.5
     */
    public int getHttpCode() {
        return this.httpCode;
    }

    /**
     * <br>
     * 
     * @param httpCode the status code of HTTP.
     * @since SDNO 0.5
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return this.httpCode + ": " + super.getMessage();
    }

    /**
     * <br>
     * 
     * @return
     * @since SDNO 0.5
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + errorCode;
        result = prime * result + httpCode;
        return result;
    }

    /**
     * <br>
     * 
     * @param obj
     * @return
     * @since SDNO 0.5
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        OpenStackException other = (OpenStackException)obj;
        if(errorCode != other.errorCode) {
            return false;
        }
        if(httpCode != other.httpCode) {
            return false;
        }
        return true;
    }

}
