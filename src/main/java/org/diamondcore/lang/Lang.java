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

public class Lang {
	
	private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private static final PrintStream formatter = new PrintStream(baos);
	private static Properties locale = null;
	
	public static final void setLang(String localeName) throws InvalidLangException {
		try {
			if(locale != null)
				return; // TODO: Throw exception
			InputStream in = Lang.class.getResource("/lang/" + localeName + ".lang").openStream();
			locale = new Properties();
			locale.load(in);
		} catch(NullPointerException | IOException e) {
			throw new InvalidLangException(localeName);
		}
	}
	
	public static final String get(String langKey) {
		return locale.getProperty(langKey);
	}
	
	public static final String get(String langKey, Object... args) {
		formatter.format(get(langKey), args);
		String formatted = new String(baos.toByteArray());
		baos.reset();
		return formatted;
	}
	
	public static void main(String[] args) throws InvalidLangException {
		setLang("en_US");
		System.out.println(get("file.copyError"));
		System.out.println(get("file.copyError", "README.md", "LICNESE"));
	}
	
}
