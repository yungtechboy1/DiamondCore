/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.desktop.handshake;

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
