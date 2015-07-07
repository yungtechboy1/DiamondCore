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
	
	private static int time = 0;
	
	/**
	 * Used to push up the world time by 1 tick
	 * 
	 * @author Trent Summerlin
	 */
	public static void tick() {
		if(time >= 24000)
			time = 0;
		else
			time++;
	}
	
	/**
	 * Used to push up the world time by the amount
	 * of ticks desired by the user
	 * <br><br>
	 * Note: If the max ticks in a day are reached
	 * when this is used, it will automatically adjust
	 * the time of day to match the correct tick amount
	 * 
	 * @param ticks
	 * 		- The amount of ticks to add to the time
	 * @author Trent Summerlin
	 */
	public static void tick(int ticks) {
		time += ticks;
		while(time >= 24000)
			time -= 24000;
	}
	
	/**
	 * Used to set the tick of day desired by the user
	 * <br><br>
	 * Note: If the max ticks in a day are reached
	 * when this is used, it will automatically adjust
	 * the time of day to match the correct tick amount
	 * 
	 * @param tick
	 * 		- The tick of the day to set the time
	 * @author Trent Summerlin
	 */
	public static void setTick(int tick) {
		time = tick;
		while(time >= 24000)
			time -= 24000;
	}
	
	/**
	 * Used to retrieve the day stage
	 * 
	 * @return Current DayStage
	 * @author Trent Summerlin
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
	 */
	public static int getTime() {
		return time;
	}
	
}
