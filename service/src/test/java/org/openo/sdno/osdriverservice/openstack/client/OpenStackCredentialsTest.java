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

package org.openo.sdno.osdriverservice.openstack.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OpenStackCredentialsTest {

    @Test
    public void testIsValid() {
        OpenStackCredentials credentials = new OpenStackCredentials();
        boolean isValid = credentials.isValid();
        assertFalse(isValid);
    }

    @Test
    public void testIsValidAllData() {
        OpenStackCredentials credentials = new OpenStackCredentials("1.1.1.1", "8080", "username", "password");
        boolean isValid = credentials.isValid();
        assertTrue(isValid);
    }

    @Test
    public void testIsValidNullPort() {
        OpenStackCredentials credentials = new OpenStackCredentials("1.1.1.1", null, "username", "password");
        boolean isValid = credentials.isValid();
        assertFalse(isValid);
    }

    @Test
    public void testIsValidNullUserName() {
        OpenStackCredentials credentials = new OpenStackCredentials("1.1.1.1", "8080", null, "password");
        boolean isValid = credentials.isValid();
        assertFalse(isValid);
    }

    @Test
    public void testIsValidNullUserPassword() {
        OpenStackCredentials credentials = new OpenStackCredentials("1.1.1.1", "8080", "username", null);
        boolean isValid = credentials.isValid();
        assertFalse(isValid);
    }

    @Test
    public void testIsValidValidDomain() {
        OpenStackCredentials credentials =
                new OpenStackCredentials("1.1.1.1", "8080", "username", "pass").setDomain(null);
        boolean isValid = credentials.isValid();
        assertFalse(isValid);
    }

}
