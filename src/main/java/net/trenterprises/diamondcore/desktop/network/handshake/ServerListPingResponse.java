package net.trenterprises.diamondcore.desktop.network.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;
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
	
	@SuppressWarnings("unchecked")
	public ServerListPingResponse(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Create MOTD JSON Object
		JSONObject versionMap = new JSONObject();
		versionMap.put("name", Diamond.versionTag);
		versionMap.put("protocol", Diamond.DesktopProtocol-10);
								
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
		info.put("text", ServerSettings.getPCMOTD());
			
		// Put icon if available
		if(ServerSettings.getServerFavicon() != null) object.put("favicon", ServerSettings.getServerFavicon());
					
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
		output.writeByte((byte) DesktopPacketIDList.HANDSHAKE_PACKET);
		output.write(VarInt.writeUnsignedVarInt(json.getBytes().length));
		output.write(json.getBytes());
		output.flush();
	}
	
	
	
}
