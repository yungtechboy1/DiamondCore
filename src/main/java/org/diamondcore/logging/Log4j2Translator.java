package org.diamondcore.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.diamondcore.ChatColor;
import org.diamondcore.lang.Lang;

public class Log4j2Translator implements DiamondTranslator {
	
	private final Logger logger;
	
	public Log4j2Translator(String name) {
		this.logger = LogManager.getLogger(name);
	}
	
	@Override
	public void info(String lang) {
		logger.info(ChatColor.toConsole(Lang.get(lang)));
	}
	
	@Override
	public void info(String lang, Object... args) {
		logger.info(ChatColor.toConsole(Lang.get(lang, args)));
	}

	@Override
	public void warn(String lang) {
		logger.warn(ChatColor.toConsole(Lang.get(lang)));
	}
	
	@Override
	public void warn(String lang, Object... args) {
		logger.warn(ChatColor.toConsole(Lang.get(lang, args)));
	}
	
	@Override
	public void err(String lang) {
		logger.error(ChatColor.toConsole(Lang.get(lang)));
	}
	
	@Override
	public void err(String lang, Object... args) {
		logger.error(ChatColor.toConsole(Lang.get(lang, args)));
	}

	@Override
	public void debug(String lang) {
		logger.debug(ChatColor.toConsole(Lang.get(lang)));
	}
	
	@Override
	public void debug(String lang, Object... args) {
		logger.debug(ChatColor.toConsole(Lang.get(lang, args)));
	}

}
