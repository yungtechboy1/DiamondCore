/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

 */

package org.diamondcore.utils;

/**
 * This class is used for Dynamic timing. {@code Thread.sleep()} would work, but
 * when <br>
 * many processes are going on it can cause it to not work properly and sleep
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
	private int gameTicks;
	private boolean shouldStop = false;

	public Ticker(int desiredTicks) {
		this.desiredTicks = desiredTicks;
	}

	public void run() {
		long startingTick = System.currentTimeMillis();
		gameTicks = 0;
		while (true) {
			long currentTick = System.currentTimeMillis();
			long tick = currentTick - startingTick;
			if (tick >= fullMilli / desiredTicks) {
				startingTick = currentTick;
				if (gameTicks < desiredTicks)
					gameTicks++;
				else
					gameTicks = 0;
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
	 * @version 1.0
	 */
	public final int getTick() {
		return gameTicks;
	}

	/**
	 * Used to get the amount of desired ticks a second
	 * 
	 * @return Desired ticks a second
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final int getDesiredTicks() {
		return desiredTicks;
	}

	/**
	 * Used to stop the ticker completely
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void stopTicker() {
		shouldStop = true;
	}
}