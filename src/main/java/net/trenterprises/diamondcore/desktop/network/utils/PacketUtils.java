package net.trenterprises.diamondcore.desktop.network.utils;

import java.io.DataInputStream;
import java.io.IOException;

import net.trenterprises.diamondcore.cross.borrowed.VarInt;

/**
 * This class is used to work with features quickly
 * <br>
 * that normally a fine chunk of code to do
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class PacketUtils {
	
	private PacketUtils() {}
	
	/**
	 * Used to read a string from a InputStream
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param input
	 * 		InputStream to read from
	 * @throws IOException 
	 * @return Decoded String
	 */
	public static String readString(DataInputStream input) throws IOException {
		int length = VarInt.readUnsignedVarInt(input.readByte());
		byte[] rawString = new byte[length];
		for(int i = 0; i < length; i++) rawString[i] = input.readByte();
		return new String(rawString, "UTF-8");
	}
	
}
