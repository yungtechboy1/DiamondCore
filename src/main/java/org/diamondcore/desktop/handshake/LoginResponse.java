package org.diamondcore.desktop.handshake;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import org.diamondcore.desktop.DesktopPacket;
import org.diamondcore.desktop.packet.HandshakePacket;
import org.diamondcore.entity.player.DesktopPlayer;

public class LoginResponse extends HandshakePacket {
	
	// Socket info
	private final Socket s;
	private final DesktopPacket input;
	//private final DataOutputStream output;
	
	// Packet data
	private String name;
	
	public LoginResponse(Socket s) throws IOException {
		this.s = s;
		this.input = new DesktopPacket(new DataInputStream(this.s.getInputStream()));
		//this.output = new DataOutputStream(this.s.getOutputStream());
		
		this.decode();
		this.sendResponse();
	}

	@Override
	public void decode() throws IOException {
		input.read();
		this.name = input.readString();
	}

	@Override
	public void sendResponse() throws IOException {
		/*
			DesktopPacket packet = new DesktopPacket((byte) 0x01);
			packet.writeString("");
			packet.writeByteArray(SecurityUtils.generateX509Key(Diamond.getServer().getKeyPair().getPublic()).getEncoded());
			packet.writeByteArray(SecurityUtils.generateVerifyToken());
		*/
		
		// For now, just disconnect the player
		DesktopPlayer p = new DesktopPlayer(s, this.name, UUID.randomUUID());
		p.disconnect("Sorry, but login for players on the desktop edition has not yet been implemented!");
	}
	
}
