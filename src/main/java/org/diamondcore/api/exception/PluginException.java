/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.api.exception;

/**
 * Thrown whenever there is a plugin error
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PluginException extends Exception {
	
	private static final long serialVersionUID = -6057450613825764343L;
	
	public PluginException() {
		super("There was a error in running/loading a plugin!");
		return;
	}

	public PluginException(String error) {
		super("There was a error in running/loading a plugin!\n\tCause: " + error);
		return;
	}
	
}
