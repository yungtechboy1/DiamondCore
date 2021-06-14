package org.jukeboxmc.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.raknet.utils.Identifiers;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class ConnectionRequestAccepted extends Packet {

    private InetSocketAddress address;
    private InetSocketAddress[] systemAddress = new InetSocketAddress[]{
            new InetSocketAddress( "127.0.0.1", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 )
    };
    private long requestTimestamp; //Ping
    private long acceptedTimestamp; //Pong

    public ConnectionRequestAccepted() {
        super( Identifiers.ConnectionRequestAccepted );
    }

    @Override
    public void read() {
        super.read();
        this.address = this.readAddress();
        this.readShort();
        for ( int i = 0; i < 10; i++ ) {
            this.systemAddress[i] = this.readAddress();
        }
        this.requestTimestamp = this.readLong();
        this.acceptedTimestamp = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeAddress( this.address );
        this.writeShort( (short) 0 );
        for ( int i = 0; i < 10; i++ ) {
            this.writeAddress( this.systemAddress[i] );
        }
        this.writeLong( this.requestTimestamp );
        this.writeLong( this.acceptedTimestamp );
    }
}
