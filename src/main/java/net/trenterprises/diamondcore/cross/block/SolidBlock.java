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

/**
 * This is extended by solid blocks that can
 * <br>
 * be destroyed or placed by players
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class SolidBlock extends Block {
	
	/**
	 * Used to get the name of the block
	 * 
	 * @author Trent Summerlin
	 * @return Block name
	 */
	public abstract String getName();
	
	/**
	 * Used to get the hardness of the block
	 * 
	 * @author Trent Summerlin
	 * @return Block hardness
	 */
	public abstract double getHardness();
	
	/**
	 * Used to get the level of light emitted by the block
	 * 
	 * @author Trent Summerlin
	 * @return Block light level
	 */
	public abstract double getLightLevel();
	
}
