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
public class UnconnectedPong extends Packet {

    private long time;
    private long serverGUID;
    private String serverID;

    public UnconnectedPong() {
        super( Identifiers.UnconnectedPong );
    }

    @Override
    public void write() {
        super.write();
        this.writeLong( this.time );
        this.writeLong( this.serverGUID );
        this.writeMagic();
        this.writeString( this.serverID );
    }
}
