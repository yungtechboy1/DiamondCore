/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;


/**
 * This class is used to start up the DiamondCore program
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class run {
	
	static boolean shouldDebug = false;
	
	public static void mainlo(String[] args) throws IOException {
		OutputStream out = new FileOutputStream("./players/test-user.json");
		DataOutputStream file = new DataOutputStream(out);
		
		
		ByteArrayOutputStream begin = new ByteArrayOutputStream();
		begin.write((byte) 0xDF);
		begin.write(1);
		String username = "SuperstarGamer";
		begin.write((byte) username.getBytes().length);
		begin.write(username.getBytes());
		
		DataOutputStream inv = new DataOutputStream(begin);
		inv.writeByte((byte) 0x01);
		inv.write(1);
		
		inv.writeByte((byte) 0x02); // Signal it is a block
		inv.writeShort((short) 38); // ID
		inv.writeByte((byte) 0); // Type
		inv.writeShort((short) 0); // No custom name
		inv.writeByte((byte) 5); // Slot
		inv.writeByte((byte) 21); // Quantity
		inv.flush();
		
		file.write(begin.toByteArray());
	}
	
	public static void main(String[] args) {
		try {
			if(args.length >= 1)
				shouldDebug = Boolean.parseBoolean(args[0]);
			else
				shouldDebug = false;
			
			String logo = IOUtils.toString(run.class.getResource("/files/logo.txt").openStream());
			System.out.println(logo);
			new Server(shouldDebug);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
