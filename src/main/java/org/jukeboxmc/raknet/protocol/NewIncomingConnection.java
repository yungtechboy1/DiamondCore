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
public class NewIncomingConnection extends Packet {

    private InetSocketAddress address;

    private InetSocketAddress[] systemAddress = new InetSocketAddress[]{
            new InetSocketAddress( "127.0.0.1", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 )
    };

    private long requestTimestamp;
    private long acceptedTimestamp;

   public NewIncomingConnection() {
        super( Identifiers.NewIncomingConnection );
    }

    @Override
    public void read() {
        this.address = this.readAddress();

        this.requestTimestamp = this.readLong();
        this.acceptedTimestamp = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeAddress( this.address );
        for ( int i = 0; i < 10; i++ ) {
            this.writeAddress( this.systemAddress[i] );
        }
        this.writeLong( this.requestTimestamp );
        this.writeLong( this.acceptedTimestamp );
    }
}
