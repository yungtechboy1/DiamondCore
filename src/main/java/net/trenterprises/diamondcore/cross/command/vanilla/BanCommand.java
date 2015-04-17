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

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.file.FileList;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class BanCommand extends Command {
	
	private final String username;
	private final String reason;
	
	public BanCommand(String username) {
		this.username = username;
		this.reason = null;
	}
	
	public BanCommand(String username, String reason) {
		this.username = username;
		this.reason = reason;
	}
	
	@Override
	public String getName() {
		return "Ban";
	}
	
	@Override
	public String getDescription() {
		return "used to make sure a player by name (uuid) cannot rejoin the server";
	}

	@Override
	public String[] getDefaultParameters() {
		String[] params = new String[2];
		params[0] = "username";
		params[1] = "reason";
		return params;
	}

	@Override
	public String[] getParameters() {
		int length = 0;
		if(this.username != null) length++;
		if(this.reason != null) length++;
		String[] params = new String[length];
		if(this.username != null) params[0] = this.username;
		if(this.reason != null) params[1] = this.reason;
		return params;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(CommandSender sender, DiamondLogger logger) {
		String[] args = this.getParameters();
		try {
			if(args.length >= 1) {
				JSONObject BanObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.bannedPlayerList));
				if(BanObject.containsKey(args[0])) {
					if(args.length == 1) logger.info("The player " + args[0] + " is already banned!");
					else if(args.length == 2) logger.info("Ban reason for player " + args[0] + " set to: " + args[1]);
				}
				else {
					String banReason = (args.length == 2 ? (" for reason: " + args[1]) : "");
					BanObject.put(args[0], (args.length == 2 ? args[1] : null));
					BufferedWriter BanWriter = new BufferedWriter(new FileWriter(FileList.bannedPlayerList));
					BanWriter.write(BanObject.toJSONString());
					BanWriter.close();
					logger.info("Banned player " + args[0] + banReason);
				}
			}
			else logger.info("Usage: /ban <player> [reason]");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
