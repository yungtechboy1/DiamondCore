/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.logging;

/**
 * This is a interface used by the Log4j2Logger in order
 * to be able to log correctly
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public abstract interface DiamondLogger{
	
	String softwareTag = "DiamondCore";
	String infoTag = "INFO";
	String warnTag = "WARN";
	String errorTag = "ERR";
	
	void info(String msg);
	void warn(String msg);
	void err(String msg);
	void debug(String msg);
}
