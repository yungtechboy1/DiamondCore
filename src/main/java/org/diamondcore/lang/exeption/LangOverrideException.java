/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.lang.exeption;

public class LangOverrideException extends LangException {
	
	private static final long serialVersionUID = 8002033541976652901L;

	public LangOverrideException(String langKey) {
		super("A plugin tried to override an existing lang! (" + langKey + ")");
	}

}
