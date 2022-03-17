/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition.property.task;

import java.util.Objects;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
//import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldDefinition;
//import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
//import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.I18nMode;
//import org.kie.workbench.common.stunner.bpmn.definition.BPMNProperty;
//import org.kie.workbench.common.stunner.core.definition.annotation.Property;
//import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
//@Property
//@FieldDefinition(i18nMode = I18nMode.OVERRIDE_I18N_KEY)
public class CacheType /*implements BPMNProperty*/ {

    public final static String DEFAULT =  "DEFAULT";
    public final static String NONE = "NONE";
    public final static String CACHE_ONLY = "CACHE_ONLY";
    public final static String FORCE_CACHE = "FORCE_CACHE";

//    @Value
//    @FieldValue
    private String value;

    public CacheType() {
        this(DEFAULT);
    }

    public CacheType(final String value) {
        if (value == null || value.isEmpty()) {
            this.value = DEFAULT;
        } else {
            this.value = value;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(value));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CacheType) {
            CacheType other = (CacheType) o;
            return Objects.equals(value, other.value);
        }
        return false;
    }
}
