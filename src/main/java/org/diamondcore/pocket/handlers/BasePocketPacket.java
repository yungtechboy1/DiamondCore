/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.pocket.handlers;

import java.io.IOException;

/**
 * Implemented by normal MCPE packets
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public interface BasePocketPacket {
	
	public int getPacketID();
	public void decode() throws IOException;
	public void sendResponse() throws IOException;
	
}
