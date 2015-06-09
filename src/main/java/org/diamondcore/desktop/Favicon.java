/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

/**
 * This class represents a MCPC favicon.
 * It is used in the Server List Ping as
 * an image, they are useful as they can
 * give the player a taste of what the
 * server is like before they join 
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class Favicon {
	
	private final String base64;
	
	private Favicon(String base64) {
		this.base64 = base64;
	}
	
	/**
	 * Used to get an instance of a Favicon easily
	 * 
	 * @param file
	 * 		- The file to be used in the Favicon instance
	 * @return Favicon instace
	 * @throws IOException
	 * @author Trent Summerlin
	 */
	public static Favicon getInstance(File file) throws IOException {
		byte[] favicon = IOUtils.toByteArray(new FileInputStream(file));
		String base64 = new String(Base64.getEncoder().encode(favicon));
		return new Favicon(base64);
	}
	
	/**
	 * Used to get the image as a String so
	 * Minecraft can read and display it properly
	 * 
	 * @return Favicon represented as a String
	 * @author Trent Summerlin
	 */
	public String toString() {
		return ("data:image/png;base64," + this.base64);
	}
	
}
