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
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.type.TextBoxFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "cacheValue")
public class TrustingSocialTaskExecutionSet implements BPMNPropertySet {

    @Property
    @FormField(
            type = DecimalBoxFieldType.class,
            labelKey = "cacheName.label"
    )
    @Valid
    private Double cacheValue;

    @Property
    @FormField(
            type = TextBoxFieldType.class,
            labelKey = "resultS3Key.label",
            afterElement = "cacheValue"
    )
    @Valid
    private String resultS3Key;

    @Valid
    @Property
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = IntegrationModeTrustSocial.V1)},
            afterElement = "resultS3Key"
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.IntegrationModeTrustSocialProvider"
    )
    private IntegrationModeTrustSocial integrationMode;

    private final IntegrationType integrationType = new IntegrationType(IntegrationType.TRUSTING_SOCIAL);

    private final Script script = new Script(
            new ScriptTypeValue(
                    "java",
                    "com.digitalfinance.riskengine.bpmn.integration.trustingsocial.TrustingSocial.process(kcontext);"
            )
    );

    public TrustingSocialTaskExecutionSet() {
        this(0.0, new IntegrationModeTrustSocial(), "");
    }

    public TrustingSocialTaskExecutionSet(
            final @MapsTo("cacheValue") Double cacheValue,
            final @MapsTo("integrationMode") IntegrationModeTrustSocial integrationMode,
            final @MapsTo("resultS3Key") String resultS3Key
    ) {
        this.cacheValue = cacheValue;
        this.integrationMode = integrationMode;
        this.resultS3Key = resultS3Key;
    }

    public Double getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(Double cacheValue) {
        this.cacheValue = cacheValue;
    }

    public String getResultS3Key() {
        return resultS3Key;
    }

    public void setResultS3Key(String resultS3Key) {
        this.resultS3Key = resultS3Key;
    }

    public IntegrationModeTrustSocial getIntegrationMode() {
        return integrationMode;
    }

    public void setIntegrationMode(IntegrationModeTrustSocial integrationMode) {
        this.integrationMode = integrationMode;
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
                Objects.hashCode(integrationMode),
                Objects.hashCode(script),
                Objects.hashCode(integrationType)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TrustingSocialTaskExecutionSet) {
            TrustingSocialTaskExecutionSet other = (TrustingSocialTaskExecutionSet) o;
            return Objects.equals(cacheValue, other.cacheValue) &&
                    Objects.equals(integrationMode, other.integrationMode) &&
                    Objects.equals(script, other.script) &&
                    Objects.equals(integrationType, other.integrationType);
        }
        return false;
    }
}
