package net.trenterprises.diamondcore.desktop.network.handshake;

import java.io.IOException;

public abstract class HandshakePacket {

	public abstract void decode() throws IOException;
	public abstract void sendResponse() throws IOException;

}
