/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.lang.exeption;

public final class InvalidLangException extends LangException {
	
	private static final long serialVersionUID = -1695445762024048058L;
	
	public InvalidLangException(String lang) {
		super("The language file " + lang + ".lang does not exist!");
	}
	
}
