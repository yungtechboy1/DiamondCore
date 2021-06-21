/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.block;

public class CobbleStone extends SolidBlock {

	@Override
	public String getName() {
		return "Cobblestone";
	}

	@Override
	public double getHardness() {
		return 0;
	}

	@Override
	public double getLightLevel() {
		return 0;
	}

}
