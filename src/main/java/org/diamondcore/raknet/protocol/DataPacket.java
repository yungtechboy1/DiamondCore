package org.jukeboxmc.raknet.protocol;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class DataPacket extends Packet {

    private List<Object> packets = new ArrayList<>();

    public int sequenceNumber;
    public long sendTime = System.currentTimeMillis();

    public DataPacket() {
        super( BitFlags.VALID );
    }

    @Override
    public void read() {
        super.read();
        this.sequenceNumber = this.getBuffer().readUnsignedMediumLE();

        while ( !this.feof() ) {
            this.packets.add( EncapsulatedPacket.fromBinary( this.buffer ) );
        }
    }

    @Override
    public void write() {
        super.write();
        this.writeLTriad( this.sequenceNumber );

        for ( Object object : this.packets ) {
            if ( object instanceof EncapsulatedPacket ) {
                EncapsulatedPacket encapsulatedPacket = (EncapsulatedPacket) object;
                this.getBuffer().writeBytes( encapsulatedPacket.toBinary() );
            } else if ( object instanceof byte[] ) {
                byte[] buffer = (byte[]) object;
                this.getBuffer().writeBytes( buffer );
            } else {
                System.out.println( "ELSE -> " + object.getClass().getSimpleName() );
            }
        }
    }

    public int length() {
        return this.getBuffer().duplicate().asReadOnly().readerIndex( 0 ).readableBytes();
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "packets=" + packets.size() +
                ", sequenceNumber=" + sequenceNumber +
                ", packetId=" + packetId +
                ", sendTime=" + sendTime +
                '}';
    }
}
