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
public class OpenConnectionReply2 extends Packet {

    private long serverGUID;
    private InetSocketAddress address;
    private short mtu;
    private boolean encryption = false;

    public OpenConnectionReply2( ) {
        super( Identifiers.OpenConnectionReply2 );
    }

    @Override
    public void read() {
        super.read();
        this.serverGUID = this.readLong();
        this.address = this.readAddress();
        this.mtu = this.readShort();
        this.encryption = this.readBoolean();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.writeLong( this.serverGUID );
        this.writeAddress( this.address );
        this.writeShort( this.mtu );
        this.writeBoolean( this.encryption );
    }
}
