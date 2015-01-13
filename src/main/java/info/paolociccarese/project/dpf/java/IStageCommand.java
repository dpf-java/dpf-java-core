package info.paolociccarese.project.dpf.java;

import java.util.Map;

/**
 * This is the interface to be implemented by all the
 * commands.
 * 
 * @author Dr. Paolo Ciccarese
 */
public interface IStageCommand {
	
	/**
	 * Trigger the execution of the stage within the pipeline.
	 * @param parentStage	The stage wrapping the command logic			
	 * @param parameters	List of parametrizations for the pipeline.
	 *  @param data			The data to process
	 */
	public void run(IStage parentStage, Map<String, String> parameters, Object data);
}
