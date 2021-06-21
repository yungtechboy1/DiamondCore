/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.block;

public class DirtBlock extends SolidBlock {

	@Override
	public String getName() {
		return "Dirt";
	}

	@Override
	public double getHardness() {
		return 1.5;
	}

	@Override
	public double getLightLevel() {
		return 0.0;
	}

}
