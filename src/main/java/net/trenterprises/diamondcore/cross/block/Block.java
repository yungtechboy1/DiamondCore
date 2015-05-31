/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.block;

import java.util.ArrayList;

/**
 * This class is used for handling blocks in DiamondCore
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class Block {
	
	protected static ArrayList<Block> blockList = new ArrayList<Block>();
	
	/**
	 * Used to register the blocks into the system for use
	 * 
	 * @author Trent Summerlin
	 * @version 0.1.0-SNAPSHOT
	 */
	public static void registerBlocks() {
		blockList.add(new GrassBlock());
		blockList.add(new DirtBlock());
	}
	
	/**
	 * Used to get the registered blocks of the program
	 * 
	 * @author Trent Summerlin
	 * @return Registered blocks
	 */
	public final static ArrayList<Block> getBlocks() {
		return blockList;
	}
	
	/**
	 * Used to get the name of the block
	 * 
	 * @author Trent Summerlin
	 * @return Block name
	 */
	public abstract String getName();
	
}
