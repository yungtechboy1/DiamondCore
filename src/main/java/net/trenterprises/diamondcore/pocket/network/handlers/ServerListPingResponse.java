/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.pocket.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.pocket.PocketServerListPingEvent;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.pocket.network.PocketPacketIDList;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

/**
 * This packet is in response to a ping sent by a MCPE client
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class ServerListPingResponse implements BasePocketPacket {
	
	// Server Side
	protected DatagramSocket Socket;
	protected DatagramPacket Packet;
	protected long ServerID;
	
	// Client Side
	protected long PingID;
	protected byte[] Magic;
	
	// Event side
	PocketServerListPingEvent PSLPE;
	
	public ServerListPingResponse(DatagramSocket Socket, DatagramPacket Packet, long ServerID) throws IOException {
		this.Socket = Socket;
		this.Packet = Packet;
		this.ServerID = ServerID;
		PSLPE = new PocketServerListPingEvent(this, Packet.getAddress(), Packet.getPort(), ServerSettings.getPEMOTD());
		PluginManager.throwEvent(PSLPE);
		if(PSLPE.shouldBroadcast()) this.sendResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(Packet.getData()));
		PingID = reader.readLong();
		Magic = reader.read(PocketPacketIDList.MAGIC.length);
		reader.close();
	}
	
	@Override
	public void sendResponse() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		String identifier = "MCPE;" + PSLPE.getMOTD() + ";" + Diamond.pocketProtocol + ";" + Diamond.pocketVersionTag + ";01";
		writer.writeByte(PocketPacketIDList.ID_UNCONNECTED_PING_OPEN_CONNECTIONS);
		writer.writeLong(PingID);
		writer.writeLong(ServerID);
		writer.write(PocketPacketIDList.MAGIC);
		writer.writeShort((short) identifier.getBytes().length);
		writer.write(identifier.getBytes());
		Socket.send(new DatagramPacket(output.toByteArray(), output.toByteArray().length, Packet.getAddress(), Packet.getPort()));
		writer.close();
		output.close();
	}
	
}
