package org.jukeboxmc.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.raknet.utils.Identifiers;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class ConnectionRequest extends Packet {

    private long clientGUID;
    private long requestTimestamp;
    private boolean security;

    public ConnectionRequest( ) {
        super( Identifiers.ConnectionRequest );
    }

    @Override
    public void read() {
        this.clientGUID = this.readLong();
        this.requestTimestamp = this.readLong();
        this.security = this.readBoolean(); //Secure
    }

    @Override
    public void write() {
        super.write();
        this.writeLong( this.clientGUID );
        this.writeLong( this.requestTimestamp );
        this.writeBoolean( false );
    }
}
