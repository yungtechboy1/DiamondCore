package net.trenterprises.diamondcore.pocket.network.other;

import java.io.IOException;

import org.blockserver.io.BinaryReader;

public interface BaseServerPocketPacket {
	
	public int getPacketID();
	
	public BinaryReader encode() throws IOException;
	
}
