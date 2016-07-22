package ch.hasselba.dominodejs.plugin;

/*************************************************************************
 Copyright 2016 Sven Hasselbach

 Licensed under the Apache License, Version 2.0 (the "License"); you may 
 not use this file except in compliance with the License. 
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied. See the License for the specific language governing
 permissions and limitations under the License
 
 *************************************************************************/

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;

import com.ibm.dots.task.AbstractServerTaskExt;
import com.ibm.dots.task.RunWhen;

public class Server extends AbstractServerTaskExt {

	NodeJSThread nodejsThread = null;

	@Override
	public void dispose()  {
		if( nodejsThread != null ){
			nodejsThread.interrupt();
		}
		logMessage("Shutdown complete.");
	}

	@Override
	protected void doRun(RunWhen runWhen, IProgressMonitor monitor){

		FlowControl.setRunning(true);
		FlowControl.setTaskShouldTerminate(false);
				
		while ( !monitor.isCanceled() & !FlowControl.isTaskShouldTerminate())  {
				
			pauseProcessing(1);
				
			if (FlowControl.getCommand().equalsIgnoreCase("quit")) {
				FlowControl.setCommand("");
				FlowControl.setArgument("");
				FlowControl.setTaskShouldTerminate(true);
					
				if( nodejsThread != null ){
					nodejsThread.interrupt();
				}
					
			}	
				
			if (FlowControl.getCommand().equalsIgnoreCase(Commands.CMD_START)) {
				FlowControl.setCommand("");
				FlowControl.setArgument("");
				FlowControl.setTaskShouldTerminate(false);
					
				if( nodejsThread == null ){
					nodejsThread = new NodeJSThread();
					nodejsThread.start();
				}
					
			}
		}
			
		FlowControl.setTaskShouldTerminate(true);
		
	}
	
	private void pauseProcessing(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
