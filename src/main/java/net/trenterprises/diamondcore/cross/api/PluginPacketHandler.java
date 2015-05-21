package net.trenterprises.diamondcore.cross.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import net.trenterprises.diamondcore.cross.api.python.PluginPacketIDList;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

public class PluginPacketHandler extends Thread implements Runnable {
	
	// Security
	protected final String allowedAddress = "127.0.0.1";
	protected boolean pythonConnected = false;
	
	// Plugin bridge :: PYTHON
	protected int pythonPort = -1;
	
	// Extra
	private ServerSocket serverSocket;
	public static int listenerPort = -1;
	public DiamondLogger logger = new Log4j2Logger("DiamondCore");

	public PluginPacketHandler() throws IOException {
		// Set socket Settings
		this.serverSocket = new ServerSocket(this.randInt(25000, 26000));
		listenerPort = this.serverSocket.getLocalPort();
	}

	public void run() {
		logger.info("Loading handler on port " + listenerPort + "...");
		logger.info("Waiting for connection from python!");
		while (true) {
			try {
				Socket s = serverSocket.accept();
				s.setSendBufferSize(1024);
				String address = s.getInetAddress().getHostAddress();
				if (address.equals(allowedAddress)) {
					DataInputStream input = new DataInputStream(s.getInputStream());
					DataOutputStream output = new DataOutputStream(s.getOutputStream());
					
					byte packetID = input.readByte();
					switch(packetID) {
						case PluginPacketIDList.PACKET_ID_CONFIRM_CONNECTION:
							if(!pythonConnected) {
								pythonPort = s.getPort();
								output.write(PluginPacketIDList.PACKET_ID_CONNECTION_SUCCESS);
								output.flush();
								pythonConnected = true;
								logger.info("Received connection from python!");
							} else {
								output.write(PluginPacketIDList.PACKET_ID_CONNECTION_FAILURE);
								output.writeUTF("There is already another bridge connected!");
							}
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	private int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public static void main(String[] args) throws IOException {
		PluginPacketHandler handler = new PluginPacketHandler();
		handler.logger.info("Starting...");
		handler.start();
		handler.logger.info("Started! Port");
	}
}
