package org.jukeboxmc.raknet.protocol;

import lombok.NoArgsConstructor;
import org.jukeboxmc.raknet.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@NoArgsConstructor
public abstract class Packet extends BinaryStream {

    public byte packetId;

    public Packet( byte packetId ) {
        this.packetId = packetId;
    }

    public void read() {
        this.packetId = this.readByte();
    }

    public void write() {
        this.writeByte( this.packetId );
    }

}
