package org.diamondcore.lang.exeption;

public class LangOverrideException extends LangException {
	
	private static final long serialVersionUID = 8002033541976652901L;

	public LangOverrideException(String langKey) {
		super("A plugin tried to override an existing lang! (" + langKey + ")");
	}

}
