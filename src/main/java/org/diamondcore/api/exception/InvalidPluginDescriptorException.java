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
 * Thrown when a plugin descriptor is invalid
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class InvalidPluginDescriptorException extends PluginException {

	private static final long serialVersionUID = -763784406492530457L;
	
	public InvalidPluginDescriptorException(String descriptor) {
		super("Invalid Plugin Descriptor (" + descriptor + ")");
	}
	
}
