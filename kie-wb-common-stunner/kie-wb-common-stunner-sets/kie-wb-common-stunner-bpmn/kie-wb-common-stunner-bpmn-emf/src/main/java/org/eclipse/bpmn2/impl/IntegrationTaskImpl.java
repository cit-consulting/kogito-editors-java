/**
 * <copyright>
 * 
 * Copyright (c) 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Reiner Hille-Doering (SAP AG) - initial API and implementation and/or initial documentation
 * 
 * </copyright>
 */

package org.eclipse.bpmn2.impl;

import com.google.gwt.user.client.rpc.GwtTransient;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.IntegrationTask;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

public class IntegrationTaskImpl extends TaskImpl implements IntegrationTask {

    protected static final String SCRIPT_DEFAULT = null;

    @GwtTransient
    protected String script = SCRIPT_DEFAULT;

    protected IntegrationTaskImpl() {
        super();
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String newScript) {
        String oldScript = script;
        script = newScript;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Bpmn2Package.SCRIPT_TASK__SCRIPT, oldScript, script));
    }

    @Override
    protected EClass eStaticClass() {
        return Bpmn2Package.Literals.INTEGRATION_TASK;
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();
        return super.toString() + " (script: " + script + " )";
    }
}

/*

    protected static final String SCRIPT_EDEFAULT = null;

    @GwtTransient
    protected String script = SCRIPT_EDEFAULT;

    protected static final String SCRIPT_FORMAT_EDEFAULT = null;

    protected IntegrationTaskImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return Bpmn2Package.Literals.SCRIPT_TASK;
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String newScript) {
        String oldScript = script;
        script = newScript;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Bpmn2Package.SCRIPT_TASK__SCRIPT, oldScript, script));
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();
        return super.toString() + " (script: " + script + " )";
    }
*/
