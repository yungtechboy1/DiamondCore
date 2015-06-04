package org.diamondcore.diamond.exception;

public class DiamondException extends Exception {
	
	private static final long serialVersionUID = 6472099442596756389L;

	public DiamondException() {
		super("There was a error in DiamondCore!");
	}
	
	public DiamondException(String error) {
		super("There was an error in DiamondCore!\nCause: " + error);
	}
	
}
