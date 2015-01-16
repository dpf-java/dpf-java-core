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
package info.paolociccarese.project.dpf.java.core;

import java.util.Map;

/**
 * Interface to be implemented by the components that
 * need to be notified by the activities of a stage
 * object. Stage completion is the most obvious example.
 * 
 * @author Dr. Paolo Ciccarese
 */
public interface IStageListener {

	/**
	 * Called when the stage execution is successfully completed.
	 * @param parentStage The stage wrapping the stage command logic	
	 * @param parameters  List of parametrizations for the pipeline.
	 */
	public void notifyStageCompletion(IStage parentStage, Map<String, String> parameters, Object data);
	
	public void notifyStageSkipped(IStage parentStage, Map<String, String> parameters, Object data);
}
