/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

/**
 * This class is used for reading input from the console
 * in order to handle commands.
 */
package net.trenterprises.diamondcore.cross.console;

import java.util.Scanner;

import net.trenterprises.diamondcore.cross.command.CommandHandler;
import net.trenterprises.diamondcore.cross.command.CommandSender;

public class ConsoleInputReader {
	
	protected Scanner input;
	protected String nextCommand = null;
	
	public ConsoleInputReader() {
		input = new Scanner(System.in);
	}
	
	public void tick() {
		if(input.hasNextLine()) nextCommand = input.nextLine();
		if(nextCommand != null) {
			CommandHandler.run(CommandSender.CONSOLE, nextCommand, CommandHandler.getArguments(nextCommand));
			nextCommand = null;
		}
	}
	
	public void close() {
		input.close();
	}
	
}
