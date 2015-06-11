package org.diamondcore.lang.exeption;

public final class InvalidLangException extends LangException {
	
	private static final long serialVersionUID = -1695445762024048058L;
	
	public InvalidLangException(String lang) {
		super("The language file " + lang + ".lang does not exist!");
	}
	
}
