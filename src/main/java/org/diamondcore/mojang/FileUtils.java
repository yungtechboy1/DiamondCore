/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|
                                                                                                 
*/

package org.diamondcore.mojang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * --- NOTE ---
 * 
 * THIS CLASS WILL BE DELETED SOON!
 * 
 * --- NOTE ---
 *
 */
public class FileUtils {
	
	public static String readFromURL(URL url) throws IOException {
		InputStream in = url.openStream();
		StringBuilder builder = new StringBuilder();
		int c = -1;
		while((c = in.read()) != -1) builder.append((char) c);
		return builder.toString();
	}
	
}
