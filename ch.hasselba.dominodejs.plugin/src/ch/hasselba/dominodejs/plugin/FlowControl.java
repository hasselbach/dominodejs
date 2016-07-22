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

/**
 * FlowControl
 * Idea based on Dominic Porter's "OpenNTF Alliance Chatstore"
 * @author Sven Hasselbach
 */
public class FlowControl {
	private static boolean taskShouldTerminate 	= false;
	private static boolean running				= false;
	private static String command 				= "";
	private static String argument 				= "";


	public static String getCommand() {
		return command;
	}// end getCommand

	public static void setCommand(String command) {
		FlowControl.command = command;
	}// end setCommand

	public static String getArgument() {
		return argument;
	}// end getArgument

	public static void setArgument(String argument) {
		FlowControl.argument = argument;
	}// end setArgument
	
	public static boolean isRunning() {
		return running;
	}// end isRunning

	public static void setRunning(boolean running) {
		FlowControl.running = running;
	}// end setRunning

	public static boolean isTaskShouldTerminate() {
		return taskShouldTerminate;
	}// end isTaskShouldTerminate

	public static void setTaskShouldTerminate(boolean taskShouldTerminate) {
		FlowControl.taskShouldTerminate = taskShouldTerminate;
	}// end setTaskShouldTerminate	
}
