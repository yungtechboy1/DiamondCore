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
public class ConnectedPing extends Packet {

    private long clientTimestamp;

    public ConnectedPing() {
        super( Identifiers.ConnectedPing );
    }

    @Override
    public void read() {
        this.clientTimestamp = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeLong( this.clientTimestamp );
    }
}
