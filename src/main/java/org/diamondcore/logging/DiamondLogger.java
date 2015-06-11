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

/**
 * This is a interface used by the Log4j2Logger in order
 * to be able to log correctly
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public abstract interface DiamondLogger{
	
	String softwareTag = "DiamondCore";
	String infoTag = "INFO";
	String warnTag = "WARN";
	String errorTag = "ERR";
	
	void info(String msg);
	void warn(String msg);
	void err(String msg);
	void debug(String msg);
	
	void translate(String lang);
	void translate(String lang, Object... args);
	void translatewarn(String lang);
	void translatewarn(String lang, Object... args);
	void translateerror(String lang);
	void translateerror(String lang, Object... args);
	void translatedebug(String lang);
	void translatedebug(String lang, Object... args);
}
