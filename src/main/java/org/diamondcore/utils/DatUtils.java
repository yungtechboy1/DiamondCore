/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Used for help in DAT files for DiamondCore
 * 
 * @author Trent
 * @version 0.1.0-SNAPSHOT
 */
public class DatUtils {
	
	private DatUtils() {}
	
	protected static final byte DAT_BEGIN =  (byte) 0xDF; 
	protected static final byte INV_BEGIN =  (byte) 0x01;
	protected static final byte ITEM_BEGIN = (byte) 0x02;
	protected static final byte POS_BEGIN =  (byte) 0x03;
	
	/**
	 * Used to see if a DAT file is a valid 
	 * in terms of DiamondCore
	 * 
	 * @param data
	 * @return Valid DiamondCore file
	 * @author Trent Summerlin
	 */
	public static boolean validate(byte[] data) {
		return data[0] == DAT_BEGIN;
	}
	
	/**
	 * Used to get the inventory from a DAT byte array
	 * 
	 * @param data
	 * @return Player Inventory data
	 * @author Trent Summerlin
	 * @throws IOException
	 */
	public static byte[] getInventory(byte[] data) throws IOException {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(data));
		ByteArrayOutputStream inv = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(inv);
		boolean isValid = validate(data);
		if(isValid) {
			input.readByte();
			byte fields = input.readByte();
			StringUtils.readString(input.readByte(), input);
			boolean foundInv = false;
			for(int i = 0; i < fields; i++) {
				byte oid = input.readByte();
				if(oid == INV_BEGIN) {
					if(foundInv)
						break; // TODO: Throw exceptions
					byte blocks = input.readByte();
					output.writeByte(INV_BEGIN);
					output.writeByte(blocks);
					for(int j = 0; j < blocks; j++) {
						if(input.readByte() == ITEM_BEGIN) {
							// Get data
							short id = input.readShort();
							byte type = input.readByte();
							short nameLen = input.readByte();
							String name = StringUtils.readString(nameLen, input);
							byte slot = input.readByte();
							byte quantity = input.readByte();
							
							// Write data
							output.writeByte(ITEM_BEGIN);
							output.writeShort(id);
							output.writeByte(type);
							output.writeShort(nameLen);
							output.write(name.getBytes());
							output.writeByte(slot);
							output.writeByte(quantity);
							output.flush();
						} else
							break; // TODO: Throw exception
					}
					foundInv = true;
				}
			}
			return inv.toByteArray();
		} else
			return null;
	}
	
}
