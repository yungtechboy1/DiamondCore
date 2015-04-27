/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event.pocket;

import java.net.InetAddress;

/**
 * This event is thrown whenever a player on MCPE tries to join the server
 * and has not yet spawned in the world.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class PocketPlayerLoginEvent extends PocketEvent {
	
	private InetAddress Address;
	private int Port;
	private long ClientID;
	private short MTU;
	
	public PocketPlayerLoginEvent(net.trenterprises.diamondcore.pocket.network.handlers.JoinRequestStage2Response JoinRequestStage2ResponseClass, InetAddress Address, int Port, long ClientID, short MTU) {
		this.Address = Address;
		this.Port = Port;
		this.ClientID = ClientID;
		this.MTU = MTU;
	}
	
	/**
	 * Used to get the event name
	 */
	public String getName() {
		return "PocketPlayerLoginEvent";
	}
	
	/**
	 * Used to get the player's InetAddress
	 * 
	 * @return Player's InetAddress
	 * @author Trent Summerlin
	 */
	public InetAddress getAddress() {
		return Address;
	}
	
	/**
	 * Used to get the player's Port
	 * 
	 * @return Player's port
	 * @author Trent Summerlin
	 */
	public final int getPort() {
		return Port;
	}
	
	/**
	 * Used to get the the smallest MTU found on the device
	 * 
	 * @return Lowest Device MTU
	 * @author Trent Summerlin
	 */
	public final short getMTU() {
		return MTU;
	}
	
	/**
	 * Used to retrieve the player's client ID
	 * 
	 * @return Player's ClientID
	 * @author Trent Summerlin
	 */
	public final long getClientID() {
		return ClientID;
	}
}
