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
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "scoringIdentity")
public class ScoringTaskExecutionSet implements BPMNPropertySet {

    private final Script script = new Script(
            new ScriptTypeValue(
                    "java",
                    "com.digitalfinance.riskengine.bpmn.scoring.ScoringAdapter.call(kcontext);"
            )
    );

    @Property
    @FormField(
            labelKey = "org.kie.workbench.common.stunner.bpmn.definition.property.task.ScoringTaskExecutionSet.scoringIdentity.label",
            afterElement = "general"
    )
    @Valid
    private String scoringIdentity;

    private final IntegrationType integrationType = new IntegrationType(IntegrationType.SCORING);

    public ScoringTaskExecutionSet() {
        this("");
    }

    public ScoringTaskExecutionSet(
            final @MapsTo("scoringIdentity") String scoringIdentity
    ) {
        this.scoringIdentity = scoringIdentity;
    }

    public void setScoringIdentity(String scoringIdentity) {
        this.scoringIdentity = scoringIdentity;
    }

    public String getScoringIdentity() {
        return scoringIdentity;
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
                Objects.hashCode(script),
                Objects.hashCode(integrationType),
                Objects.hashCode(scoringIdentity)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScoringTaskExecutionSet) {
            ScoringTaskExecutionSet other = (ScoringTaskExecutionSet) o;
            return Objects.equals(script, other.script) &&
                    Objects.equals(integrationType, other.integrationType) &&
                    Objects.equals(scoringIdentity, other.scoringIdentity);
        }
        return false;
    }
}
