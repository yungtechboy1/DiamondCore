/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore;


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
			try {
				shouldDebug = Boolean.parseBoolean(args[0]);
			}
			catch(Exception E) {
				shouldDebug = false;
			}
			new DiamondCoreServer(shouldDebug);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to see if the server is in debug mode
	 * 
	 * @return Debug mode state
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static boolean getShouldDebug() {
		return shouldDebug;
	}
	
}
