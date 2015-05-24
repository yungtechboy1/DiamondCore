/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * This class is used to easily read, and write to files and
 * do some other things with IO
 * 
 * @author Trent Summerlin
 * @version 1.2
 */
public class FileUtils {
	
	/**
	 * Used to read from a text file located on the system memory
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param file
	 *  	Text file to read from
	 * @return Text file string result
	 * @throws IOException
	 */
	public static String readFromFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while((line = reader.readLine()) != null) {
			builder.append(line);
		}
		reader.close();
		return builder.toString();
	}
	
	/**
	 * Used to write to a text file located on the system memory
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param File
	 * @throws IOException
	 */
	public static void writeToFile(File file, String contents) throws IOException {
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		writer.write(contents);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Used to read from a text file from a HTTP URL
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param File
	 * @return Text file string result
	 * @throws IOException
	 */
	public static String readFromURL(URL url) throws IOException {
		Scanner URLScanner = new Scanner(url.openStream());
		StringBuilder Builder = new StringBuilder();
		while(URLScanner.hasNext()) {
			Builder.append(URLScanner.next());
		}
		URLScanner.close();
		return Builder.toString();
	}
	
	public static org.apache.commons.io.FileUtils getApache() {
		return new org.apache.commons.io.FileUtils();
	}
	
}
