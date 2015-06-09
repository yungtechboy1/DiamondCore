package org.diamondcore.command;

import java.util.Scanner;

public class InputReader extends Thread {
	
	public void run() {
		Scanner input = new Scanner(System.in);
		while(true) {
			while(!input.hasNextLine()); // Wait for input
			String[] raw = input.nextLine().split(" ");
			
			// Get command
			CommandType command = CommandType.getByName(raw[0]);
			String[] args = new String[raw.length-1];
			for(int i = 1; i < raw.length; i++)
				args[i-1] = raw[i];
		}
	}
	
}
