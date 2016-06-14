/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.workflow;

/**
 * Launch workflow instance.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface WorkflowLauncher {

    /**
     * Launch workflow execution.
     *
     * @param group The group of the workflow
     * @param name The name of the workflow
     * @param version The version of the workflow
     * @param arguments The arguments of the workflow
     */
    public void launch(String appId, String group, String name, String version, Object[] arguments) throws Throwable;
}