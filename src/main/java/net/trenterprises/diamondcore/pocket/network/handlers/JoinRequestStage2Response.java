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

import net.trenterprises.diamondcore.cross.api.java.event.TriggerCause;
import net.trenterprises.diamondcore.cross.api.java.event.player.PlayerLoginEvent;
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
	protected DatagramSocket socket;
	protected DatagramPacket packet;
	protected long serverID;
		
	// Client Side
	protected byte[] magic;
	protected byte securityCookie;
	protected short serverUDPPort;
	protected short MTU;
	protected long clientID;
	protected short clientPort;
	
	// Event info
	PlayerLoginEvent event;
	
	public JoinRequestStage2Response(DatagramSocket socket, DatagramPacket packet, long serverID) throws IOException {
		this.socket = socket;
		this.packet = packet;
		this.serverID = serverID;
		this.clientPort = (short) packet.getPort();
		this.throwEvent();
		this.sendResponse();
	}

	@Override
	public int getPacketID() {
		return packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(packet.getData()));
		magic = reader.read(PocketPacketIDList.MAGIC.length);
		securityCookie = reader.readByte();
		serverUDPPort = reader.readShort();
		MTU = reader.readShort();
		clientID = reader.readLong();
		reader.close();
	}

	@Override
	public void sendResponse() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		writer.writeByte(PocketPacketIDList.ID_OPEN_CONNECTION_REPLY_2);
		writer.write(PocketPacketIDList.MAGIC);
		writer.writeLong(serverID);
		writer.writeShort(clientPort);
		writer.writeShort(MTU);
		writer.writeByte((byte) 0);
		socket.send(new DatagramPacket(output.toByteArray(), output.toByteArray().length, packet.getAddress(), packet.getPort()));
		writer.close();
		output.close();
	}
	
	public void throwEvent() {
		this.event = new PlayerLoginEvent(TriggerCause.POCKET, this.socket, packet.getAddress(), packet.getPort());
		PluginManager.throwEvent(this.event);
	}
	
	
}
