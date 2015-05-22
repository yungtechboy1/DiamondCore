package net.trenterprises.diamondcore.cross.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
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
	protected short pythonCode = -1;
	
	// Plugin bridge :: PYTHON
	protected int pythonPort = -1;
	
	// Extra
	private ServerSocket serverSocket;
	public int listenerPort = -1;
	public DiamondLogger logger = new Log4j2Logger("DiamondCore");

	public PluginPacketHandler() throws IOException {
		// Set socket Settings
		this.serverSocket = new ServerSocket();
		this.serverSocket.bind(new InetSocketAddress("0.0.0.0", 0));
		this.listenerPort = this.serverSocket.getLocalPort();
	}

	public void run() {
		logger.info("Loading handler on port " + this.listenerPort + "...");
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
								pythonCode = input.readShort();
								output.write(PluginPacketIDList.PACKET_ID_CONNECTION_SUCCESS);
								pythonConnected = true;
							} else {
								output.write(PluginPacketIDList.PACKET_ID_CONNECTION_FAILURE);
								output.writeUTF("There is already another bridge connected!");
							}
							break;
						case 0x05:
							short code = input.readShort();
							if(pythonConnected && code == pythonCode) {
								byte level = input.readByte();
								if(level == 0) logger.info(input.readUTF());
								if(level == 1) logger.warn(input.readUTF());
								if(level == 2) logger.err(input.readUTF());
							} else {
								// Reject
								System.out.println("Code: " + pythonCode + " Received: " + code);
							}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			byte b = (byte) 0x7FFFFFFF;
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
		handler.logger.info("Started!");
	}
}
