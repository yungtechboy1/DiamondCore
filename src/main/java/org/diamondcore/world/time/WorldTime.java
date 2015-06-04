/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.world.time;

/**
 * Used to manage the world and retrieve info from it
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class WorldTime {
	
	protected static int time = 0;
	
	/**
	 * Used to push up the world time by 1 tick
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static void tick() {
		if(time >= 24000) time = 0;
		else time++;
	}
	
	/**
	 * Used to retrieve the day stage
	 * 
	 * @return Current DayStage
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static DayStage getDayStage() {
		if(time <= 6000) return DayStage.DAY;
		else if(time <= 12000 && time >= 6000) return DayStage.NOON;
		else if(time <= 18000 && time >= 12000) return DayStage.EVENING;
		else if(time >= 18000) return DayStage.NIGHT;
		return null;
	}
	
	/**
	 * Used to retrieve the time of day in ticks
	 * 
	 * @return Time of day (In ticks)
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static int getTime() {
		return time;
	}
	
}
