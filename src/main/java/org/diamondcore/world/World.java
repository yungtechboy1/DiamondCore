/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.world;

import org.diamondcore.utils.ServerSettings;
import org.diamondcore.world.time.DayStage;
import org.diamondcore.world.time.WorldTime;

/**
 * This class can be used to get world info and do things in the world.
 * <br>
 * For example, if I want to. I can spawn a bat, and set a player riding it.
 * <br>
 * I can also set the time, and get the daystage or exact time.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class World {
	
	/**
	 * Used for getting the time in ticks
	 * <br>
	 * This function is used for getting the daystage
	 * <br>
	 * This function is also used for getting the time in 24-hour and and 12-hour time
	 * 
	 * @return World time in ticks
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final int getTime() {
		return WorldTime.getTime();
	}
	
	/**
	 * Used for getting the daystage
	 * 
	 * @return World daystage
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final DayStage getDayStage() {
		return WorldTime.getDayStage();
	}
	
	/**
	 * Used for getting the world name
	 * 
	 * @return World name
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final String getWorldName() {
		return ServerSettings.getLevelName();
	}
	
	/**
	 * Used for getting the world name
	 * <br>
	 * Note: Even though a seed might be "URMOMDOWNTOWN"
	 * <br>
	 * This function will not return that, it instead, will return: -264925342
	 * 
	 * @return World seed (In hash-code form)
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final int getWorldSeed() {
		return ServerSettings.getLevelSeed().hashCode();
	}
	
}
