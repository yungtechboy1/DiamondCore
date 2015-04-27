/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.pocket.network.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.trenterprises.diamondcore.pocket.network.PocketPacketIDList;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

/**
 * This class is used to work with MCPE packets easily
 * <br>
 * without needing to do much work, for example,
 * <br>
 * encapsulating and decapsulating a packet
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class PocketPacketUtils {
	
	protected final static int BITS_IN_BYTE = 8;
	
	/**
	 * This function is used to get an packet from a encapsulated one
	 * <br>
	 * swiftly without conversion techniques involved.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param bb
	 * @return Decapsulated packet
	 * @throws IOException 
	 */
	public static BinaryReader getEncap(BinaryReader raw) throws IOException {
		BinaryReader reader = toReader(getEncap(raw, 3));
		if(reader == null) reader = toReader(getEncap(raw, 7));
		return reader;
	}
	
	/**
	 * This function is hidden from the rest of the program, used
	 * <br>
	 * to get the decapsulated packet, but with the ability to choose
	 * <br>
	 * how many unknown bytes to grab in the process. If the bytes
	 * <br>
	 * remaining is equal to 0, it will return the decapsulated packet,
	 * <br>
	 * otherwise, it will return null. The one public one uses this
	 * <br>
	 * function by getting 3 bytes, but if the encap is null because
	 * <br>
	 * there are remaining bytes (Supposedly 4) it will then run this
	 * <br>
	 * function again, except with 7, instead of 3 bytes. This will
	 * <br>
	 * make it easier to retrieve decapsulated packets because it is unknown
	 * <br>
	 * whether a client is sending 0x40, or 0x60.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param raw
	 * @param unknown
	 * @return Decapsulated packet (If there are no remaining bytes) or, null.
	 * @throws IOException 
	 */
	protected static BinaryWriter getEncap(BinaryReader raw, int unknown) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		raw.readByte(); // Get first byte
		short rawLength = raw.readShort();
		raw.read(unknown); // Get unknown, and unneeded bytes.
		output.write(raw.read(rawLength/BITS_IN_BYTE)); // Now output the bytes read
		writer.close();
		if(raw.getInputStream().available() == 0) return new BinaryWriter(output);
		else return null;
	}
	
	/**
	 * This method is used to get the encapsulated packet
	 * <br>
	 * from a packet to send instead of retrieve swiftly.
	 * 
	 * @param raw
	 * @return Encapsulated packet form of packet used.
	 * @throws IOException 
	 */
	public static BinaryWriter putEncap(BinaryReader raw) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BinaryWriter writer = new BinaryWriter(output);
		writer.writeByte(PocketPacketIDList.MAGIC[0]);
		writer.writeShort((short) (toByteArray(raw.getInputStream()).length*BITS_IN_BYTE)); // Put packet length (in bits)
		writer.write(toByteArray(raw.getInputStream()));
		writer.close();
		return new BinaryWriter(output);
	}
	
	/**
	 * Converts a BinaryReader to a BinaryWriter
	 * 
	 * @author Trent Summerlin
	 * @param writer
	 * @return converted reader
	 */
	public static BinaryReader toReader(BinaryWriter writer) {
		ByteArrayOutputStream output = (ByteArrayOutputStream) writer.getOutputStream();
		InputStream input = new ByteArrayInputStream(output.toByteArray());
		BinaryReader reader = new BinaryReader(input);
		return reader;
	}
	
	/**
	 * Converts a BinaryReader to BinaryWriter
	 * 
	 * @author Trent Summerlin
	 * @param reader
	 * @return Converted writer
	 * @throws IOException
	 */
	public static BinaryWriter toWriter(BinaryReader reader) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(toByteArray(reader.getInputStream()));
		return new BinaryWriter(output);
	}
	
	/**
	 * Converts a input stream to a byte array
	 *  
	 * @author Trent Summerlin
	 * @param is
	 * @return converted byte array
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while(reads != -1) {
			baos.write(reads); reads = is.read();
		} 
		return baos.toByteArray();
	}
}