package info.paolociccarese.project.dpf.java;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Dr. Paolo Ciccarese
 */
public class DummyCommand implements IStageCommand {

	IStageListener _listener;
	
	public DummyCommand(IStageListener listener) {
		_listener = listener;
	}
	
	@Override
	public void run(IStage stage, Map<String, String> parameters, Object data) {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO log end of wait
			System.out.println("++++");
			//Thread.currentThread().interrupt();
			
		}
		_listener.notifyStageCompletion(stage, parameters, data);
	}
}
