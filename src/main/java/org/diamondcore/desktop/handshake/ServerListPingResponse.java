/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.desktop.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.diamondcore.api.PlayerType;
import org.diamondcore.api.event.EventFactory;
import org.diamondcore.api.event.server.ServerListPingEvent;
import org.diamondcore.desktop.DesktopPacket;
import org.diamondcore.desktop.PacketIDList;
import org.diamondcore.desktop.packet.HandshakePacket;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.utils.ServerSettings;
import org.json.simple.JSONObject;

public class ServerListPingResponse extends HandshakePacket {
	
	// Socket info
	protected Socket s;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	// Packet info
	JSONObject object = new JSONObject();
	
	// Event side
	ServerListPingEvent event;
	
	@SuppressWarnings("unchecked")
	public ServerListPingResponse(Socket s) throws IOException, DiamondException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Throw event before putting together json because something might be changed by a plugin
		this.event = new ServerListPingEvent(PlayerType.DESKTOP, s.getInetAddress(), s.getPort(), ServerSettings.getPCMOTD());
		EventFactory.throwEvent(this.event);
		
		// Create MOTD JSON Object
		JSONObject versionMap = new JSONObject();
		versionMap.put("name", event.getProtocolTag());
		versionMap.put("protocol", event.getProtocol());
								
		// Players
		JSONObject playerMap = new JSONObject();
		playerMap.put("max", event.getMaxPlayers());
		playerMap.put("online", event.getOnlinePlayers());
		
		// TODO: Add player sample list
		/**
		 * How to add a player to the sample list:
		 * Step 1: Put the player name
		 * Step 2: Put the player's UUID (A random UUID will work as well, this will be useful for MCPE players)
		 */
										
		// Description and favicon
		JSONObject info = new JSONObject();
		info.put("text", event.getMOTD());
			
		// Put favicon if available
		if(ServerSettings.getServerFavicon() != null)
			object.put("favicon", event.getFavicon().toString());
					
		// Now put everything all together
		object.put("version", versionMap);
		object.put("players", playerMap);
		object.put("description", info);
		
		// Send response
		this.sendResponse();
	}
	
	@Override
	public void decode() throws IOException {
		// Nothing to decode
	}

	@Override
	public void sendResponse() throws IOException {
		DesktopPacket packet = new DesktopPacket(PacketIDList.SERVER_PING_RESPONSE);
		packet.writeString(object.toJSONString());
		
		output.write(packet.toByteArray());
		output.flush();
	}
	
	
	
}
