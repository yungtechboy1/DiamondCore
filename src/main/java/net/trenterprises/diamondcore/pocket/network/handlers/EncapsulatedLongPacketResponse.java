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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

/**
 * This packet is in response to a encapsulated packet
 * NOTE: DEPRECATED. DO NOT USE!
 * 
 * @author Trent Summerlin
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
@Deprecated
public class EncapsulatedLongPacketResponse implements BasePocketPacket {
	
	// Packet Side
	public static byte ELPR1 = (byte) 0x09;
	public static byte ELPR2 = (byte) 0x13;
	private byte ELPRUsing;
	
	private byte Cookie = (byte) 0x00 * 4;
	@SuppressWarnings("unused")
	private byte SecurityFlag = (byte) 0xCD;
	
	// Server Side
	protected DatagramSocket Socket;
	protected DatagramPacket Packet;
	
	// Client Side
	protected short PacketLength;
	protected long ClientID;
	protected long SessionID;
	
	public EncapsulatedLongPacketResponse(net.trenterprises.diamondcore.pocket.network.PocketPacketHandler PocketPacketHandlerClass, DatagramSocket Socket, DatagramPacket Packet, byte ELPR) {
		this.Socket = Socket;
		this.Packet = Packet;
		ELPRUsing = ELPR;
	}


	@Override
	public int getPacketID() {
		return Packet.getData()[0];
	}
	
	public int getEncapsulatedPacketID() {
		return ELPRUsing;
	}


	@Override
	public void decode() {
		ByteBuffer BB = ByteBuffer.wrap(Packet.getData());
		if(ELPRUsing == ELPR1) {
			this.PacketLength = BB.getShort();
			this.ClientID = BB.getLong();
			this.SessionID = BB.getLong();
		}
	}


	@Override
	public void sendResponse() throws IOException {
		if(ELPRUsing == ELPR1) {
			ByteBuffer BB = ByteBuffer.allocate(96);
			BB.put(this.Cookie);
			
			@SuppressWarnings("unused")
			ByteBuffer BBF = ByteBuffer.allocate(0);
		}
	}
	
}
