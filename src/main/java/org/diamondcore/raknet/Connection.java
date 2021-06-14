package org.jukeboxmc.raknet;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import lombok.Data;
import org.jukeboxmc.raknet.protocol.*;
import org.jukeboxmc.raknet.utils.BinaryStream;
import org.jukeboxmc.raknet.utils.Identifiers;
import org.jukeboxmc.raknet.utils.Reliability;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
public class Connection {

    private Listener listener;
    private short mtuSize;
    private InetSocketAddress address;

    private Status state = Status.CONNECTING;

    private List<Integer> nackQueue = new LinkedList<>();
    private List<Integer> ackQueue = new LinkedList<>();

    private Map<Integer, DataPacket> recoveryQueue = new LinkedHashMap<>();

    private List<DataPacket> packetToSend = new LinkedList<>();

    private DataPacket sendQueue = new DataPacket();

    private Map<Integer, Map<Integer, EncapsulatedPacket>> splitPackets = new LinkedHashMap<>();

    private int windowStart = -1;
    private int windowEnd = 2048;
    private int reliableWindowStart = 0;
    private int reliableWindowEnd = 2048;
    private Map<Integer, EncapsulatedPacket> reliableWindow = new LinkedHashMap<>();
    private int lastReliableIndex = -1;

    private Queue<Integer> receivedWindow = new ConcurrentLinkedQueue<>();

    private int lastSequenceNumber = -1;
    private int sendSequenceNumber = 0;

    private int messageIndex;
    private List<Integer> channelIndex = new LinkedList<>();

    private int splitID = 0;

    private long lastUpdate;
    private boolean isActive = false;

    Connection( Listener listener, short mtuSize, InetSocketAddress address ) {
        this.listener = listener;
        this.mtuSize = mtuSize;
        this.address = address;

        this.lastUpdate = System.currentTimeMillis();

        for ( int i = 0; i < 32; i++ ) {
            this.channelIndex.add( 0 );
        }
    }

    //TODO hier weiter machen ich glaub ab hier ist was falsch
    void update( long timestamp ) {
        if ( !this.isActive && this.lastUpdate + 10000 < timestamp ) {
            this.disconnect( "Timeout" );
            return;
        }
        this.isActive = false;

        if ( this.ackQueue.size() > 0 ) {
            ACK ack = new ACK();
            ack.setPackets( this.ackQueue );
            this.sendPacket( ack );
            this.ackQueue.clear();
        }

        if ( this.nackQueue.size() > 0 ) {
            NACK nack = new NACK();
            nack.setPackets( this.nackQueue );
            this.sendPacket( nack );
            this.nackQueue.clear();
        }

        if ( this.packetToSend.size() > 0 ) {
            int limit = 16;

            for ( DataPacket packet : this.packetToSend ) {
                packet.sendTime = timestamp;
                packet.write();

                this.recoveryQueue.put( packet.sequenceNumber, packet );
                this.packetToSend.remove( packet );
                this.sendPacket( packet );

                if ( --limit <= 0 ) {
                    break;
                }
            }

            if ( this.packetToSend.size() > 2048 ) {
                this.packetToSend.clear();
            }
        }

        for ( int seq : new ArrayList<>( this.recoveryQueue.keySet() ) ) {
            DataPacket packet = this.recoveryQueue.get( seq );
            if ( packet.sendTime < System.currentTimeMillis() - 8000 ) {
                this.packetToSend.add( packet );
                this.recoveryQueue.remove( seq );
            }
        }

        for ( int seq : this.receivedWindow ) {
            if ( seq < this.windowStart ) {
                this.receivedWindow.remove( seq );
            } else {
                break;
            }
        }
        this.sendQueue();
    }

    void receive( ByteBuf buffer ) {
        this.isActive = true;
        this.lastUpdate = System.currentTimeMillis();

        ByteBuf duplicate = buffer.duplicate();
        int packetId = buffer.readUnsignedByte();

        if ( ( packetId & BitFlags.VALID ) == 0 ) {//Ignore
        } else if ( ( packetId & BitFlags.ACK ) != 0 ) {
            this.handleACK( buffer );
        } else if ( ( packetId & BitFlags.NACK ) != 0 ) {
            System.out.println( "Handle NACK.." );
            this.handleNACK( buffer );
            System.out.println( "NACK was handelt!" );
        } else {
            System.out.println( "Datagram -> " + packetId );
            this.handleDatagram(duplicate );
        }
    }

    //TODO
    private void handleDatagram( ByteBuf buffer ) {
        DataPacket dataPacket = new DataPacket();
        dataPacket.buffer = buffer;
        dataPacket.read();

        System.out.println( dataPacket.toString() );

        if ( dataPacket.sequenceNumber < this.windowStart || dataPacket.sequenceNumber > this.windowEnd || this.receivedWindow.contains( dataPacket.sequenceNumber ) ) {
            return;
        }

        int diff = dataPacket.sequenceNumber - this.lastSequenceNumber;
        int index = this.nackQueue.indexOf( dataPacket.getSequenceNumber() );

        if ( index > -1 ) {
            this.nackQueue.remove( index );
        }

        this.ackQueue.add( dataPacket.sequenceNumber );
        this.receivedWindow.add( dataPacket.sequenceNumber );

        if ( diff != 1 ) {
            for ( int i = this.lastSequenceNumber + 1; i < dataPacket.sequenceNumber; i++ ) {
                if ( !this.receivedWindow.contains( i ) ) {
                    this.nackQueue.add( i );
                }
            }
        }

        if ( diff >= 1 ) {
            this.lastSequenceNumber = dataPacket.sequenceNumber;
            this.windowStart += diff;
            this.windowEnd += diff;
        }

        for ( Object packetObject : dataPacket.getPackets() ) {
            if ( packetObject instanceof EncapsulatedPacket ) {
                this.receivePacket( (EncapsulatedPacket) packetObject );
            }
        }
    }

    private void handlePacket( EncapsulatedPacket packet ) {
        if ( packet.split ) {
            this.handleSplit( packet );
            return;
        }
        int id =  packet.buffer.readUnsignedByte();

        if ( id < 0x80 ) {
            if ( this.state == Status.CONNECTING ) {
                if ( id == Identifiers.ConnectionRequest ) {
                    this.handleConnectionRequest( packet.buffer );
                } else if ( id == Identifiers.NewIncomingConnection ) {
                    NewIncomingConnection incomingConnection = new NewIncomingConnection();
                    incomingConnection.fill( packet.buffer );
                    incomingConnection.read();

                    int serverPort = this.listener.getAddress().getPort();
                    if ( incomingConnection.getAddress().getPort() == serverPort ) {
                        this.state = Status.CONNECTED;
                    }
                }
            } else if ( id == Identifiers.DisconnectNotification ) {
                this.disconnect( "Client disconnect" );
            } else if ( id == Identifiers.ConnectedPing ) {
                this.handleConnectedPing( packet.buffer );
            }
        } else if ( this.state == Status.CONNECTED ) {
            System.out.println( "Allready connected: " + id );
        }
    }

    private void handleACK( ByteBuf buffer ) {
        ACK packet = new ACK();
        packet.fill( buffer );
        packet.read();

        for ( int seq : packet.getPackets() ) {
            this.recoveryQueue.remove( seq );
        }
    }

    private void handleNACK( ByteBuf buffer ) {
        NACK packet = new NACK();
        packet.fill( buffer );
        packet.read();

        for ( int seq : packet.getPackets() ) {
            if ( this.recoveryQueue.containsKey( seq ) ) {
                DataPacket pk = this.recoveryQueue.get( seq );
                pk.sequenceNumber = this.sendSequenceNumber++;
                pk.sendTime = System.currentTimeMillis();
                this.sendPacket( pk );
                this.recoveryQueue.remove( seq );
            }
        }
    }

    //HERE Nach Success 9 sollte er 10
    public void receivePacket( EncapsulatedPacket packet ) {
        if ( packet.messageIndex == -1 ) {
            System.out.println( "HandlePacket" );
            this.handlePacket( packet );
        } else {
            System.out.println( "Success -> " + packet.messageIndex );
            if ( packet.messageIndex < this.reliableWindowStart || packet.messageIndex > this.reliableWindowEnd ) {
                return;
            }

            if ( packet.messageIndex - this.lastReliableIndex == 1 ) {
                this.lastReliableIndex++;
                this.reliableWindowStart++;
                this.reliableWindowEnd++;
                this.handlePacket( packet );

                if ( this.receivedWindow.size() > 0 ) {
                    ArrayList<Map.Entry<Integer, EncapsulatedPacket>> windows = new ArrayList<>( this.reliableWindow.entrySet() );
                    Map<Integer, EncapsulatedPacket> reliableWindow = new LinkedHashMap<>();
                    windows.sort( Comparator.comparingInt( Map.Entry::getKey ) );

                    for ( Map.Entry<Integer, EncapsulatedPacket> entry : windows ) {
                        reliableWindow.put( entry.getKey(), entry.getValue() );
                    }

                    this.reliableWindow = reliableWindow;

                    for ( Map.Entry<Integer, EncapsulatedPacket> entry : this.reliableWindow.entrySet() ) {
                        Integer seqIndex = entry.getKey();
                        if ( seqIndex - this.lastReliableIndex != 1 ) {
                            break;
                        }
                        this.lastReliableIndex++;
                        this.reliableWindowStart++;
                        this.reliableWindowEnd++;
                        this.handlePacket( entry.getValue() );

                        this.reliableWindow.remove( seqIndex );
                    }
                }
            } else {
                this.reliableWindow.put( packet.messageIndex, packet );
            }
        }
    }

    //Here
    private void handleSplit( EncapsulatedPacket packet ) {
        System.out.println( "HandleSplit" );
        if ( this.splitPackets.containsKey( packet.splitID ) ) {
            Map<Integer, EncapsulatedPacket> packetMap = this.splitPackets.get( packet.splitID );
            packetMap.put( packet.splitIndex, packet );
            this.splitPackets.put( packet.splitID, packetMap );
        } else {
            Map<Integer, EncapsulatedPacket> map = new LinkedHashMap<>();
            map.put( packet.splitIndex, packet );
            this.splitPackets.put( packet.splitID, map );
        }

        Map<Integer, EncapsulatedPacket> localSplits = this.splitPackets.get( packet.splitID );
        if ( localSplits.size() == packet.splitCount ) {
            EncapsulatedPacket pk = new EncapsulatedPacket();
            ByteBuf buffer = Unpooled.buffer( localSplits.values().size() );
            for ( EncapsulatedPacket value : localSplits.values() ) {
                buffer.writeBytes( value.buffer );
            }
            this.splitPackets.remove( packet.splitID );

            pk.buffer = buffer;
            this.receivePacket( pk );
        }
    }

    private void addToQueue( EncapsulatedPacket encapsulatedPacket, int priority ) {
        if ( priority == Priority.IMMEDIATE ) {
            DataPacket packet = new DataPacket();
            packet.sequenceNumber = this.sendSequenceNumber++;
            packet.getPackets().add( encapsulatedPacket.toBinary() );
            this.sendPacket( packet );
            packet.sendTime = System.currentTimeMillis();
            this.recoveryQueue.put( packet.sequenceNumber, packet );
            return;
        }

        int length = this.sendQueue.length();
        if ( length + encapsulatedPacket.getTotalLength() > this.mtuSize ) {
            this.sendQueue();
        }
        this.sendQueue.getPackets().add( encapsulatedPacket.toBinary() );
    }

    private void handleConnectionRequest( ByteBuf buffer ) {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.fill( buffer );
        connectionRequest.read();

        ConnectionRequestAccepted connectionRequestAccepted = new ConnectionRequestAccepted();
        connectionRequestAccepted.setAddress( this.address );
        connectionRequestAccepted.setRequestTimestamp( connectionRequest.getRequestTimestamp() );
        connectionRequestAccepted.setAcceptedTimestamp( System.currentTimeMillis() );
        connectionRequestAccepted.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = Reliability.UNRELIABLE;
        encapsulatedPacket.buffer = connectionRequestAccepted.getBuffer();

        this.addToQueue( encapsulatedPacket, Priority.IMMEDIATE );
    }

    private void handleConnectedPing( ByteBuf buffer ) {
        ConnectedPing connectedPing = new ConnectedPing();
        connectedPing.fill( buffer );
        connectedPing.read();

        ConnectedPong connectedPong = new ConnectedPong();
        connectedPong.setClientTimestamp( connectedPing.getClientTimestamp() );
        connectedPong.setServerTimestamp( System.currentTimeMillis() );
        connectedPong.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = Reliability.UNRELIABLE;
        encapsulatedPacket.buffer = connectedPong.getBuffer();
        this.addToQueue( encapsulatedPacket, Priority.IMMEDIATE );
    }

    public void addEncapsulatedToQueue( EncapsulatedPacket packet, int priority ) {
        if ( packet.reliability == Reliability.UNRELIABLE
                || packet.reliability == Reliability.RELIABLE_ORDERED
                || packet.reliability == Reliability.RELIABLE_SEQUENCED
                || packet.reliability == Reliability.RELIABLE_WITH_ACK_RECEIPT
                || packet.reliability == Reliability.RELIABLE_ORDERED_WITH_ACK_RECEIPT ) {
            packet.messageIndex = this.messageIndex++;
            if ( packet.reliability == Reliability.RELIABLE_ORDERED ) {
                int index = this.channelIndex.get( packet.orderIndex );
                packet.orderIndex = index + 1;
            }
        }

        if ( packet.getTotalLength() + 4 > this.mtuSize ) {
            byte[][] buffers = BinaryStream.splitBytes( packet.buffer.array(), this.mtuSize - 60 ); //Maybe -60
            short splitID = (short) ( ++this.splitID % 65536 );

            for ( int count = 0; count < buffers.length; count++ ) {
                byte[] buffer = buffers[count];
                EncapsulatedPacket pk = new EncapsulatedPacket();
                pk.splitID = splitID;
                pk.split = true;
                pk.splitCount = buffers.length;
                pk.reliability = packet.reliability;
                pk.splitCount = count;
                pk.buffer = Unpooled.wrappedBuffer( buffer );

                if ( count > 0 ) {
                    pk.messageIndex = this.messageIndex++;
                } else {
                    pk.messageIndex = packet.messageIndex;
                }

                if ( pk.reliability == Reliability.RELIABLE_ORDERED ) {
                    pk.orderChannel = packet.orderChannel;
                    pk.orderIndex = packet.orderIndex;
                }

                this.addToQueue( pk, priority | Priority.IMMEDIATE );
            }
        } else {
            this.addToQueue( packet, priority );
        }
    }

    private void sendQueue() {
        if ( this.sendQueue.getPackets().size() > 0 ) {
            this.sendQueue.sequenceNumber = this.sendSequenceNumber++;
            this.sendPacket( this.sendQueue );
            this.sendQueue.sendTime = System.currentTimeMillis();
            this.recoveryQueue.put( this.sendQueue.sequenceNumber, this.sendQueue );
            this.sendQueue = new DataPacket();
            System.out.println( "SendQue" );
        }
    }

    public void sendPacket( Packet packet, boolean write ) {
        if ( write ) {
            packet.write();
        }
        this.sendPacket( packet );
    }

    public void sendPacket( Packet packet ) {
        packet.write();
        this.listener.sendBuffer( packet.getBuffer(), this.address ); //Maybe wrong
    }

    public void disconnect( String reason ) {
        if ( reason == null ) {
            reason = "Unknown";
        }
        this.listener.removeConnection( this, reason );
    }

    void close() {
        ByteBuf buffer = Unpooled.buffer( 4 );
        buffer.writeBytes( new byte[]{ 0x00, 0x00, 0x08, 0x15 } );
        EncapsulatedPacket packet = EncapsulatedPacket.fromBinary( buffer );
        if ( packet != null ) {
            this.addEncapsulatedToQueue( packet, Priority.IMMEDIATE );
        }
    }

    public static class Priority {
        public static final int NORMAL = 0;
        public static final int IMMEDIATE = 1;
    }

    public enum Status {
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }
}
