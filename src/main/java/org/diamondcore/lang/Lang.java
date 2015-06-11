/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|  
                                                                                                      
*/

package org.diamondcore.lang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.diamondcore.lang.exeption.InvalidLangException;
import org.diamondcore.lang.exeption.LangException;
import org.diamondcore.lang.exeption.LangOverrideException;

public class Lang {
	
	private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private static final PrintStream formatter = new PrintStream(baos);
	private static String localeString = null;
	private static Properties locale = null;
	
	/**
	 * Used to set the lang of the program by locale. Once
	 * it has been set, it can no longer be changed until
	 * the program has been shutdown
	 * 
	 * 
	 * @param localeName
	 * @throws InvalidLangException
	 * @author Trent Summerlin
	 */
	public static final void setLang(String localeName) throws InvalidLangException {
		try {
			if(locale != null)
				return; // TODO: Throw exception
			InputStream in = Lang.class.getResource("/lang/" + localeName + ".lang").openStream();
			localeString = localeName;
			locale = new Properties();
			locale.load(in);
		} catch(NullPointerException | IOException e) {
			throw new InvalidLangException(localeName);
		}
	}
	
	/**
	 * Used to add lang to the existing lang file for the
	 * program. This can be useful if a plugin want's to
	 * be multilanguage.
	 * 
	 * <br><br>
	 * 
	 * Note, it is not allowed to override an exisisting lang
	 * 
	 * @param langKey
	 * @param lang
	 * @throws LangOverrideException 
	 * @author Trent Summerlin
	 */
	public static final void add(String langKey, String lang) throws LangOverrideException {
		if(locale.containsKey(langKey))
			throw new LangOverrideException(langKey);
		locale.put(langKey, lang);
	}
	
	/**
	 * Used to get the name of the locale being used
	 * 
	 * @return Locale name
	 * @author Trent Summerlin
	 */
	public static final String getLocale() {
		return localeString;
	}
	
	/**
	 * Used to get a value of a lang by a key
	 * 
	 * @param langKey
	 * @return Lang value by key
	 * @throws LangException
	 * @author Trent Summerlin
	 */
	public static final String get(String langKey) {
		return locale.getProperty(langKey);
	}
	
	/**
	 * Used to get a value of a lang by a key
	 * and format it using a PrintStream
	 * 
	 * @param langKey
	 * @param args
	 * @return Formatted lang by key
	 * @throws LangException
	 * @author Trent Summerlin
	 */
	public static final String get(String langKey, Object... args) {
		formatter.format(get(langKey), args);
		String formatted = new String(baos.toByteArray());
		baos.reset();
		return formatted;
	}
	
	public static void main(String[] args) throws LangException {
		setLang("en_US");
		add("test.ff", "This is a new and %s lang key!");
		System.out.println(get("test.newLang"));
		System.out.println(get("test.newLang", "SUPERDUPERAWESOME"));
	}
	
}
