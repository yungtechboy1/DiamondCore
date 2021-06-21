/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.entity.player;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import org.diamondcore.desktop.packet.ClientDisconnectPacket;

/**
 * This class represents a Minecraft Desktop
 * player, it extends the Player object. Thus making it
 * easier to handle for Plugin makers who just want to
 * do things like get their world location, name, etc.
 * <br><br>
 * But, if needed, a plugin maker can cast the player
 * object to a DesktopPlayer and do something like get
 * his Socket connection and send a raw packet to it
 * 
 * @version 0.1.0-SNAPSHOT
 * @author Trent Summerlin
 */
public class DesktopPlayer extends Player {
	
	private final Socket s;
	
	public DesktopPlayer(Socket s, String username, UUID uuid) {
		super(username, uuid);
		this.s = s;
	}
	
	@Override
	public void sendMessage(String msg) {
		// Send message
	}
	
	@Override
	public void disconnect(String reason) throws IOException {
		ClientDisconnectPacket packet = new ClientDisconnectPacket(s, reason);
		packet.setShouldLog(true);
		packet.disconnect();
	}
	
}
