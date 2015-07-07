package org.diamondcore.command;

public interface CommandSender {
	
	public void sendMessage(String msg);
	public boolean hasPermission();
	public boolean hasOp();
	
}
