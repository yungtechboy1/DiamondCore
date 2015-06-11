/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                
                                                                                        
 */

package org.diamondcore.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.diamondcore.ChatColor;

/**
 * An implementation of a Logger that supports Log4J2.
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public class Log4j2Logger implements DiamondLogger {

	private final Logger logger;

	public Log4j2Logger(String name) {
		logger = LogManager.getLogger(name);
	}

	@Override
	public void debug(String msg) {
		logger.debug(ChatColor.toConsole(msg));
	}

	@Override
	public void info(String msg) {
		logger.info(ChatColor.toConsole(msg));
	}

	@Override
	public void warn(String msg) {
		logger.warn(ChatColor.toConsole(msg));
	}

	@Override
	public void err(String msg) {
		logger.error(ChatColor.toConsole(msg));
	}
	
	
}
