package net.trenterprises.diamondcore.desktop.network.handshake;

public enum HandshakeType {
	SERVER_PING(1), PLAYER_LOGIN(2);
	
	private int type;
	
	private HandshakeType(int type) {
		this.type = type;
	}
	
	public int toInt() {
		return this.type;
	}
};
