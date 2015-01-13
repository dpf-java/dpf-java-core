package info.paolociccarese.project.dpf.java;

import java.util.Map;

/**
 * A Stage is one component of the pipeline. It wraps the logic
 * to be executes so that you don't have to worry about the 
 * pipeline management. 
 * 
 * @author Dr. Paolo Ciccarese
 */
public class Stage implements IStage {

	/**
	 * Identifier for the stage, this is used
	 * for matching the parametrization.
	 */
	private String _id;
	
	/**
	 * The command to be executed by the stage. The command
	 * implementation contains all the necessary logic.
	 */
	private IStageCommand _command;
	
	/**
	 * By default each stage is executable (see constructor). 
	 * IF the stage needs to be skipped that has to be declared 
	 * explicitly by using the method setExecutable(IStage.SKIP).
	 */
	private boolean _executable;
	
	/**
	 * The constructors create a Stage object which consists of 
	 * a wrapper for the logic defined by the command implementation.
	 * @param id		The unique identifier of the present stage.
	 * @param command	The logic to be executed.
	 */
	public Stage(String id, IStageCommand command) {
		_executable = EXECUTE;
		_command = command;	
		_id = id;
	}
	
	/**
	 * Returns the identifier of the stage.
	 */
	@Override
	public String getId() {
		return _id;
	}
	
	/**
	 * Returns the command wrapped by this stage.
	 */
	@Override
	public IStageCommand getCommand() {
		return _command;
	}
	
	/**
	 * Sets if this stage is executable or has to be skipped.
	 * @param executable True if the stage is set to be executed within the pipeline
	 */
	@Override
	public void setExecutable(boolean executable) {
		_executable = executable;
	}
	
	/**
	 * Returns true if the stage is currently executable.
	 */
	@Override
	public boolean isExecutable() {
		return _executable;
	}
	
	/**
	 * Triggers the execution of the stage within the pipeline.
	 * @param parameters List of parametrizations for the pipeline.
	 */
	@Override
	public void execute(Map<String, String> parameters, Object data) {
		_command.run(this, parameters, data);
	}
}
