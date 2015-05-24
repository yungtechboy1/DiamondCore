package net.trenterprises.diamondcore.desktop.network.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.desktop.DesktopServerListPingEvent;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
import net.trenterprises.diamondcore.desktop.network.DesktopPacketIDList;

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
	DesktopServerListPingEvent DSLPE;
	
	@SuppressWarnings("unchecked")
	public ServerListPingResponse(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Throw event before putting together json because something might be changed by a plugin
		DSLPE = new DesktopServerListPingEvent(this, s.getInetAddress(), s.getPort(), ServerSettings.getPCMOTD(), ServerSettings.getServerFavicon());
		PluginManager.throwEvent(DSLPE);
		
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
		info.put("text", DSLPE.getMOTD());
			
		// Put icon if available
		if(DSLPE.getFavicon() != null) object.put("favicon", DSLPE.getFavicon());
					
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
		output.write(VarInt.writeUnsignedVarInt(3 + json.getBytes().length));
		output.writeByte(DesktopPacketIDList.HANDSHAKE_PACKET);
		output.write(VarInt.writeUnsignedVarInt(json.getBytes().length));
		output.write(json.getBytes());
		output.flush();
	}
	
	
	
}
