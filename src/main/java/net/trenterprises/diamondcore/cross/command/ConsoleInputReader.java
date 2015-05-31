/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.command;

import java.util.Scanner;

/**
 * Used to read input from the console for command execution
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class ConsoleInputReader {
	
	protected final Scanner input;
	
	public ConsoleInputReader() {
		input = new Scanner(System.in);
	}
	
	public void tick() {
		String nextCommand = input.nextLine();
		if(nextCommand != null) CommandHandler.run(CommandSender.CONSOLE, nextCommand, CommandHandler.getArguments(nextCommand));
	}
	
	public void close() {
		input.close();
	}
	
}
