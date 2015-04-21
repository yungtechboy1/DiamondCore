package net.trenterprises.diamondcore.desktop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.borrowed.VarInt;

public class TestPing {
	
}

class TestThread extends Thread {
	
	String json = null;
	ServerSocket ss = null;
	public TestThread(ServerSocket ss, String json) {
		this.ss = ss;
		this.json = json;
	}
	
	public void run() {
		while(true) {
			try {
				Socket s = ss.accept();
				DataInputStream input = new DataInputStream(s.getInputStream());
				
				System.out.println(input.available());
				System.out.println("Packet length?: " + input.readByte());
				System.out.println("PacketID: " + VarInt.readUnsignedVarInt(new byte[] { input.readByte() }));
				System.out.println("Protocol: " + VarInt.readUnsignedVarInt(new byte[] { input.readByte() }));
				
				int length = VarInt.readUnsignedVarInt(new byte[] { input.readByte() });
				byte[] rawString = new byte[length];
				for(int i = 0; i < length; i++) rawString[i] = input.readByte();
				String address = new String(rawString);
				System.out.println("Server address: " + address);
				
				System.out.println("Server port: " + input.readUnsignedShort());
				System.out.println("Next state: " + VarInt.readUnsignedVarInt(new byte[] { input.readByte() }));
				
				DataOutputStream output = new DataOutputStream(s.getOutputStream());
				output.write(VarInt.writeUnsignedVarInt(3 + json.getBytes().length));
				output.writeByte((byte) 0x00);
				output.write(VarInt.writeUnsignedVarInt(json.getBytes().length));
				output.write(json.getBytes());
				
				//writer.close();
				s.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
