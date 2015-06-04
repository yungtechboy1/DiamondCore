/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.pocket.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.diamondcore.diamond.Diamond;
import org.diamondcore.pocket.PocketPacketIDList;
import org.diamondcore.utils.ServerSettings;

/**
 * This packet is in response to a ping sent by a MCPE client
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class ServerListPingResponse implements BasePocketPacket {
	
	// Server Side
	protected DatagramSocket socket;
	protected DatagramPacket packet;
	protected long serverID;
	
	// Client Side
	protected long pingID;
	protected byte[] magic;
	
	// Event info
	//ServerListPingEvent event;
	
	public ServerListPingResponse(DatagramSocket socket, DatagramPacket packet, long serverID) throws IOException {
		this.socket = socket;
		this.packet = packet;
		this.serverID = serverID;
		
		//event = new ServerListPingEvent(PlayerType.POCKET, packet.getAddress(), packet.getPort(), ServerSettings.getPEMOTD());
		//EventDispatcher.throwEvent(this.event);
		this.sendResponse();
	}

	@Override
	public int getPacketID() {
		return packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(packet.getData()));
		pingID = reader.readLong();
		magic = reader.read(PocketPacketIDList.MAGIC.length);
		reader.close();
	}
	
	@Override
	public void sendResponse() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		String identifier = "MCPE;" + ServerSettings.getPEMOTD() + ";" + Diamond.pocketProtocol + ";" + Diamond.pocketVersionTag + ";" + 0 + ";" + ServerSettings.getMaxPlayers();
		writer.writeByte(PocketPacketIDList.ID_UNCONNECTED_PING_OPEN_CONNECTIONS);
		writer.writeLong(pingID);
		writer.writeLong(serverID);
		writer.write(PocketPacketIDList.MAGIC);
		writer.writeShort((short) identifier.getBytes().length);
		writer.write(identifier.getBytes());
		socket.send(new DatagramPacket(output.toByteArray(), output.toByteArray().length, packet.getAddress(), packet.getPort()));
		writer.close();
		output.close();
	}
	
}
