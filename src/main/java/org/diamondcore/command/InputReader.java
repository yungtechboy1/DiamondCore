package org.diamondcore.command;

import java.util.Scanner;

public class InputReader extends Thread {
	
	public void run() {
		Scanner input = new Scanner(System.in);
		while(true) {
			while(!input.hasNextLine()); // Wait for input
			String command = input.nextLine();
		}
	}
	
}
