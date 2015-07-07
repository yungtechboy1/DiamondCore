package org.diamondcore.entity;

import org.diamondcore.command.CommandSender;

public abstract class Human extends Entity implements CommandSender {
	
	@Override
	public abstract String getName();
	
}
