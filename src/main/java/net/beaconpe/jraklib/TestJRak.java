package net.beaconpe.jraklib;

import net.beaconpe.jraklib.protocol.EncapsulatedPacket;
import net.beaconpe.jraklib.server.JRakLibServer;
import net.beaconpe.jraklib.server.ServerHandler;
import net.beaconpe.jraklib.server.ServerInstance;

public class TestJRak implements ServerInstance {
	
	public TestJRak() {
		JRakLibServer s = new JRakLibServer(new RakLibLogger("JRakLib"), 19132, "0.0.0.0");
		@SuppressWarnings("unused")
		ServerHandler handler = new ServerHandler(s, this);
	}
	
	public static void main(String[] args) {
		new TestJRak();
	}

	@Override
	public void openSession(String identifier, String address, int port,
			long clientID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeSession(String identifier, String reason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEncapsulated(String identifier,
			EncapsulatedPacket packet, int flags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleRaw(String address, int port, byte[] payload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyACK(String identifier, int identifierACK) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleOption(String option, String value) {
		// TODO Auto-generated method stub
		
	}
	
}
