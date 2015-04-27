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

import net.trenterprises.diamondcore.cross.api.java.event.pocket.PocketPlayerLoginEvent;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.pocket.network.PocketPacketIDList;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

/**
 * This packet is in response to the second login packet sent by a MCPE client
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class JoinRequestStage2Response implements BasePocketPacket {
	
	// Server Side
	protected DatagramSocket Socket;
	protected DatagramPacket Packet;
	protected long ServerID;
		
	// Client Side
	protected byte[] Magic;
	protected byte SecurityCookie;
	protected short ServerUDPPort;
	protected short MTU;
	protected long ClientID;
	protected short ClientPort;
	
	// Event side
	PocketPlayerLoginEvent PPLE;
	
	public JoinRequestStage2Response(DatagramSocket Socket, DatagramPacket Packet, long ServerID) throws IOException {
		this.Socket = Socket;
		this.Packet = Packet;
		this.ServerID = ServerID;
		this.ClientPort = (short) Packet.getPort();
		this.throwEvent();
		this.sendResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(Packet.getData()));
		Magic = reader.read(PocketPacketIDList.MAGIC.length);
		SecurityCookie = reader.readByte();
		ServerUDPPort = reader.readShort();
		MTU = reader.readShort();
		ClientID = reader.readLong();
		reader.close();
	}

	@Override
	public void sendResponse() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		writer.writeByte(PocketPacketIDList.ID_OPEN_CONNECTION_REPLY_2);
		writer.write(PocketPacketIDList.MAGIC);
		writer.writeLong(ServerID);
		writer.writeShort(ClientPort);
		writer.writeShort(MTU);
		writer.writeByte((byte) 0);
		Socket.send(new DatagramPacket(output.toByteArray(), output.toByteArray().length, Packet.getAddress(), Packet.getPort()));
		writer.close();
		output.close();
	}
	
	public void throwEvent() {
		PPLE = new PocketPlayerLoginEvent(this, Packet.getAddress(), Packet.getPort(), ClientID, MTU);
		try {
			PluginManager.throwEvent(PPLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
