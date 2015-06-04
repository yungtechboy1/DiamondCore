/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.utils;

/**
 * Used to help with images in DiamondCore, such as
 * when a image needs to converted to Base64 for the
 * Server list ping
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public final class ImageUtils {
	
	private ImageUtils() {}
	
	public static String toString(File image) throws IOException {
        return new String(Base64.getEncoder().encode(toByteArray(image)));  
    } 
	
	protected static byte[] toByteArray(File file) {
		try {
			FileInputStream fileInputStream = null;
			byte[] bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			for(int i = 0; i < bFile.length; i++) bFile[i] = (byte) fileInputStream.read();
			fileInputStream.close();
			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
