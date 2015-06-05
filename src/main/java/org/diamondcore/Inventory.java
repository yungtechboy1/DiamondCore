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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.diamondcore.block.BlockType;
import org.diamondcore.file.FileList;
import org.diamondcore.item.Item;
import org.diamondcore.utils.DatUtils;
import org.diamondcore.utils.StringUtils;

public class Inventory {
	
	// Raw data
	protected final byte[] data;
	protected final DataInputStream in;
	protected boolean compiled = false;
	
	// Inventory data
	protected ArrayList<Item> itemList = new ArrayList<Item>();
	
	public Inventory(PlayerSession session, Player player) throws IOException {
		this.in = new DataInputStream(new FileInputStream(FileList.playerFolder.toString() + "/test-user" + ".dat"));
		this.data = IOUtils.toByteArray(in);
		this.compile();
	}
	
	public Item[] getItems() {
		Item[] list = new Item[itemList.size()];
		for(int i = 0; i < list.length; i++)
			list[i] = itemList.get(i);
		return list;
	}
	
	@SuppressWarnings("unused")
	protected final void compile() throws IOException {
		boolean isValid = DatUtils.validate(data);
		if(isValid && !compiled) {
			DataInputStream inv = new DataInputStream(new ByteArrayInputStream(DatUtils.getInventory(data)));
			inv.readByte();
			byte blocks = inv.readByte();
			for(int i = 0; i < blocks; i++) {
				inv.readByte();
				short id = inv.readShort(); // TODO: Use ID to get the block type
				byte type = inv.readByte(); // TODO: Use type to get block type of block
				String blockName = null;
				short nameLen = inv.readShort();
				if(nameLen > 0)
					blockName = StringUtils.readString(nameLen, inv);
				byte slot = inv.readByte();
				byte quantity = inv.readByte();
				itemList.add(new Item(this, BlockType.GRASS_BLOCK, quantity, slot, blockName)); // For now it's always grass
			}
			compiled = true;
		}
		else
			return; // TODO: Throw exception
	}
	
	public static void main(String[] args) throws IOException {
		Inventory inv = new Inventory(null, null);
		for(Item i : inv.getItems()) {
			System.out.println("----------");
			System.out.println(i.getDisplayName());
			System.out.println(i.getQuantity());
			System.out.println(i.getType().getSID());
		}
	}
	
}
