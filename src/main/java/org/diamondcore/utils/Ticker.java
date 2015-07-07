package org.diamondcore.utils;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is used for Dynamic timing. {@code Thread.sleep()} would work, but
 * when many processes are going on it can cause it to not work properly and sleep
 * later on
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class Ticker extends Thread implements Runnable {
 
	// Finals, do not change these
	private final long fullMilli = 1000L;
	private final int desiredTicks;
 
	// Subject to change
	private int delta;
	private int gameTicks;
	private int cyclesRan = 0;
	private boolean shouldStop = false;
	
	// Sleep
	private HashMap<String, Integer> sleep = new HashMap<String, Integer>();
 
	public Ticker(int desiredTicks) {
		this.desiredTicks = desiredTicks;
		this.gameTicks = 0;
	}
 
	public void run() {
		long startingTick = System.currentTimeMillis();
		int lastGameTick = 0;
		while (true) {
			long currentTick = System.currentTimeMillis();
			long tick = currentTick - startingTick;
			if (tick >= fullMilli / desiredTicks) {
				startingTick = currentTick;
				delta = (gameTicks - lastGameTick);
				lastGameTick = gameTicks;
				if (gameTicks <= desiredTicks) {
					gameTicks++;
					Iterator<String> keys = sleep.keySet().iterator();
					while(keys.hasNext()) {
						String key = keys.next();
						int sleepTick = sleep.get(key);
						if((sleepTick - 1) <= 0)
							sleep.remove(key);
						else
							sleep.put(key, (sleepTick - 1));
						System.out.println(key);
					}
				}
				else {
					gameTicks = 0;
					cyclesRan++;
				}
			}
			if (shouldStop)
				break;
		}
	}
 
	/**
	 * Used to get the current tick position
	 * 
	 * @return Current tick position
	 * @author Trent Summerlin
	 */
	public final int getTick() {
		return gameTicks;
	}
 
	/**
	 * Used to get the amount of desired ticks a second
	 * 
	 * @return Desired ticks a second
	 * @author Trent Summerlin
	 */
	public final int getDesiredTicks() {
		return desiredTicks;
	}
	
	/**
	 * Used to get the cycles ran of the current running ticker
	 * 
	 * @return Cycles ran
	 * @author Trent Summerlin
	 */
	@Deprecated
	public final int getCyclesRan() {
		return cyclesRan;
	}
	
	/**
	 * Used to get the delta, useful in timing if ticks
	 * are skipped
	 * 
	 * @return Ticker delta
	 * @author Trent Summerlin
	 */
	 public final int getDelta() {
	 	return (delta > 1 ? delta : 1); // If delta is 0 it can cause errors in math such as multiplication (? * 0 will always equals 0)
	 }
	 
	 /**
	  * Used to sleep with the ticker running
	  * 
	  * @param key
	  * @param time
	  * 	- Time to sleep in ticks
	  * @author Trent Summerlin
	  */
	 public final void sleep(String key, int time) {
		 sleep.put(key, time);
	 }
	 
	 /**
	  * 
	  * @param key
	  * @return Whether or not the component has slept
	  */
	 public final boolean hasSlept(String key) {
		 return (sleep.containsKey(key) == false);
	 }
	 
 
	/**
	 * Used to stop the ticker completely
	 * 
	 * @author Trent Summerlin
	 */
	public void stopTicker() {
		shouldStop = true;
	}
}
