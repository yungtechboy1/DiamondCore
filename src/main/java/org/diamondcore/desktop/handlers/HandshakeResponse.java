/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package org.diamondcore.desktop.handlers;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.diamondcore.desktop.DesktopPacket;
import org.diamondcore.desktop.PacketIDList;
import org.diamondcore.desktop.handshake.HandshakeType;
import org.diamondcore.desktop.handshake.LoginResponse;
import org.diamondcore.desktop.handshake.ServerListPingResponse;
import org.diamondcore.desktop.packet.HandshakePacket;
import org.diamondcore.exception.DiamondException;

/**
 * This is the first packet sent by the server in response
 * <br>
 * to the client, if it is a long packet, a new Login packet
 * <br>
 * will be thrown, and can be retrieved by using <INSERTNAMEHERE>
 * <br>
 * If it is null, that means it is a ping by minecraft.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class HandshakeResponse {
	
	// Socket info
	private final Socket socket;
	private final DesktopPacket input;
	
	// Packet info
	private int receivedPacketLength;
	private int protocol;
	private String destinationAddress;
	private int destinationPort;
	private int nextState;
	private HandshakePacket packet;
	
	public HandshakeResponse(Socket socket) throws IOException, DiamondException {
		// Initialize
		this.socket = socket;
		this.input = new DesktopPacket(new DataInputStream(this.socket.getInputStream()));
		this.receivedPacketLength = socket.getInputStream().available()+2; // Add two because two bytes have already been read by the DesktopPacketHandler
		
		// Decode
		this.decode();
		
		// Send response
		this.sendResponse();
	}
	
	public int getReceivedPacketLength() {
		return this.receivedPacketLength;
	}
	
	public int getPacketID() {
		return PacketIDList.HANDSHAKE_PACKET;
	}
	
	public int getProtocol() {
		return this.protocol;
	}
	
	/**
	 * Used to retrieve the destination address sent by the client
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Client's destination address
	 */
	public String getDestinationAddress() {
		return this.destinationAddress;
	}
	
	/**
	 * Used to retrieve the destination port sent by the client
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Client's destination port
	 */
	public int getDestinationPort() {
		return this.destinationPort;
	}
	
	/**
	 * Used to get the handshake type sent by the client
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Packet handshake type
	 */
	public HandshakeType getHandshakeType() {
		if(this.nextState == 1)
			return HandshakeType.SERVER_PING;
		else if(this.nextState == 2)
			return HandshakeType.PLAYER_LOGIN;
		return null;
	}
	
	/**
	 * Used to get the packet response created by
	 * <br>
	 * the Handshake handler
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Created handshake
	 */
	public HandshakePacket getHandshakePacket() {
		return this.packet;
	}
	
	public void decode() throws IOException {
		this.protocol = input.readVarInt();
		this.destinationAddress = input.readString();
		this.destinationPort = input.readUShort();
		this.nextState = input.readVarInt();
	}

	public void sendResponse() throws IOException, DiamondException {
		if(this.nextState == 1)
			this.packet = new ServerListPingResponse(this.socket);
		else if(this.nextState == 2)
			this.packet = new LoginResponse(this.socket);
	} 

}