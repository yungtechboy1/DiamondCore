package net.beaconpe.jraklib;

public class RakLibLogger implements Logger {
	
	private final String name;
	public RakLibLogger(String name) {
		this.name = name;
	}

	@Override
	public void notice(String message) {
		System.out.println("[" + name + "] [NOTICE] " + message);
	}

	@Override
	public void critical(String message) {
		System.out.println("[" + name + "] [CRITICAL] " + message);
	}

	@Override
	public void emergency(String message) {
		System.out.println("[" + name + "] [EMERGENCY] " + message);
	}
	
}
