/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

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
