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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.Session;
import lotus.notes.NotesThread;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class NodeJSThread extends Thread {

	static NodeJS nodejs;
	Session session; 
	
	static String NODE_SCRIPT = "var http = require('http');\n" + ""
			+ "var server = http.createServer(function (request, response) {\n"
			+ " response.writeHead(200, {'Content-Type': 'text/plain'});\n"
			+ " response.end(someJavaMethod());\n" + "});\n" + ""
			+ "server.listen(9000);\n"
			+ "console.log('Server running at http://127.0.0.1:9000/');";

	@Override
	public void run() {
		
		NotesThread.sinitThread();
		try {
			session = NotesFactory.createSession();
		} catch (NotesException e) {
			e.printStackTrace();
		}
		System.out.println( "Starting NodeJS...");
		if( nodejs == null )
			nodejs = NodeJS.createNodeJS();
		

		JavaCallback callback = new JavaCallback() {
	
			public Object invoke(V8Object receiver, V8Array parameters) {
				try {
					return "Hello, DominoWorld! " + session.getCommonUserName();
				} catch (NotesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "ERROR!";
			}
		};
		
		nodejs.getRuntime().registerJavaMethod(callback, "someJavaMethod");
		File nodeScript = null;
		try {
			nodeScript = createTemporaryScriptFile(NODE_SCRIPT, "example");
		} catch (IOException e) {

			e.printStackTrace();
		}

		nodejs.exec(nodeScript);
		
		while( nodejs.isRunning() && !(this.isInterrupted()) ){
			nodejs.handleMessage();
		}
		
		nodejs.release();
		System.out.println( "Stopped NodeJS.");
		try {
			session.recycle();
		} catch (NotesException e) {
			e.printStackTrace();
		}
		NotesThread.stermThread();
		
	}
	
	
	private static File createTemporaryScriptFile(String script, String name)
			throws IOException {
		File tempFile = File.createTempFile(name, ".js.tmp");
		PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
		try {
			writer.print(script);
		} finally {
			writer.close();
		}
		return tempFile;
	}

}
