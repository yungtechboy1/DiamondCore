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
public class ConnectedPong extends Packet {

    private long clientTimestamp;
    private long serverTimestamp;

    public ConnectedPong() {
        super( Identifiers.ConnectedPong );
    }

    @Override
    public void read() {
        super.read();
        this.clientTimestamp = this.readLong();
        this.serverTimestamp = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeLong( this.clientTimestamp );
        this.writeLong( this.serverTimestamp );
    }
}
