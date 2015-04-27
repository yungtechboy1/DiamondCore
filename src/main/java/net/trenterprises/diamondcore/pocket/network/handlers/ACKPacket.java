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

/**
 * This packet is used to ACK the present of
 * a received packet from a MCPE client.
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public class ACKPacket implements BasePocketPacket {

	@Override
	public int getPacketID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void decode() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendResponse() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
