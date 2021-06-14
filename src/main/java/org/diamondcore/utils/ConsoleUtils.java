/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.utils;



/**
 * Used for help in the console
 * <br><br>
 * This class uses native methods and C code
 * to accomplish these tasks, such as setting
 * the console title, which Java cannot do on
 * it's own.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class ConsoleUtils {
	
	private static final String lib = "/lib/native/console";
	private static final native void internSetConsoleTitle(String s);
	
	static {
		/*if(System.getProperty("os.name").contains("Windows")) {
			try {
				Runtime.getRuntime().load(ConsoleUtils.class.getResource("/native/console.dll").toURI().toURL().getPath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/ // TODO: Load DLL (The DLL functions, but loading it is a doozy)
	}
	
	/**
	 * Used to set the console title
	 * 
	 * @param s
	 * 		- The console title to use
	 * @author Trent Summerlin
	 */
	public static void setConsoleTitle(String s) {
		//if(System.getProperty("os.name").contains("Windows"))
			//internSetConsoleTitle(s);
	}
	
}
