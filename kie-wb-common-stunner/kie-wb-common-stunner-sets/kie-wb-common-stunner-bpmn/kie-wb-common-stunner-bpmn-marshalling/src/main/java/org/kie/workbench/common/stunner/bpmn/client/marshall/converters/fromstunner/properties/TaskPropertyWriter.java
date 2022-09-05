package org.kie.workbench.common.stunner.bpmn.client.marshall.converters.fromstunner.properties;

import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.Task;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.customproperties.CustomAttribute;
import org.kie.workbench.common.stunner.bpmn.client.marshall.converters.customproperties.CustomElement;
import org.kie.workbench.common.stunner.bpmn.definition.AdvanceAITask;
import org.kie.workbench.common.stunner.bpmn.definition.AmazonPhotoValidationTask;
import org.kie.workbench.common.stunner.bpmn.definition.AmazonTask;
import org.kie.workbench.common.stunner.bpmn.definition.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.DragonPayTask;
import org.kie.workbench.common.stunner.bpmn.definition.FinScoreTask;
import org.kie.workbench.common.stunner.bpmn.definition.JuicyTask;
import org.kie.workbench.common.stunner.bpmn.definition.MBATask;
import org.kie.workbench.common.stunner.bpmn.definition.S3FetchTask;
import org.kie.workbench.common.stunner.bpmn.definition.SQLAdapterTask;
import org.kie.workbench.common.stunner.bpmn.definition.SeonTask;
import org.kie.workbench.common.stunner.bpmn.definition.TeleSignTask;
import org.kie.workbench.common.stunner.bpmn.definition.TrustingSocialTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.TaskGeneralSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.AdvanceAITaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.AmazonPhotoValidationTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.AmazonTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.DragonPayTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.FinScoreTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.JuicyTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MBATaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.S3FetchTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.SQLAdapterTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.SeonTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TeleSignTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TrustingSocialTaskExecutionSet;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import java.util.Set;

public class TaskPropertyWriter extends ActivityPropertyWriter {

    private final Task task;

    public TaskPropertyWriter(Task task, VariableScope variableScope, Set<DataObject> dataObejcts) {
        super(task, variableScope, dataObejcts);
        this.task = task;
    }

    public void setResultS3Key(String resultS3Key) {
        CustomElement.resultS3Key.of(task).set(resultS3Key);
    }

    public void setCacheValue(Double value) {
        CustomElement.cacheValue.of(task).set(value);
    }

    public void setIntegrationType(String async) {
        CustomElement.integrationType.of(task).set(async);
    }

    public void setIntegrationIdentity(String integrationIdentity) {
        CustomElement.integrationIdentity.of(task).set(integrationIdentity);
    }

    public void setIntegrationMode(String value) {
        CustomElement.integrationMode.of(task).set(value);
    }

    public void setIsAsync(Boolean isAsync) {
        CustomElement.async.of(task).set(isAsync);
    }

    public void setTaskName(final String value) {
        CustomAttribute.serviceTaskName.of(task).set(value);
    }

    public TaskPropertyWriter fillWithTaskExecutionSet(final BaseTask baseTask, final Node<View<Object>, ?> n) {
        TaskGeneralSet general = baseTask.getGeneral();
        setName(general.getName().getValue());
        setDocumentation(general.getDocumentation().getValue());
        setTaskName("Integration");
        setAbsoluteBounds(n);
        setSimulationSet(baseTask.getSimulationSet());

        if (baseTask instanceof DragonPayTask) {
            DragonPayTaskExecutionSet executionSet = ((DragonPayTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof AmazonTask) {
            AmazonTaskExecutionSet executionSet = ((AmazonTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof SeonTask) {
            SeonTaskExecutionSet executionSet = ((SeonTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof AdvanceAITask) {
            AdvanceAITaskExecutionSet executionSet = ((AdvanceAITask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
            setIntegrationMode(executionSet.getIntegrationMode().getValue());
        } else if (baseTask instanceof TrustingSocialTask) {
            TrustingSocialTaskExecutionSet executionSet = ((TrustingSocialTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
            setIntegrationMode(executionSet.getIntegrationMode().getValue());
        } else if (baseTask instanceof FinScoreTask) {
            FinScoreTaskExecutionSet executionSet = ((FinScoreTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
            setIntegrationMode(executionSet.getIntegrationMode());
        } else if (baseTask instanceof SQLAdapterTask) {
            SQLAdapterTaskExecutionSet executionSet = ((SQLAdapterTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof TeleSignTask) {
            TeleSignTaskExecutionSet executionSet = ((TeleSignTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof AmazonPhotoValidationTask) {
            AmazonPhotoValidationTaskExecutionSet executionSet = ((AmazonPhotoValidationTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
            setIntegrationMode(executionSet.getIntegrationMode().getValue());
        } else if (baseTask instanceof S3FetchTask) {
            S3FetchTaskExecutionSet executionSet = ((S3FetchTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof MBATask) {
            MBATaskExecutionSet executionSet = ((MBATask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        } else if (baseTask instanceof JuicyTask) {
            JuicyTaskExecutionSet executionSet = ((JuicyTask) baseTask).getExecutionSet();
            setIntegrationType(executionSet.getIntegrationType().getValue());
            setIntegrationIdentity(executionSet.getIntegrationIdentity());
            setCacheValue(executionSet.getCacheValue());
            setResultS3Key(executionSet.getResultS3Key());
        }

        return this;
    }
}
