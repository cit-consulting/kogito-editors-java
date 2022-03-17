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
import javax.validation.Valid;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.field.selector.SelectorDataProvider;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.type.DecimalBoxFieldType;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.selectors.listBox.type.ListBoxFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "cacheValue")
public class DBRequestTaskExecutionSet implements BPMNPropertySet {

    @Property
    @FormField(
            type = DecimalBoxFieldType.class,
            labelKey = "cacheName.label"
    )
    @Valid
    private Double cacheValue;

    @Property
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = DBRequestType.WEB)},
            afterElement = "cacheValue"
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.DBRequestTypeProvider"
    )
    @Valid
    private DBRequestType dbRequestType;

    private final Script script = new Script(
            new ScriptTypeValue(
                    "java",
                    "com.digitalfinance.riskengine.bpmn.integration.db.DBClientAdapter.call(kcontext);"
            )
    );

    private final IntegrationType integrationType = new IntegrationType(IntegrationType.DATA_BASE_REQUEST);

    public DBRequestTaskExecutionSet() {
        this(0.0, new DBRequestType());
    }

    public DBRequestTaskExecutionSet(
            final @MapsTo("cacheValue") Double cacheValue,
            final @MapsTo("dbRequestType") DBRequestType dbRequestType
    ) {
        this.dbRequestType = dbRequestType;
        this.cacheValue = cacheValue;
    }

    public Double getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(Double cacheValue) {
        this.cacheValue = cacheValue;
    }

    public DBRequestType getDbRequestType() {
        return dbRequestType;
    }

    public void setDbRequestType(DBRequestType dbRequestType) {
        this.dbRequestType = dbRequestType;
    }

    public IntegrationType getIntegrationType() {
        return integrationType;
    }

    public Script getScript() {
        return script;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(
                Objects.hashCode(cacheValue),
                Objects.hashCode(dbRequestType),
                Objects.hashCode(script),
                Objects.hashCode(integrationType)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DBRequestTaskExecutionSet) {
            DBRequestTaskExecutionSet other = (DBRequestTaskExecutionSet) o;
            return Objects.equals(cacheValue, other.cacheValue) &&
                    Objects.equals(dbRequestType, other.dbRequestType) &&
                    Objects.equals(script, other.script) &&
                    Objects.equals(integrationType, other.integrationType);
        }
        return false;
    }
}
