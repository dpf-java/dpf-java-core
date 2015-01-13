package info.paolociccarese.project.dpf.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class providing the basic implementation of a pipeline.
 * 
 * @author Dr. Paolo Ciccarese
 */
public abstract class APipeline implements IStageListener /*, IParametersCache*/ {
	
	private final Logger log = LoggerFactory.getLogger(APipeline.class);
	
	public static final int FIRST_STAGE = 0;
	
	public enum PipelineStatus {
	    CREATED, 		
	    INITIALIZED, 
	    EXECUTING, 
	    TERMINATED
	}
	
	public enum PipelineAction {
		EXECUTE,
		SUSPEND
	}
	
	protected PipelineStatus _pipelineStatus;
	protected PipelineAction _pipelineAction;
	protected long _pipelineStartTime;
	
	protected HashMap<Integer, Long> _stagesStartTimes;
	protected ArrayList<IStage> _stages;
	private int _currentStage;
	
	
	public APipeline() {
		_pipelineStatus = PipelineStatus.CREATED;
		_stages =  new ArrayList<IStage>();
		_stagesStartTimes = new HashMap<Integer, Long>();
		_currentStage = FIRST_STAGE;
	}
	
	private void setup() {
		if(_pipelineStatus==PipelineStatus.CREATED 
				|| _pipelineStatus==PipelineStatus.TERMINATED) {
			init();
		} else if(_pipelineStatus==PipelineStatus.EXECUTING) {
			log.error(getPipelineName() + " > Already executing");
			throw new RuntimeException("Pipeline already executing");
		} else if(_pipelineStatus==PipelineStatus.INITIALIZED) {
			log.warn(getPipelineName() + " > Already initialized");
			log.warn(getPipelineName() + " > Re-init");
			init();
		}
	}
	
	private void init() {
		log.info(getPipelineName() + " > Init");
		_pipelineStatus = PipelineStatus.INITIALIZED;
		_currentStage = FIRST_STAGE;			
	}
	
	public void start(Map<String, String> parameters, Object data) {
		_pipelineStartTime = System.currentTimeMillis();
		
		setup();
		
		if(_pipelineStatus==PipelineStatus.INITIALIZED) {
			log.info(getPipelineName() + " > Start");

			_pipelineAction = PipelineAction.EXECUTE;
			_pipelineStatus = PipelineStatus.EXECUTING;
			
			if(!_stages.isEmpty()) {
				executeStage(parameters, data, _currentStage);
			}	
		} else if(_pipelineStatus==PipelineStatus.CREATED
				|| _pipelineStatus==PipelineStatus.TERMINATED) {
			log.warn(getPipelineName() + " > Not initialized");
			pipelineCompleted();
		}
	}
	
	private void executeStage(Map<String, String> parameters, Object data, int currentStage) {
		Long stageStartTime = System.currentTimeMillis();
		try {
			String commandName = _stages.get(currentStage).getCommand().getClass().getName();
			String isStageExecutable = parameters.get(commandName);
			if(isStageExecutable!=null) _stages.get(currentStage).setExecutable(Boolean.parseBoolean(isStageExecutable)); 
			if(_stages.get(currentStage).isExecutable()) {
				logINFO(_stages.get(currentStage), "Executing Stage", commandName);
				_stagesStartTimes.put(_stages.get(currentStage).hashCode(), stageStartTime);
				_stages.get(currentStage).execute(parameters, data);
			} else {
				logINFO(_stages.get(currentStage), "Skipping Stage", commandName);
				_stagesStartTimes.put(_stages.get(currentStage).hashCode(), stageStartTime);
				notifyStageSkipped(_stages.get(currentStage), parameters, data);
			}
		} catch(Exception e) {
			log.error("Stage execution failed: " + e.getMessage());
			pipelineTerminated(_pipelineStartTime);
		}
	}
	
	@Override
	public void notifyStageCompletion(IStage stage, Map<String, String> parameters, Object data) {
		long startTime = _stagesStartTimes.remove(stage.hashCode());
		logINFO(stage, "Stage", "completed in (ms) " + (System.currentTimeMillis() - startTime));
		next(parameters, data);
	}
	
	@Override
	public void notifyStageSkipped(IStage stage, Map<String, String> parameters, Object data) {
		long startTime = _stagesStartTimes.remove(stage.hashCode());
		logINFO(stage, "Stage", "skipped in (ms) " + (System.currentTimeMillis() - startTime));
		next(parameters, data);
	}
	
	public void next(Map<String, String> parameters, Object data) {
		if(_pipelineStatus!=PipelineStatus.TERMINATED) { 
			if(_stages.size()>0 && _stages.size()>(++_currentStage)) {
				executeStage(parameters, data, _currentStage);
			} else {
				pipelineCompleted();
			}
		} else {
			log.error(getPipelineName() + " > EXECUTING STAGE AFTER TERMINATION " + _currentStage);
		}
	}
	
	private void pipelineCompleted() {
		log.info(getPipelineName() + " > Completed in (ms) " + (System.currentTimeMillis() - _pipelineStartTime));
		_pipelineStatus = PipelineStatus.TERMINATED;
		pipelineCompleted(_pipelineStartTime);
	}
	
	public void suspend() {
		_pipelineAction = PipelineAction.SUSPEND;
	}
	
	public void resume() {
		_pipelineAction = PipelineAction.EXECUTE;
	}
	
	
	
	
	public String getPipelineName() {
		return "Pipeline";
	}
	
	public abstract void pipelineTerminated(long startTime);
	public abstract void pipelineSuspended(long startTime);
	public abstract void pipelineResumed(long startTime);
	public abstract void pipelineCompleted(long startTime);
	
	private void logINFO(IStage stage, String prefix, String postfix) {
		log.info(getPipelineName() + " > " + prefix + " [ID:" + stage.getId() + "/HASH:" + stage.hashCode() +  "] " + postfix );
	}
}

