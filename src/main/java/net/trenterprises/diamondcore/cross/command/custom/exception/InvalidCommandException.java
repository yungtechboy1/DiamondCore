package net.trenterprises.diamondcore.cross.command.custom.exception;

public class InvalidCommandException extends Exception {
	
	private static final long serialVersionUID = -4558056958077269306L;

	public InvalidCommandException(String commandError) {
		super("There was a error with a custom command!\nError: " + (commandError != null ? commandError : "generic"));
	}
	
}
