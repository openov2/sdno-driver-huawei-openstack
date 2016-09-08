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

package org.openo.sdno.osdriverservice.openstack.client.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Model class for project.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-15
 */
@JsonRootName("project")
// @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
// @JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
public class Project {

    private String id;

    /**
     * Project name
     */
    private String name;

    /**
     * Project description
     */
    private String description;

    /**
     * Domain id
     */
    @JsonProperty("domain_id")
    private String domainId;

    /**
     * Enabled
     */
    private static final boolean ENABLED = true;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomainId() {
        return this.domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public boolean isEnabled() {
        return this.ENABLED;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
