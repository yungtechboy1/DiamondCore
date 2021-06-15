/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore;

import org.apache.commons.io.IOUtils;


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
			
			//String logo = IOUtils.toString(run.class.getResource("/logo.txt").openStream());
			System.out.println("DiamondCore Started");
			new Server(shouldDebug);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
