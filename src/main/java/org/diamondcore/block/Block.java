/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.block;

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
                blockList.add(new BedrockBlock());
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
