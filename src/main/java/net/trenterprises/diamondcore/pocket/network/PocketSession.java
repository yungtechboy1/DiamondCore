package net.trenterprises.diamondcore.pocket.network;

import java.io.IOException;
import java.net.SocketAddress;

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.network.ClientSession;
import net.trenterprises.diamondcore.pocket.network.utils.PocketPacketUtils;

import org.blockserver.io.BinaryReader;

/**
 * Represents a MCPE client session.
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public class PocketSession extends ClientSession{
    //private ArrayList<ACKPacket> ackQueue;
    //private ArrayList<NACKPacket> nackQueue;
    //private Map<Integer, CustomPacket> recoveryQueue;
    @SuppressWarnings("unused")
	private int currentSeqNum = 0;
    @SuppressWarnings("unused")
	private int lastSeqNum = 0;

    @SuppressWarnings("unused")
	private SocketAddress clientAddress;

    public PocketSession(DiamondCoreServer server, SocketAddress client){
        super(server);
        clientAddress = client;
    }

    @Override
    public void handlePacket(BinaryReader in){
        try {
            byte pid = in.readByte();
            Diamond.logger.debug("Handling Packet: "+pid);
            switch(pid){

                default:
                    if(pid <= PocketPacketIDList.RAKNET_CUSTOM_PACKET_MAX){
                        handleCustomPacket(in);
                    } else {
                        Diamond.logger.warn("Unknown Packet: "+pid);
                    }
                    break;
            }
        } catch (IOException e) {
            Diamond.logger.warn("IOException while handling packets: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleCustomPacket(BinaryReader in){
    	try {
    		BinaryReader reader = PocketPacketUtils.getEncap(in);
    		System.out.println(reader.readByte());
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
}