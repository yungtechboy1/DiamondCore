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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.diamondcore.desktop.DesktopPacketIDList;
import org.diamondcore.desktop.handshake.HandshakeType;
import org.diamondcore.desktop.handshake.ServerListPingResponse;
import org.diamondcore.desktop.packet.HandshakePacket;
import org.diamondcore.desktop.utils.PacketUtils;
import org.diamondcore.utils.VarInt;

/**
 * This is the first packet sent by the server in response
 * to the client, if it is a long packet, a new Login packet
 * will be thrown, and can be retrieved by using <INSERTNAMEHERE>
 * If it is null, that means it is a ping by minecraft.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class HandshakeResponse {
	
	// Socket info
	protected final Socket socket;
	protected final DataInputStream input;
	protected final DataOutputStream output;
	
	// Packet info
	protected int receivedPacketLength;
	protected int protocol;
	protected String destinationAddress;
	protected int destinationPort;
	protected int nextState;
	protected HandshakePacket packet;
	
	public HandshakeResponse(Socket socket) throws IOException {
		// Initialize
		this.socket = socket;
		this.input = new DataInputStream(this.socket.getInputStream());
		this.output = new DataOutputStream(this.socket.getOutputStream());
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
		return DesktopPacketIDList.HANDSHAKE_PACKET;
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
		if(this.nextState == 1) return HandshakeType.SERVER_PING;
		else if(this.nextState == 2) return HandshakeType.PLAYER_LOGIN;
		else return null; // TODO: Add exception
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
		this.protocol = VarInt.readUnsignedVarInt(input.readByte());
		this.destinationAddress = PacketUtils.readString(input);
		this.destinationPort = input.readUnsignedShort();
		this.nextState = VarInt.readUnsignedVarInt(input.readByte());
	}

	public void sendResponse() throws IOException {
		if(this.nextState == 1) this.packet = new ServerListPingResponse(this.socket);
		//else if(this.nextState == 2) this.packet = new LoginResponse(this.socket);
	} 

}
