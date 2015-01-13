package info.paolociccarese.project.dpf.java;

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
