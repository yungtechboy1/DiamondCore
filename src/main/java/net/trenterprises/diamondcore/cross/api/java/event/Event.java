/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event;

import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This is the main event class, two other classes extend off of this one.
 * <br>
 * They are DesktopEvent and PocketEvent and other classes extend off of those
 * <br>
 * This class does not do anything. It is just used for catching all events when they happen.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract class Event {
	
	DiamondLogger logger = new Log4j2Logger(DiamondLogger.softwareTag);
	
	public DiamondLogger getLogger() {
		return logger;
	}
	
	public abstract String getName();
	
}
