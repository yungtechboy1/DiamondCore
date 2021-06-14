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
public class OpenConnectionRequest2 extends Packet {

    private InetSocketAddress address;
    private short mtu;
    private long clientGUID;

    public OpenConnectionRequest2() {
        super( Identifiers.OpenConnectionRequest2 );
    }

    @Override
    public void read() {
        super.read();
        this.readMagic();
        this.address = this.readAddress();
        this.mtu = this.readShort();
        this.clientGUID = this.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.writeAddress( this.address );
        this.writeShort( this.mtu );
        this.writeLong( this.clientGUID );
    }
}
