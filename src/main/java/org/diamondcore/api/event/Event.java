/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.api.event;

import org.diamondcore.api.PlayerType;

/**
 * The base for DiamondCore events
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class Event {
	
	public abstract String getName();
	public abstract PlayerType getPlayerType();
	
}
