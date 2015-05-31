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
 * This class is extended by blocks that flow
 * <br>
 * and can possibly be picked up by a bucket
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class FlowableBlock extends Block {
	
	/**
	 * Used to get the name of the block
	 * 
	 * @author Trent Summerlin
	 * @return Block name
	 */
	public abstract String getName();
	
	/**
	 * Used to get the speed of the flow of the block
	 * 
	 * @author Trent Summerlin
	 * @return Block flow speed
	 */
	public abstract String getFlowSpeed();
	
	/**
	 * Used to get the level of light emitted by the block
	 * 
	 * @author Trent Summerlin
	 * @return Block light level
	 */
	public abstract double getLightLevel();
	
	/**
	 * Used to see if the block can be held by a bucket
	 * 
	 * @author Trent Summerlin
	 * @return Whether or not the bucket can hold it
	 */
	public abstract boolean canBeHeldByBucket();
	
}
