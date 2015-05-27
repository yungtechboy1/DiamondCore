package net.trenterprises.diamondcore.desktop.network.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.trenterprises.diamondcore.cross.utils.VarInt;

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
		int length = VarInt.readUnsignedVarInt(input, true);
		byte[] rawString = new byte[length];
		for(int i = 0; i < length; i++) rawString[i] = input.readByte();
		return new String(rawString, "UTF-8");
	}
	
	/**
	 * Used to write a string to a OutputStream
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param string
	 * 		String to encode
	 * @param output
	 * 		OutputStream to write to
	 * @throws IOException
	 * @returns Encoded String
	 */
	public static void writeString(String string, DataOutputStream output) throws IOException {
		output.write(VarInt.writeUnsignedVarInt(string.getBytes().length));
		output.write(string.getBytes());
	}
	
	/**
	 * Used to write a short to a OutputStream
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param number
	 * 		Short to encode
	 * @param output
	 * 		OutputStream to write to
	 * @throws IOException
	 */
	public static void writeUnsignedShort(short number, DataOutputStream output) throws IOException {
		output.writeChar(number);
	}
	
	/**
	 * Used to read multiple bytes from a InputStream at once
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param input
	 * 		InputStream to read from
	 * @param length
	 * 		Amount of bytes to read
	 * @return Bytes read
	 * @throws IOException 
	 */
	public static byte[] readBytes(DataInputStream input, int length) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for(int i = 0; i < length; i++) output.write(input.readByte());
		return output.toByteArray();
	}
	
}
