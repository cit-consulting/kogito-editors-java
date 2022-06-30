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
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.type.DecimalBoxFieldType;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.type.TextBoxFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "cacheValue")
public class FinScoreTaskExecutionSet implements BPMNPropertySet {

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
            labelKey = "resultS3Key.label"
    )
    @Valid
    private String resultS3Key;

    @Property
    @FormField(
            labelKey = "integrationMode.label",
            afterElement = "cacheType"
    )
    @Valid
    private String integrationMode;


    @Property
    @FormField(
            afterElement = "integrationMode"
    )
    @Valid
    private IsAsync isAsync;

    private final IntegrationType integrationType = new IntegrationType(IntegrationType.FIN_SCORE);

    private final Script script = new Script(
            new ScriptTypeValue(
                    "java",
                    "com.digitalfinance.riskengine.bpmn.integration.CommonIntegration.process(kcontext, \"finscore-integration\");"
            )
    );

    public FinScoreTaskExecutionSet() {
        this(0.0, "", "default", new IsAsync());
    }

    public FinScoreTaskExecutionSet(
            final @MapsTo("cacheValue") Double cacheValue,
            final @MapsTo("integrationMode") String integrationMode,
            final @MapsTo("resultS3Key") String resultS3Key,
            final @MapsTo("isAsync") IsAsync isAsync
    ) {
        this.cacheValue = cacheValue;
        this.integrationMode = integrationMode;
        this.resultS3Key = resultS3Key;
        this.isAsync = isAsync;
    }

    public String getIntegrationMode() {
        return integrationMode;
    }

    public void setIntegrationMode(String integrationMode) {
        this.integrationMode = integrationMode;
    }

    public IntegrationType getIntegrationType() {
        return integrationType;
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

    public Script getScript() {
        return script;
    }

    public void setIsAsync(IsAsync isAsync) {
        this.isAsync = isAsync;
    }

    public IsAsync getIsAsync() {
        return isAsync;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(
                Objects.hashCode(cacheValue),
                Objects.hashCode(script),
                Objects.hashCode(integrationType),
                Objects.hashCode(integrationMode),
                Objects.hashCode(resultS3Key),
                Objects.hashCode(isAsync)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FinScoreTaskExecutionSet) {
            FinScoreTaskExecutionSet other = (FinScoreTaskExecutionSet) o;
            return Objects.equals(cacheValue, other.cacheValue) &&
                    Objects.equals(script, other.script) &&
                    Objects.equals(integrationType, other.integrationType) &&
                    Objects.equals(integrationMode, other.integrationMode) &&
                    Objects.equals(isAsync, other.isAsync) &&
                    Objects.equals(resultS3Key, other.resultS3Key);
        }
        return false;
    }
}
