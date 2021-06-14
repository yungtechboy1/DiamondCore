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
public class IncompatibleProtocolVersion extends Packet {

    private byte protocol;
    private long serverGUID;

    public IncompatibleProtocolVersion() {
        super( Identifiers.IncompatibleProtocolVersion );
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.protocol );
        this.writeMagic();
        this.writeLong( this.serverGUID );
    }
}
