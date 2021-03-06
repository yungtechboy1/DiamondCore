/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|  
                                                                                                      
*/

package org.diamondcore.command.vanilla;

import org.diamondcore.command.CommandExecutor;
import org.diamondcore.command.CommandSender;
import org.diamondcore.world.time.WorldTime;

public class Time implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage("Time is " + WorldTime.getTime());
			return true;
		} else if(args.length == 2) {
			String result = null;
			String func = args[0];
			int val = -1;
			
			try {
				val = Integer.parseInt(args[1]);
			} catch(Exception e) {
				return false;
			}
			
			if(func.equalsIgnoreCase("set")) {
				WorldTime.setTick(val);
				result = ("Set the time to " + val);
			} else if(func.equalsIgnoreCase("add")) {
				WorldTime.tick(val);
				result = ("Added " + val + " to the time");
			} else
				return false;
			
			sender.sendMessage(result);
			
			return true;
		}
		return false;
	}

}
