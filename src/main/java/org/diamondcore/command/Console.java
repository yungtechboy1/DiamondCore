package org.diamondcore.command;

import java.util.Scanner;

import org.diamondcore.Diamond;

public class Console extends Thread implements CommandSender {
	
	@SuppressWarnings("resource")
	public void run() {
		Scanner input = new Scanner(System.in);
		while(true) {
			while(!input.hasNextLine()); // Wait for input
			String[] raw = input.nextLine().split(" ");
			
			// Get command
			String label = raw[0];
			String[] args = new String[raw.length-1];
			for(int i = 1; i < raw.length; i++)
				args[i-1] = raw[i];
			
			Command c = Command.getByName(label);
			if(c != null)
				c.execute(this, args);
			else
				Diamond.logger.warn("Unknown command!");
		}
	}

	@Override
	public boolean hasPermission() {
		return true;
	}

	@Override
	public boolean hasOp() {
		return true;
	}
	
	public void sendMessage(String msg) {
		Diamond.logger.info(msg);
	}
	
}
