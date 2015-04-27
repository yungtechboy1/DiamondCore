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
 * This is the base of every MCPE packet hander in DiamondCore
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract interface BasePocketPacket {
	
	/**
	 * Used to get the packet ID sent by the MCPE client
	 * 
	 * @author Trent Summerlin
	 * @return Packet ID sent by client
	 */
	public int getPacketID();
	
	/**
	 * Used to get all of the information in the packet in order to
	 * <br>
	 * respond correctly to the enviroment
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IOException
	 */
	public void decode() throws IOException;
	
	/**
	 * Used to send the response to the packet received
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IOException
	 */
	public void sendResponse() throws IOException;
	
}
