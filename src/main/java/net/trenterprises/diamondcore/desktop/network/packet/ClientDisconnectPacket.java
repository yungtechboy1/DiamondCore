package net.trenterprises.diamondcore.desktop.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.utils.VarInt;

import org.json.simple.JSONObject;

public class ClientDisconnectPacket {
	
	// Socket info
	Socket s;
	DataInputStream input;
	DataOutputStream output;
	
	// Packet info
	String reason;
	
	public ClientDisconnectPacket(Socket s, String reason) throws IOException {
		this.s = s;
		this.input = new DataInputStream(s.getInputStream());
		this.output = new DataOutputStream(s.getOutputStream());
		this.reason = reason;
	}
	
	@SuppressWarnings("unchecked")
	public void sendResponse() throws IOException {
		JSONObject object = new JSONObject();
		object.put("text", this.reason.toString());
		
		output.write(VarInt.writeUnsignedVarInt(2 + object.toString().getBytes().length));
		output.write((byte) 0x00);
		output.write(VarInt.writeUnsignedVarInt(object.toString().getBytes().length));
		output.write(object.toString().getBytes());
		output.flush();
	}
	
}
