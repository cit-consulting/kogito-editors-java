/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.client.marshall.converters.fromstunner.properties;

import java.util.Set;

import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.ScoringTask;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.customproperties.CustomElement;

public class ScoringTaskPropertyWriter extends ActivityPropertyWriter {

    private final ScoringTask scoringTask;

    public ScoringTaskPropertyWriter(ScoringTask scoringTask, VariableScope variableScope, Set<DataObject> dataObjects) {
        super(scoringTask, variableScope, dataObjects);
        this.scoringTask = scoringTask;
    }

    public void setScript(String script) {
        CustomElement.script.of(scoringTask).set(script);

//        if (script != null && !script.isEmpty()) {
//            scoringTask.setScript(asCData(script));
//        }
    }
}
