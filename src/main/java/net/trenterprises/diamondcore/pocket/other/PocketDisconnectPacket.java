/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.pocket.other;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.trenterprises.diamondcore.pocket.PocketPacketIDList;
import net.trenterprises.diamondcore.pocket.utils.PocketPacketUtils;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

public class PocketDisconnectPacket implements BaseServerPocketPacket {
	
	private String reason;
	
	public PocketDisconnectPacket(String reason) {
		this.reason = reason;
	}
	
	@Override
	public int getPacketID() {
		return PocketPacketIDList.DISCONNECT_PACKET;
	}

	@Override
	public BinaryReader encode() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(out);
		writer.writeByte((byte) 0x84);
		writer.writeShort((short) reason.length());
		writer.write(reason.getBytes());
		return PocketPacketUtils.toReader(PocketPacketUtils.putEncap(PocketPacketUtils.toReader(writer)));
	}

}
