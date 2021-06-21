/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.entity.player;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import org.diamondcore.Diamond;
import org.diamondcore.Inventory;
import org.diamondcore.command.CommandSender;
import org.diamondcore.entity.Human;

/**
 * Used to represent a player and manage it easily
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class Player extends Human implements CommandSender {
	
	// Player list
	protected static ArrayList<Player> playerList = new ArrayList<Player>();
	protected static ArrayList<UUID> uuidList = new ArrayList<UUID>();
	public static Player[] getPlayers() {
		Player[] list = new Player[playerList.size()];
		for(int i = 0; i < list.length; i++)
			list[i] = playerList.get(i);
		return list;
	}
	
	// Player info
	private String username;
	private Inventory inventory;
	private final UUID uuid;
	
	public Player(String username, UUID uuid) {
		// Set player info
		this.username = username;
		this.uuid = uuid;
		
		// Make sure player is not duplicate
		if(uuidList.contains(uuid)) {
			// Throw new exception
			return;
		}
		
		// Add player to list
		playerList.add(this);
		uuidList.add(uuid);
	}
	
	/**
	 * Used to get the player's name
	 * 
	 * @return Player name
	 * @author Trent Summerlin
	 */
	public final String getName() {
		return this.username;
	}
	
	/**
	 * Used to get the player's inventory
	 * 
	 * @return Player inventory
	 * @author Trent Summerlin
	 */
	public final Inventory getInventory() {
		return this.inventory;
	}
	
	/**
	 * Used to get the player's UUID
	 * 
	 * @return Player UUID
	 * @author Trent Summerlin
	 */
	public final UUID getUUID() {
		return this.uuid;
	}
	
	/**
	 * Used to send a message to the player
	 * 
	 * @param msg
	 * @author Trent Summerlin
	 */
	public void sendMessage(String msg) {
		Diamond.logger.err("A class has not overriden the sendMessage() method!");
	};
	
	/**
	 * Used to disconnect the player
	 * 
	 * @param reason
	 * @author Trent Summerlin
	 */
	public void disconnect(String reason) throws IOException {
		Diamond.logger.err("A class has not overriden the disconnect() method!");
	}
	
	/**
	 * Used to disconnect the player
	 * 
	 * @author Trent Summerlin
	 */
	public void disconnect() throws IOException {
		Diamond.logger.err("A class has not overriden the disconnect() method!");
	}
	
	/**
	 * Used to get the socket the player is using
	 * 
	 * @return Player socket
	 * @author Trent Summerlin
	 */
	protected final Socket getSocket() {
		// Return socket
		return null;
	}

	@Override
	public boolean hasPermission() {
		if(this.hasOp()){
			return true;
		} else { // TODO: Add check later
			return false;
             } 
	}

	@Override
	public boolean hasOp() {
		return false;
	}

	@Override
	public double getHealth() {
		return 0;
	}

	@Override
	public double getMaxHealth() {
		return 20.0;
	}

	@Override
	public void setHealth() {
		//TODO
	}

	@Override
	public void setMaxHealth(int heal) {
		return this.getMaxHealth() == heal;
	}
	
}
