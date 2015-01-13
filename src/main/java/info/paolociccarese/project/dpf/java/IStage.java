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
