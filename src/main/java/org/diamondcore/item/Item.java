/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.item;

import org.diamondcore.Inventory;
import org.diamondcore.block.BlockType;

/**
 * Used to represent a item or block item
 * 
 * @author Trent Summerlin
 * @version 1.0.0-SNAPSHOT
 */
public final class Item {
	
	protected final BlockType block;
	protected int quantity = -1;
	protected String name = null;
	
	public Item(Inventory inv, BlockType block, byte quantity, byte slot) {
		this(inv, block, quantity, slot, null);
	}
	
	public Item(Inventory inv, BlockType block, byte quantity, byte slot, String name) {
		this.block = block;
		this.quantity = quantity;
		this.name = name;
	}
	
	/**
	 * Used to set the quantity of the item in the
	 * players inventory
	 * 
	 * @param quantity
	 * @author Trent Summerlin
	 */
	public final void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Used to set the display name of the item in
	 * the players inventory
	 * 
	 * @param name
	 * @author Trent Summerlin
	 */
	public final void setDisplayName(String name) {
		this.name = name;
	}
	
	/**
	 * Used to get the quantity of the item in the
	 * players inventory
	 * 
	 * @return Item quantity
	 * @author Trent Summerlin
	 */
	public final int getQuantity() {
		return quantity;
	}
	
	/**
	 * Used to get the display name of the item in
	 * the players inventory
	 * @return
	 */
	public final String getDisplayName() {
		return this.name;
	}
	
	/**
	 * Used to get block/item type that is in the
	 * player's inventory
	 * 
	 * @return Block type
	 * @author Trent Summerlin
	 */
	public final BlockType getType() {
		return this.block;
	}
	
}
