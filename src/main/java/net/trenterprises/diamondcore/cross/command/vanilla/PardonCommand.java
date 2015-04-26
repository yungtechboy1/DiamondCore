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

public class PardonCommand extends Command {
	
	protected final String username;
	
	public PardonCommand(String username) {
		this.username = username;
	}
	
	@Override
	public String getName() {
		return "pardon";
	}

	@Override
	public String getDescription() {
		return "used to remove a player from the ban list";
	}

	@Override
	public String[] getDefaultParameters() {
		String[] params = new String[1];
		params[0] = "username";
		return params;
	}

	@Override
	public String[] getParameters() {
		String[] params = new String[1];
		params[0] = this.username;
		return params;
	}

	@Override
	public void execute(CommandSender sender, DiamondLogger logger) {
		if(sender.equals(CommandSender.CONSOLE)) {
			try {
				JSONObject banObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.bannedPlayerList));
				if(banObject.containsKey(this.username)) {
					banObject.remove(this.username);
					BufferedWriter BanWriter = new BufferedWriter(new FileWriter(FileList.bannedPlayerList));
					BanWriter.write(banObject.toJSONString());
					BanWriter.close();
					logger.info("Pardoned player " + this.username);
				} else {
					logger.info("The player " + this.username + " is not banned!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
