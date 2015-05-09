package net.trenterprises.diamondcore.cross.api.java.event.desktop;

import java.net.Socket;

import net.trenterprises.diamondcore.cross.api.java.event.Event;

public class DesktopPlayerLoginEvent extends Event {
	
	public String username;
	public Socket s;
	
	public DesktopPlayerLoginEvent(String username, Socket s) {
		this.username = username;
		this.s = s;
	}

	@Override
	public String getName() {
		return "DesktopPlayerLoginEvent";
	}
	
}
