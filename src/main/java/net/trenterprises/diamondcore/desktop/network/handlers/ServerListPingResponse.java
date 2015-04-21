package net.trenterprises.diamondcore.desktop.network.handlers;

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
import net.trenterprises.diamondcore.desktop.network.utils.ImageUtils;
import net.trenterprises.diamondcore.desktop.network.utils.PacketUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class ServerListPingResponse implements BaseDesktopPacket {
	
	// Socket info
	protected final Socket socket;
	protected final DataInputStream input;
	protected final DataOutputStream output;
	
	// Packet info
	protected int receivedPacketLength;
	protected int packetID;
	protected int protocol;
	protected String destinationAddress;
	protected int destinationPort;
	protected int nextState;
	
	// Other
	protected static JSONObject object = new JSONObject();
	
	@SuppressWarnings("unchecked")
	public ServerListPingResponse(Socket socket, int receivedPacketLength, byte packetID) throws IOException {
		// Initialize
		this.socket = socket;
		this.input = new DataInputStream(this.socket.getInputStream());
		this.output = new DataOutputStream(this.socket.getOutputStream());
		this.receivedPacketLength = receivedPacketLength;
		this.packetID = packetID;
		
		// Decode
		this.decode();
		
		// Version object
		JSONObject versionMap = new JSONObject();
		versionMap.put("name", Diamond.versionTag);
		versionMap.put("protocol", Diamond.DesktopProtocol);
					
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
		if(ServerSettings.getServerFavicon() != null) object.put("favicon", ("data:image/png;base64," + ImageUtils.encodeToString(ServerSettings.getServerFavicon())));
		
		// Now put everything all toghether
		object.put("version", versionMap);
		object.put("players", playerMap);
		object.put("description", info);
		
		// Send response
		this.sendResponse();
	}
	
	@Override
	public int getReceivedPacketLength() {
		return this.receivedPacketLength;
	}
	
	@Override
	public int getPacketID() {
		return this.packetID;
	}
	
	public int getProtocol() {
		return this.protocol;
	}
	
	public String getDestinationAddress() {
		return this.destinationAddress;
	}
	
	public int getDestinationPort() {
		return this.destinationPort;
	}
	
	public int getNextState() {
		return this.nextState;
	}
	
	@Override
	public void decode() throws IOException {
		//this.receivedPacketLength = VarInt.readUnsignedVarInt(input.readByte());
		//this.packetID = VarInt.readUnsignedVarInt(input.readByte());
		this.protocol = VarInt.readUnsignedVarInt(input.readByte());
		this.destinationAddress = PacketUtils.readString(input);
		this.destinationPort = input.readUnsignedShort();
		this.nextState = VarInt.readUnsignedVarInt(input.readByte());
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
