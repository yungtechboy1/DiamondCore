/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.desktop;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import net.trenterprises.diamondcore.cross.Entity;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.utils.VarInt;

/**
 * Used to represent a desktop sessions
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class DesktopSession {
	
	public static ArrayList<DesktopSession> desktopClients = new ArrayList<DesktopSession>();
	
	// Info
	protected final String username;
	protected final UUID uuid;
	protected final int id;
	
	// Socket
	protected final Socket playerSocket;
	protected final DataInputStream input;
	protected final DataOutputStream output;
	
	// Seucurity
	protected final byte[] verifyToken;
	
	public DesktopSession(String username, UUID uuid, Socket playerSocket, byte[] verifyToken) throws IOException {
		this.username = username;
		this.uuid = uuid;
		this.id = Entity.generateEntityID();
		
		this.playerSocket = playerSocket;
		this.input = new DataInputStream(playerSocket.getInputStream());
		this.output = new DataOutputStream(playerSocket.getOutputStream());
		
		this.verifyToken = verifyToken;
		
		// Write "join game" packet
		ByteArrayOutputStream joinGame = new ByteArrayOutputStream();
		DataOutputStream joinGameOutput = new DataOutputStream(joinGame);
		joinGameOutput.writeInt(id);
		joinGameOutput.write((byte) 0x01 & 0xFF);
		joinGameOutput.write((byte) 0x00 & 0xFF);
		joinGameOutput.write((byte) ServerSettings.getMaxPlayers() & 0xFF);
		String type = "default";
		joinGameOutput.write(VarInt.writeUnsignedVarInt(type.getBytes().length));
		joinGameOutput.write(type.getBytes());
		joinGameOutput.writeBoolean(false);
		joinGameOutput.flush();
		
		// Send "join game" packet
		output.write(VarInt.writeUnsignedVarInt(joinGame.size()));
		output.write(joinGame.toByteArray());
		output.flush();
		
		// Write "location" packet
		ByteArrayOutputStream location = new ByteArrayOutputStream();
		int x = 0; int y = 0; int z = 0;
		byte[] longMagic = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
		int rawCoords = ((x & 0x3FFFFFF) << 38) | ((y & 0xFFF) << 26) | (z & 0x3FFFFFF);
		byte[] coords = new BigInteger(rawCoords, longMagic).toByteArray();
		location.write((byte) 0x05);
		location.write(coords);
		location.flush();
		
		// Send "location" packget
		output.write(VarInt.writeUnsignedVarInt(location.size()));
		output.write(location.toByteArray());
		output.flush();
		
		// Write "abilites" packet
		ByteArrayOutputStream abilities = new ByteArrayOutputStream();
		DataOutputStream abilitiesOutput = new DataOutputStream(abilities);
		abilitiesOutput.write((byte) 0x39);
		abilitiesOutput.write(new byte[] {});
		abilitiesOutput.writeFloat(1.0f);
		abilitiesOutput.writeFloat(1.0f);
		
		// Send "abilites" packet
		output.write(VarInt.writeUnsignedVarInt(abilities.size()));
		output.write(abilities.toByteArray());
		output.flush();
		
		// Write the "position + look" packet
		ByteArrayOutputStream pLook = new ByteArrayOutputStream();
		DataOutputStream pLookOutput = new DataOutputStream(pLook);
		pLookOutput.writeDouble(0.0);
		pLookOutput.writeDouble(0.0);
		pLookOutput.writeDouble(0.0);
		pLookOutput.writeFloat(0.0f);
		pLookOutput.write(new byte[] {});
		
		// Send the "position + look" packet
		output.write(VarInt.writeUnsignedVarInt(pLook.size()));
		output.write(pLook.toByteArray());
		output.flush();
	}
	
	/**
	 * Used to get the player name
	 * 
	 * @author Trent Summerlin
	 * @return Player username
	 */
	public final String getUsername() {
		return this.username;
	}
	
	/**
	 * Used to get the player UUID
	 * 
	 * @author Trent Summerlin
	 * @return Player UUID
	 */
	public final UUID getUUID() {
		return this.uuid;
	}
	
	/**
	 * Used to get the entity ID of the player
	 * 
	 * @author Trent Summerlin
	 * @return Player Entity ID
	 */
	public final int getEntityID() {
		return this.id;
	}
	
	/**
	 * Used to get the socket the player is using
	 * 
	 * @author Trent Summerlin
	 * @return Player socket
	 */
	public final Socket getPlayerSocket() {
		return this.playerSocket;
	}
	
}
