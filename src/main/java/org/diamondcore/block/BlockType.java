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
import java.util.EnumSet;

/**
 * Used for help in inventories, chunks, etc.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public enum BlockType {
	
	GRASS_BLOCK("minecraft:grass", 2, 0, GrassBlock.class), COBBLESTONE("minecraft:cobblestone", 4, 0, CobbleStone.class), BEDROCK("minecraft:bedrock", 7, 0, BedrockBlock.class);
	
	protected final String sid;
	protected final int nid;
	protected final int type;
	protected final Class<? extends Block> block;
	private BlockType(String sid, int nid, int type, Class<? extends Block> block) {
		this.sid = sid;
		this.nid = nid;
		this.type = type;
		this.block = block;
	}
	
	protected static ArrayList<BlockType> blocks = new ArrayList<BlockType>(EnumSet.allOf(BlockType.class));
	public static BlockType getByID(int id, int type) {
		System.out.println(blocks.size());
		for(BlockType block : blocks) {
			if(block.getNID() == id && block.getType() == type)
				System.out.println("FOUND MATCH");
		}
		return null;
	}
	
	/**
	 * Used to get the String ID of
	 * the block
	 * 
	 * @return String block ID
	 * @author Trent Summerlin
	 */
	public final String getSID() {
		return this.sid;
	}
	
	/**
	 * Used to get the Numeric ID of
	 * the block
	 * 
	 * @return Numeric block ID
	 * @author Trent Summerlin
	 */
	public final int getNID() {
		return this.nid;
	}
	
	/**
	 * Used to get the numeric type ID
	 * of the block
	 * 
	 * @return Numeric Block type
	 * @author Trent Summerlin
	 */
	public final int getType() {
		return this.type;
	}
	
	/**
	 * Used to get the class of the block
	 * 
	 * @return Class block-binding
	 * @author Trent Summerlin
	 */
	public final Class<? extends Block> getBlock() {
		return this.block;
	}
	
}
