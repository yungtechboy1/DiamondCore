/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.exception;

public class DiamondException extends Exception {
	
	private static final long serialVersionUID = 6472099442596756389L;

	public DiamondException() {
		super("DiamondCore have error!");
	}
	
	public DiamondException(String error) {
		super("DiamondCore have error!\nCause: " + error);
	}
	
}
