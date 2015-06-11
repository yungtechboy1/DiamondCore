package org.diamondcore.logging;

public interface DiamondTranslator {
	
	void info(String lang);
	void info(String lang, Object... args);
	void warn(String lang);
	void warn(String lang, Object... args);
	void err(String lang);
	void err(String lang, Object... args);
	void debug(String lang);
	void debug(String lang, Object... args);
	
}
