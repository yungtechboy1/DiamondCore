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
public class OpenConnectionReply1 extends Packet {

    private long serverGUID;
    private InetSocketAddress address;
    private boolean security;
    private short mtu;

    public OpenConnectionReply1() {
        super( Identifiers.OpenConnectionReply1 );
    }

    @Override
    public void read() {
        this.serverGUID = this.readLong();
        this.security = this.readBoolean();
        this.mtu = this.readShort();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.writeLong( this.serverGUID );
        this.writeBoolean( false );
        this.writeShort( this.mtu );
    }
}
