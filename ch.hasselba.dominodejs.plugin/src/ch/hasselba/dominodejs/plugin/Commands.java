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

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

public final class Commands implements CommandProvider {
	public static final String CMD_START = "start";
	public static final String CMD_QUIT = "quit";
	public Commands() {
		FlowControl.setTaskShouldTerminate(false);
		FlowControl.setCommand("");
	}

	@Override
	public String getHelp() {
		String strTemp = "";
		strTemp =  "---DomiNodeJS Help ---\n" 
				+ "dominodejs start - starts Node.JS server\n"
				+ "dominodejs quit - stops Node.JS server\n";
		
		return strTemp;
	}
	
	public static void _dominodejs(CommandInterpreter ci){
		String command = ci.nextArgument();
			
		if( CMD_START.equalsIgnoreCase( command ) ){
			FlowControl.setCommand(CMD_START);
		}
		if( CMD_QUIT.equalsIgnoreCase( command ) ){
			FlowControl.setCommand(CMD_QUIT);
		}	
	}

}
