/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event.player;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.api.java.event.Event;
import net.trenterprises.diamondcore.cross.api.java.event.TriggerCause;
import net.trenterprises.diamondcore.desktop.network.packet.ClientDisconnectPacket;

/**
 * This event is thrown whenever a player on MCPE tries to join the server
 * and has not yet spawned in the world.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class PlayerLoginEvent extends Event {
	
	protected final TriggerCause cause;
	private final Socket desktop;
	@SuppressWarnings("unused")
	private final DatagramSocket pocket;
	private final InetAddress address;
	private final int port;
	
	private boolean isCancelled = false;
	
	public PlayerLoginEvent(TriggerCause cause, Socket desktop, InetAddress address, int port) {
		this.cause = cause;
		this.desktop = desktop;
		this.pocket = null;
		this.address = address;
		this.port = port;
	}
	
	public PlayerLoginEvent(TriggerCause cause, DatagramSocket pocket, InetAddress address, int port) {
		this.cause = cause;
		this.desktop = null;
		this.pocket = pocket;
		this.address = address;
		this.port = port;
	}
	
	public String getName() {
		return "PlayerLoginEvent";
	}
	
	/**
	 * Used to cancel the login of the player
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void cancelLogin(String reason) {
		if(cause == TriggerCause.POCKET) {
			this.getLogger().warn("Note, this proccess can not be done as this code has not been finished yet");
		} else if(cause == TriggerCause.DESKTOP) {
			try {
				new ClientDisconnectPacket(desktop, reason);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		isCancelled = true;
	}
	
	/**
	 * Used to see if the login was cancelled
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Login status
	 */
	public boolean getLoginCancelled() {
		return isCancelled;
	}
	
	/**
	 * Used to get the player's InetAddress
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Player's InetAddress
	 */
	public InetAddress getAddress() {
		return address;
	}
	
	/**
	 * Used to get the player's Port
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Player's port
	 */
	public final int getPort() {
		return port;
	}
	
}
