/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.block;

/**
 * This is extended by solid blocks that can
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
