/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.exception;

/**
 * Thrown when a IllegalArgumentClass exception is thrown by the JVM
 * because newInstance() is invalid because the class has a constructor
 * with parameters.
 */
public class InvalidConstructorException extends PluginException {

	private static final long serialVersionUID = -6873938897465969121L;
	
	public InvalidConstructorException(Class<?> pluginClass) {
		super("There was a error instintaniting the class " + pluginClass.getName() + " because it has " + (pluginClass.getConstructors().length-1) + " too many constructors!");
	}
	
}
