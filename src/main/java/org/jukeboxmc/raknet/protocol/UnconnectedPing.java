package org.jukeboxmc.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.raknet.utils.BinaryStream;
import org.jukeboxmc.raknet.utils.Identifiers;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class UnconnectedPing extends Packet {

    private long time;
    private long clientGUID;

    public UnconnectedPing() {
        super( Identifiers.UnconnectedPing );
    }

    @Override
    public void read() {
        super.read();
        this.time = this.readLong();
        this.clientGUID = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeLong( this.time );
        this.writeMagic();
        this.writeLong( this.clientGUID );
    }
}
