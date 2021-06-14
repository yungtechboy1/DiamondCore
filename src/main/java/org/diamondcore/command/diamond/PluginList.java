/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.command.diamond;

import org.diamondcore.api.PluginSession;
import org.diamondcore.command.CommandExecutor;
import org.diamondcore.command.CommandSender;

public class PluginList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		PluginSession[] plugins = PluginSession.getPluginSessions();
		
		builder.append("Plugins(" + plugins.length + "): ");
		for(int i = 0; i < plugins.length; i++)
			builder.append(plugins[i].getPlugin().getName() + (i+1 < plugins.length ? ", " : ""));
		
		sender.sendMessage(builder.toString());
		
		return true;
	}

}
