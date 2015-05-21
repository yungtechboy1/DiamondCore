package net.trenterprises.diamondcore.cross.api.python;

import net.trenterprises.diamondcore.cross.api.PluginPacketHandler;


/**
 * This class is used to manage the console for the python side of the
 * application. <br>
 * <br>
 * It can be used in order to send input, and get output from the python
 * console.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PythonConsole extends Thread implements Runnable {

	public void run() {
		/* This class is currently not used as python
		 * currently has a outputstream issue, causing the
		 * outputstream data to not be fired until the
		 * program is completed.
		 * 
		 * However, it is used to start the python server itself
		 */
		
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "plugin_python_bridge/run.py", PluginPacketHandler.listenerPort + "");
			pb.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new PythonConsole().start();
	}

}
