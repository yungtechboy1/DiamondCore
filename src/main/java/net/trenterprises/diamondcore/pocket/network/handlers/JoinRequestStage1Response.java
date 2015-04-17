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

import net.trenterprises.diamondcore.pocket.network.PocketPacketIDList;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

/**
 * This packet is in response to the first login packet sent by a client
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class JoinRequestStage1Response implements BasePocketPacket {
	
	// Server Side
	protected DatagramSocket Socket;
	protected DatagramPacket Packet;
	protected long ServerID;
	
	// Client Side
	protected byte[] Magic;
	protected byte ProtocolVersion;
	protected byte MTU;
	
	public JoinRequestStage1Response(DatagramSocket Socket, DatagramPacket Packet, long ServerID) throws IOException {
		this.Socket = Socket;
		this.Packet = Packet;
		this.ServerID = ServerID;
		this.sendResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(Packet.getData()));
		reader.readByte(); // Get packet ID
		Magic = reader.read(PocketPacketIDList.MAGIC.length);
		ProtocolVersion = reader.readByte();
		MTU = reader.readByte();
		reader.close();
	}

	@Override
	public void sendResponse() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		writer.writeByte(PocketPacketIDList.ID_OPEN_CONNECTION_REPLY_1);
		writer.write(PocketPacketIDList.MAGIC);
		writer.writeLong(ServerID);
		writer.writeByte((byte) 0);
		writer.writeShort((short) MTU);
		Socket.send(new DatagramPacket(output.toByteArray(), output.toByteArray().length, Packet.getAddress(), Packet.getPort()));
		writer.close();
		output.close();
	}
	
}
