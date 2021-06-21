/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.entity.player;

import java.util.UUID;

/**
 * This class represents a Minecraft: Pocket Edition
 * player, it extends the Player object. Thus making it
 * easier to handle for Plugin makers who just want to
 * do things like get their world location, name, etc.
 * <br><br>
 * But, if needed, a plugin maker can cast the player
 * object to a PocketPlayer and do something like get
 * his DatagramSocket connection and send a raw packet
 * to it
 * 
 * @version 0.1.0-SNAPSHOT
 * @author Trent Summerlin
 */
public class PocketPlayer extends Player {

	public PocketPlayer(String username, UUID uuid) {
		super(username, uuid);
	}
	
	@Override
	public void sendMessage(String msg) {
		// TODO: Send message
	}
	
}
