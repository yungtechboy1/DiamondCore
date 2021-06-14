package org.jukeboxmc.raknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import org.jukeboxmc.raknet.protocol.*;
import org.jukeboxmc.raknet.utils.BinaryStream;
import org.jukeboxmc.raknet.utils.Identifiers;
import org.jukeboxmc.raknet.utils.ServerName;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Listener {

    private Bootstrap socket;
    private NioEventLoopGroup group;
    private Channel channel;

    private ServerName serverName;

    public long serverId;

    @Getter
    private InetSocketAddress address;

    private boolean isRunning = false;

    private Map<String, Connection> connections = new HashMap<>();

    public Listener() {
        this.serverName = new ServerName( this );
        this.serverId = UUID.randomUUID().getMostSignificantBits();
    }

    public boolean listen( String address, int port ) {
        this.address = new InetSocketAddress( address, port );
        this.socket = new Bootstrap();
        this.socket.group( this.group = new NioEventLoopGroup() );
        this.socket.channel( Epoll.isAvailable() ? EpollDatagramChannel.class : NioDatagramChannel.class );
        this.socket.handler( new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead( ChannelHandlerContext ctx, Object msg ) {
                DatagramPacket packet = (DatagramPacket) msg;
                InetSocketAddress sender = packet.sender();
                ByteBuf content = packet.content();
                handlePackets( content, sender, ctx );
                content.release();
            }
        } );

        try {
            this.channel = this.socket.bind( address, port ).sync().channel();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        this.isRunning = true;
        this.tick();
        return this.isRunning;
    }

    private void handlePackets( ByteBuf buffer, InetSocketAddress address, ChannelHandlerContext ctx ) {
        int packetId = buffer.getUnsignedByte( 0 );
        if ( packetId == Identifiers.Query ) {
            return;
        }
        String token = address.getHostName() + ":" + address.getPort();
        if ( this.connections.containsKey( token ) ) {
            Connection connection = this.connections.get( token );
            connection.receive( buffer );

        } else {
            switch ( packetId ) {
                case Identifiers.UnconnectedPing:
                    this.handleUnconnectedPing( buffer, address, ctx );
                    break;
                case Identifiers.OpenConnectionRequest1:
                    this.handleOpenConnectionRequest1( buffer, address, ctx );
                    break;
                case Identifiers.OpenConnectionRequest2:
                    this.handleOpenConnectionRequest2( buffer, address, ctx );
                    break;
                default:
                    break;
            }
        }
    }

    private void handleUnconnectedPing( ByteBuf buffer, InetSocketAddress address, ChannelHandlerContext ctx ) {
        UnconnectedPing decodedPacket = new UnconnectedPing();
        decodedPacket.fill( buffer );
        decodedPacket.read();

        UnconnectedPong packet = new UnconnectedPong();
        packet.setTime( System.currentTimeMillis() );
        packet.setServerGUID( this.serverId );
        packet.setServerID( this.serverName.toString() );
        this.sendPacket( packet, address );
    }

    private void handleOpenConnectionRequest1( ByteBuf buffer, InetSocketAddress address, ChannelHandlerContext ctx ) {
        OpenConnectionRequest1 decodedPacket = new OpenConnectionRequest1();
        decodedPacket.fill( buffer );
        decodedPacket.read();

        if ( decodedPacket.getProtocol() != 10 ) { //Raknet protocol version
            IncompatibleProtocolVersion packet = new IncompatibleProtocolVersion();
            packet.setProtocol( (byte) Identifiers.Protocol );
            packet.setServerGUID( this.serverId );
            System.err.println( "Incorrect protocol!" );
            this.sendPacket( packet, address );
        }

        OpenConnectionReply1 packet = new OpenConnectionReply1();
        packet.setServerGUID( this.serverId );
        packet.setMtu( decodedPacket.getMtu() );
        this.sendPacket( packet, address );
    }

    private void handleOpenConnectionRequest2( ByteBuf buffer, InetSocketAddress address, ChannelHandlerContext ctx ) {
        OpenConnectionRequest2 decodedPacket = new OpenConnectionRequest2();
        decodedPacket.fill( buffer );
        decodedPacket.read();

        OpenConnectionReply2 packet = new OpenConnectionReply2();
        packet.setServerGUID( this.serverId );
        packet.setMtu( decodedPacket.getMtu() );
        packet.setAddress( address );
        this.sendPacket( packet, address );

        String token = address.getHostName() + ":" + address.getPort();
        this.connections.put( token, new Connection( this, decodedPacket.getMtu(), address ) );
    }

    private void sendPacket( Packet packet, InetSocketAddress address ) {
        if ( this.channel != null ) {
            packet.write();
            this.channel.writeAndFlush( new DatagramPacket( packet.getBuffer(), address ) );
        }
    }

    void sendBuffer( ByteBuf buffer, InetSocketAddress address ) {
        this.channel.writeAndFlush( new DatagramPacket( buffer, address ) );
    }

    void removeConnection( Connection connection, String reason ) {
        InetSocketAddress address = connection.getAddress();
        String token = address.getHostName() + ":" + address.getPort();
        if ( this.connections.containsKey( token ) ) {
            System.out.println( "Reason: " + reason );
            this.connections.get( token ).close(); //Does no work for timeout TODO
            this.connections.remove( token );
            System.out.println( "Remove > " + token );
        }
    }

    private void tick() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                if ( isRunning ) {
                    for ( Connection connection : connections.values() ) {
                        connection.update( System.currentTimeMillis() ); //TODO hier weiter machen ich glaub ab hier ist was falsch
                    }
                } else {
                    this.cancel();
                }
            }
        }, 10, 10 ); //Raknet tick
    }


    public void shutdown() {
        this.group.shutdownGracefully();

        try {
            this.channel.closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        this.isRunning = false;
    }
}
