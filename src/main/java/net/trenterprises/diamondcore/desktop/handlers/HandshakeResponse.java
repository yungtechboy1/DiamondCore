/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package net.trenterprises.diamondcore.desktop.handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.utils.VarInt;
import net.trenterprises.diamondcore.desktop.DesktopPacketIDList;
import net.trenterprises.diamondcore.desktop.handshake.HandshakePacket;
import net.trenterprises.diamondcore.desktop.handshake.HandshakeType;
import net.trenterprises.diamondcore.desktop.handshake.LoginResponse;
import net.trenterprises.diamondcore.desktop.handshake.ServerListPingResponse;
import net.trenterprises.diamondcore.desktop.utils.PacketUtils;

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
public final class HandshakeResponse implements BaseDesktopPacket {
	
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
	
	@Override
	public int getReceivedPacketLength() {
		return this.receivedPacketLength;
	}
	
	@Override
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
		else return null;
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
	
	@Override
	public void decode() throws IOException {
		this.protocol = VarInt.readUnsignedVarInt(input.readByte());
		this.destinationAddress = PacketUtils.readString(input);
		this.destinationPort = input.readUnsignedShort();
		this.nextState = VarInt.readUnsignedVarInt(input.readByte());
	}

	@Override
	public void sendResponse() throws IOException {
		if(this.nextState == 1) this.packet = new ServerListPingResponse(this.socket);
		else if(this.nextState == 2) this.packet = new LoginResponse(this.socket);
	} 

}
