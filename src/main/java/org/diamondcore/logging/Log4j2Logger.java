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
import org.diamondcore.lang.Lang;

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

	@Override
	public void translate(String lang) {
		logger.info(ChatColor.toConsole(Lang.get(lang)));
	}

	@Override
	public void translate(String lang, Object... args) {
		logger.info(ChatColor.toConsole(Lang.get(lang, args)));
	}

	@Override
	public void translatewarn(String lang) {
		logger.warn(ChatColor.toConsole(Lang.get(lang)));
	}

	@Override
	public void translatewarn(String lang, Object... args) {
		logger.warn(ChatColor.toConsole(Lang.get(lang, args)));
	}

	@Override
	public void translateerror(String lang) {
		logger.error(ChatColor.toConsole(Lang.get(lang)));
	}

	@Override
	public void translateerror(String lang, Object... args) {
		logger.error(ChatColor.toConsole(Lang.get(lang, args)));
	}

	@Override
	public void translatedebug(String lang) {
		logger.debug(ChatColor.toConsole(Lang.get(lang)));
	}

	@Override
	public void translatedebug(String lang, Object... args) {
		logger.debug(ChatColor.toConsole(Lang.get(lang, args)));
	}
	
}
