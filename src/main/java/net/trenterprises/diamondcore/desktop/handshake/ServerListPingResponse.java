/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.desktop.handshake;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.PlayerType;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.EventDispatcher;
import net.trenterprises.diamondcore.cross.api.java.event.server.ServerListPingEvent;
import net.trenterprises.diamondcore.cross.utils.VarInt;
import net.trenterprises.diamondcore.desktop.DesktopPacketIDList;

import org.json.simple.JSONArray;
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
	public ServerListPingResponse(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Throw event before putting together json because something might be changed by a plugin
		this.event = new ServerListPingEvent(PlayerType.DESKTOP, s.getInetAddress(), s.getPort(), ServerSettings.getPCMOTD(), ServerSettings.getServerFavicon());
		EventDispatcher.throwEvent(this.event);
		
		// Create MOTD JSON Object
		JSONObject versionMap = new JSONObject();
		versionMap.put("name", Diamond.desktopVersionTag);
		versionMap.put("protocol", Diamond.desktopProtocol-10);
								
		// Players
		ArrayList<String> playerList = Diamond.getOnlinePlayers();
		JSONObject playerMap = new JSONObject();
		playerMap.put("max", ServerSettings.getMaxPlayers());
		playerMap.put("online", 1);
		
		if(playerList.size() <= 10) {
			JSONArray onlinePlayerMap = new JSONArray();
			for(int i = 0; i < playerList.size(); i++) {
				JSONObject currentPlayer = new JSONObject();
				currentPlayer.put("name", playerList.get(i));
				currentPlayer.put("id", UUID.randomUUID().toString());
				onlinePlayerMap.add(currentPlayer);
			}
			playerMap.put("sample", onlinePlayerMap);
		}
										
		// Description and favicon
		JSONObject info = new JSONObject();
		info.put("text", event.getMOTD());
			
		// Put icon if available
		if(event.getFavicon() != null) object.put("favicon", event.getFavicon());
					
		// Now put everything all toghether
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
		String json = object.toJSONString();
		
		// Write Data
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		data.write(DesktopPacketIDList.HANDSHAKE_PACKET);
		data.write(VarInt.writeUnsignedVarInt(json.getBytes().length));
		data.write(json.getBytes());
		data.flush();
		
		// Send packet
		output.write(VarInt.writeUnsignedVarInt(data.size()));
		output.write(data.toByteArray());
		output.flush();
	}
	
	
	
}
