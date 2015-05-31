/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.command.vanilla;

import net.trenterprises.diamondcore.cross.command.CommandHandler;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.NativeCommand;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.world.time.WorldTime;

public class TimeCommand extends NativeCommand {
	
	String[] args;
	
	public TimeCommand(CommandHandler handler) {}
	
	public TimeCommand(String[] args) {
		this.args = args;
	}
	
	@Override
	public String getName() {
		return "time";
	}

	@Override
	public String getDescription() {
		return "used to get or set the time of the world";
	}

	@Override
	public String[] getDefaultParameters() {
		return null;
	}

	@Override
	public String[] getParameters() {
		String[] params = new String[2];
		params[0] = "set|add|remove";
		params[1] = "amount";
		return params;
	}

	@Override
	public void execute(CommandSender sender, DiamondLogger logger) {
		String result = null;
		if(args.length > 2 || args.length == 1) result = "Usage: /time <set|add|remove> <amount>";
		else if(args.length == 0) result = "Time is " + WorldTime.getTime();
		if(sender.equals(CommandSender.CONSOLE)) {
			logger.info(result);
		} else if(sender.equals(CommandSender.POCKETPLAYER)) {
			// Code for MCPE
		} else if(sender.equals(CommandSender.DESKTOPPLAYER)) {
			// Code for Minecraft Desktop
		}
	}

}
