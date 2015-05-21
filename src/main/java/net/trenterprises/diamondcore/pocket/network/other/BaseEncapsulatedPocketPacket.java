/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.pocket.network.other;


/**
 * This class is extended on by other classes that
 * <br>
 * handle encapsulated packets sent by Minecraft PE.
 * <br>
 * All of the data represented is for the decapsulated
 * <br>
 * packet only. The rest of it has nothing to do with
 * <br>
 * the outer shell of it
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 *
 */
public class BaseEncapsulatedPocketPacket {
	
	protected final byte packetID = 0x00;
	protected final byte packetLength_BYTE = 0;
	protected final byte packetLengh_BIT = 0;
	
	public BaseEncapsulatedPocketPacket(byte[] encapsulated) {
		// Get length of packets in bits
		// Get unknown 3 and/or 4 bytes
		// Get packet itself
	}
	
	public static void test(byte[] input){
	    for(int i = 0; i < input.length; i++) {
	    	for(int j = 0; j < 8; j++) {
	    		System.out.print(getBit(j, input[i]));
	    	}
	    	System.out.print(" ");
	    }
	    System.out.println();
	}
	
	public static byte getBit(int position, byte b)
	{
	   return (byte) ((b >> position) & 1);
	}
	
}
