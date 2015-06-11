/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore;

import org.apache.commons.io.IOUtils;
import org.diamondcore.utils.SplashChooser;


/**
 * This class is used to start up the DiamondCore program
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class run {
	
	static boolean shouldDebug = false;
	
	public static void main(String[] args) {
		try {
			if(args.length >= 1)
				shouldDebug = Boolean.parseBoolean(args[0]);
			else
				shouldDebug = false;
			
			String logo = IOUtils.toString(run.class.getResource("/logo.txt").openStream());
			String splash = " -" + SplashChooser.chooseSplash();
			System.out.println(logo + "\n" + splash + "\n");
			new Server(shouldDebug);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
