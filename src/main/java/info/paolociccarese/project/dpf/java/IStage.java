/*
* Copyright 2014 Paolo Ciccarese
*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package info.paolociccarese.project.dpf.java;

import java.util.Map;

/**
 * Interface exposing the main features for a pipeline stage.
 * 
 * @author Dr. Paolo Ciccarese
 */
public interface IStage {
	
	public static final boolean EXECUTE = true;
	public static final boolean SKIP = false;
	
	/**
	 * Returns the identifier of the stage.
	 * @return The stage identifier.
	 */
	public String getId();
	
	/**
	 * Returns the command wrapped by this stage.
	 * @return The logic executed by this stage.
	 */
	public IStageCommand getCommand();
	
	/**
	 * Sets if this stage is executable or has to be skipped.
	 * @param executable True if the stage is set to be executed within the pipeline
	 */
	public void setExecutable(boolean executable);
	
	/**
	 * Returns true if the stage is currently executable.
	 * @return True if the stage has to be executed.
	 */
	public boolean isExecutable();
	
	/**
	 * Triggers the execution of the stage within the pipeline.
	 * @param parameters List of parametrizations for the pipeline.
	 */
	public void execute(Map<String, String> parameters, Object data);
}
