/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.pocket.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.diamondcore.pocket.PacketIDList;

/**
 * This packet is in response to the first login packet sent by a client
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class JoinRequestResponse implements BasePocketPacket {
	
	// Server Side
	protected DatagramSocket socket;
	protected DatagramPacket packet;
	protected long serverID;
	
	// Client Side
	protected byte[] magic;
	protected byte securityCookie;
	protected short serverUDPPort;
	protected byte protocol;
	protected short MTU;
	protected long clientID;
	protected short clientPort = -1;
	
	// Other
	protected final int stage;
	
	public JoinRequestResponse(DatagramSocket Socket, DatagramPacket Packet, long ServerID, int stage) throws IOException {
		this.socket = Socket;
		this.packet = Packet;
		this.serverID = ServerID;
		this.stage = stage;
		if(this.stage == 2) {
			this.clientPort = (short) packet.getPort();
			// TODO: Throw event
		}
		
		this.sendResponse();
	}
	

	@Override
	public int getPacketID() {
		return packet.getData()[0];
	}

	@Override
	public void decode() throws IOException {
		BinaryReader reader = new BinaryReader(new ByteArrayInputStream(packet.getData()));
		if(this.stage == 1) {
			reader.readByte(); // Get packet ID
			magic = reader.read(PacketIDList.MAGIC.length);
			protocol = reader.readByte();
			MTU = reader.readByte();
			reader.close();
		} else if(this.stage == 2) {
			magic = reader.read(PacketIDList.MAGIC.length);
			securityCookie = reader.readByte();
			serverUDPPort = reader.readShort();
			MTU = reader.readShort();
			clientID = reader.readLong();
			reader.close();
		}
	}

	@Override
	public void sendResponse() throws IOException {
		byte[] data = getStage();
		socket.send(new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort()));
	}
	
	protected byte[] getStage() throws IOException {
		if(this.stage == 1) {
			// Write data
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			BinaryWriter writer = new BinaryWriter(output);
			writer.writeByte(PacketIDList.ID_OPEN_CONNECTION_REPLY_1);
			writer.write(PacketIDList.MAGIC);
			writer.writeLong(serverID);
			writer.writeByte((byte) 0);
			writer.writeShort((short) MTU);
			writer.close();
			
			return output.toByteArray();
		} else if(this.stage == 2) {
			// Write data
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			BinaryWriter writer = new BinaryWriter(output);
			writer.writeByte(PacketIDList.ID_OPEN_CONNECTION_REPLY_2);
			writer.write(PacketIDList.MAGIC);
			writer.writeLong(serverID);
			writer.writeShort(clientPort);
			writer.writeShort(MTU);
			writer.writeByte((byte) 0);
			writer.close();
			
			return output.toByteArray();
		} else {
			return null;
		}
	}
	
}
