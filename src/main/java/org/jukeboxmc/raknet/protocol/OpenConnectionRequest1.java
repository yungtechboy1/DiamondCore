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
public class OpenConnectionRequest1 extends Packet {

    private byte protocol = 10;
    private short mtu;

    public OpenConnectionRequest1() {
        super( Identifiers.OpenConnectionRequest1 );
    }

    @Override
    public void read() {
        super.read();
        this.readMagic();
        this.protocol = this.readByte();
        this.mtu = (short) this.consumePadding();
    }

}
