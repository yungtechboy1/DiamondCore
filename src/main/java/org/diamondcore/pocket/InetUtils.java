package org.diamondcore.pocket;

import java.io.IOException;

import org.blockserver.io.BinaryWriter;

public class InetUtils {
	
	public static void putAddress(String address, short port, BinaryWriter writer) throws IOException {
		// Convert string to byte array
		String[] rawAddr = address.split("\\.");
		byte[] addr = new byte[4];
		for(int i = 0; i < 4; i++)
			addr[i] = (byte) (Integer.parseInt(rawAddr[i]) & 0xFF);
		
		// Write address
		for(int i = 0; i < 4; i++)
			writer.writeByte((byte) (addr[i] & 0xFF));
		writer.writeShort(port);
	}
	
}
