package org.jukeboxmc.raknet.utils;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface Identifiers {

    String Version = "1.16.100";
    int Protocol = 419;

    byte ConnectedPing = 0x00;
    byte UnconnectedPing = 0x01;
    byte UnconnectedPong = 0x1c;
    byte ConnectedPong = 0x03;
    byte OpenConnectionRequest1 = 0x05;
    byte OpenConnectionReply1 = 0x06;
    byte OpenConnectionRequest2 = 0x07;
    byte OpenConnectionReply2 = 0x08;
    byte ConnectionRequest = 0x09;
    byte ConnectionRequestAccepted = 0x10;
    byte NewIncomingConnection = 0x13;
    byte DisconnectNotification = 0x15;
    byte IncompatibleProtocolVersion = 0x19;

    byte AcknowledgePacket = (byte) 0xc0;
    byte NacknowledgePacket = (byte) 0xa0;
    byte Query = (byte) 0xfe;

}
