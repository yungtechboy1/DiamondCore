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
 * Thrown whenever a plugin with the same class
 * as another is loaded, this can cause errors
 * as the plugin could be a duplicate of another
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PluginDuplicateException extends PluginException {
	
	private static final long serialVersionUID = -6424744149692732415L;
	
	public PluginDuplicateException(String mainClass) {
		super("The main class " + mainClass + " has been taken by another plugin!");
	}

}
