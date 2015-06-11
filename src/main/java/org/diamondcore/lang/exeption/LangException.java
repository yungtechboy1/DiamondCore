package org.diamondcore.lang.exeption;

public class LangException extends Exception {
	
	private static final long serialVersionUID = 7948660864513730867L;

	public LangException(String cause) {
		super("There was an error with the lang!\n\tCause: " + cause);
	}
	
}
