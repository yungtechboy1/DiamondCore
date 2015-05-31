package net.trenterprises.diamondcore.desktop;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.utils.VarInt;

public class DesktopSocketListener extends Thread {
	
	public void run() {
		while(true) {
			for(DesktopSession session : DesktopSession.desktopClients) {
				try {
					Socket s = session.getPlayerSocket();
					DataInputStream input = new DataInputStream(s.getInputStream());
					// Wait until their is at least 2 bytes of data (Packet Length and Packet ID)
					if(input.available() > 2) {
						VarInt.readUnsignedVarInt(input, true); // Read useless length
						int packetID = VarInt.readUnsignedVarInt(input.readByte());
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
