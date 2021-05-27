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
@FormDefinition(startElement = "integrationType")
public class ScriptTaskExecutionSet implements BPMNPropertySet {

    @Property
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = "PYTHON")}
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.IntegrationProvider"
    )
    @Valid
    private IntegrationType integrationType;

    @Property
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = "DEFAULT")},
            afterElement = "integrationType"
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider"
    )
    @Valid
    private CashType cashType;

    @Property
    @FormField(settings = {@FieldParam(name = "mode", value = "ACTION_SCRIPT")}, afterElement = "cashType")
    @Valid
    private Script script;

    public ScriptTaskExecutionSet() {
        this(new Script(new ScriptTypeValue("java", "")), new CashType(), new IntegrationType());
    }

    public ScriptTaskExecutionSet(
            final @MapsTo("script") Script script,
            final @MapsTo("cashType") CashType cashType,
            final @MapsTo("cashType") IntegrationType integrationType
            ) {
        this.integrationType = integrationType;
        this.script = script;
        this.cashType = cashType;
    }

    public IntegrationType getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(IntegrationType integrationType) {
        this.integrationType = integrationType;

        switch (integrationType.getValue()) {
            case (IntegrationType.PYTHON):
                setScript(new Script(new ScriptTypeValue("java", "foo()")));
                break;
            case (IntegrationType.DATA_BASE):
                setScript(new Script(new ScriptTypeValue("java", "bar()")));
                break;
            case (IntegrationType.AMAZON):
                setScript(new Script(new ScriptTypeValue("java", "nan()")));
                break;
        }

    }

    public CashType getCashType() {
        return cashType;
    }

    public void setCashType(CashType cashType) {
        this.cashType = cashType;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(final Script script) {
        this.script = script;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(
                Objects.hashCode(script),
                Objects.hashCode(cashType),
                Objects.hashCode(integrationType)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScriptTaskExecutionSet) {
            ScriptTaskExecutionSet other = (ScriptTaskExecutionSet) o;
            return Objects.equals(script, other.script) &&
                    Objects.equals(cashType, other.cashType) &&
                    Objects.equals(integrationType, other.integrationType);
        }
        return false;
    }
}
