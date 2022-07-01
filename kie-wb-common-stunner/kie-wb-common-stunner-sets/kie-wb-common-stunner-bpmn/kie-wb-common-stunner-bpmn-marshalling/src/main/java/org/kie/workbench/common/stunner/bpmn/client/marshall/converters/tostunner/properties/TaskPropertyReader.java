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

package org.kie.workbench.common.stunner.bpmn.client.marshall.converters.tostunner.properties;

import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.customproperties.CustomAttribute;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.customproperties.CustomElement;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.tostunner.DefinitionResolver;

public class TaskPropertyReader extends ActivityPropertyReader {

    protected final Task task;

    public TaskPropertyReader(Task task, BPMNDiagram diagram, DefinitionResolver definitionResolver) {
        super(task, diagram, definitionResolver);
        this.task = task;
    }

    public String getCacheType() {
        return CustomElement.cacheType.of(element).get();
    }

    public Double getCacheValue() {
        return CustomElement.cacheValue.of(element).get();
    }

    public String getResultS3Key() {
        return CustomElement.resultS3Key.of(element).get();
    }

    public String getDbRequestType() {
        return CustomElement.dbRequestType.of(element).get();
    }

    public String getIntegrationType() {
        return CustomElement.integrationType.of(element).get();
    }

    public String getTaskName() {
        return CustomAttribute.serviceTaskName.of(task).get();
    }

    public String getIntegrationMode() {
        String advanceAIType = CustomElement.advanceAIType.of(element).get();
        String integrationMode = CustomElement.integrationMode.of(element).get();
        if (integrationMode != null) {
            return integrationMode;
        } else {
            return advanceAIType;
        }
    }

    public String getSQLAdapterIntegrationName() {
        return CustomElement.integrationName.of(element).get();
    }

    public String getIntegrationName() {
        return CustomElement.integrationIdentity.of(task).get();

    }

    public Boolean getIsAsync() {
        return CustomElement.async.of(element).get();
    }
}
