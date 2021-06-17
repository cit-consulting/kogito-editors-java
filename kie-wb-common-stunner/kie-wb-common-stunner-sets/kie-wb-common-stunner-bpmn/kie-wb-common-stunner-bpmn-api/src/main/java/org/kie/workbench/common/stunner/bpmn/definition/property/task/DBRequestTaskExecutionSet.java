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
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.selectors.listBox.type.ListBoxFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "dbRequestType")
public class DBRequestTaskExecutionSet implements BPMNPropertySet {

    @Property
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = DBRequestType.WEB)}
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.DBRequestTypeProvider"
    )
    @Valid
    private DBRequestType dbRequestType;

    @Property
    @FormField(
            afterElement = "dbRequestType",
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = CacheType.DEFAULT)} //"DEFAULT")}
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider"
    )
    @Valid
    private CacheType cacheType;

    private final Script script = new Script(
            new ScriptTypeValue(
                    "java",
                    "com.digitalfinance.riskengine.bpmn.integration.db.DBClientAdapter.call(kcontext);"
            )
    );

    private final IntegrationType integrationType = new IntegrationType(IntegrationType.DATA_BASE_REQUEST);

    public DBRequestTaskExecutionSet() {
        this(new CacheType(), new DBRequestType());
    }

    public DBRequestTaskExecutionSet(
            final @MapsTo("cacheType") CacheType cacheType,
            final @MapsTo("dbRequestType") DBRequestType dbRequestType
    ) {
        this.cacheType = cacheType;
        this.dbRequestType = dbRequestType;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(final CacheType cacheType) {
        this.cacheType = cacheType;
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
                Objects.hashCode(cacheType),
                Objects.hashCode(dbRequestType),
                Objects.hashCode(script),
                Objects.hashCode(integrationType)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DBRequestTaskExecutionSet) {
            DBRequestTaskExecutionSet other = (DBRequestTaskExecutionSet) o;
            return Objects.equals(cacheType, other.cacheType) &&
                    Objects.equals(dbRequestType, other.dbRequestType) &&
                    Objects.equals(script, other.script) &&
                    Objects.equals(integrationType, other.integrationType);
        }
        return false;
    }
}
