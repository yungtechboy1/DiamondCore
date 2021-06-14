package org.jukeboxmc.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.raknet.utils.BinaryStream;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.00
 */

@Getter
@Setter
@ToString
public class AcknowledgePacket extends Packet {

    private List<Integer> packets = new LinkedList<>();

    public AcknowledgePacket( byte packetId ) {
        super( packetId );
    }

    @Override
    public void read() {
        this.packets.clear();

        short recordCount = this.readShort();

        for ( int i = 0; i < recordCount; i++ ) {
            int recordType = this.readByte();

            if ( recordType == 0 ) {
                int start = this.readLTriad();
                int end = this.readLTriad();

                for ( int pack = start; pack <= end; pack++ ) {
                    this.packets.add( pack );
                    if ( this.packets.size() > 4096 ) {
                        throw new IndexOutOfBoundsException( "Maximum acknowledgement packets size exceeded" );
                    }
                }
            } else {
                int packet = this.readLTriad();
                this.packets.add( packet );
            }
        }
    }

    @Override
    public void write() {
        super.write();

        short records = 0;
        BinaryStream stream = new BinaryStream();
        this.packets.sort( Collections.reverseOrder() );
        int count = this.packets.size();

        if ( count > 0 ) {
            int pointer = 1;
            int start = this.packets.get( 0 );
            int last = this.packets.get( 0 );

            while ( pointer < count ) {
                int current = this.packets.get( pointer++ );
                int diff = current - last;

                if ( diff == 1 ) {
                    last = current;
                } else if ( diff > 1 ) {
                    if ( start == last ) {
                        stream.writeBoolean( true );
                        stream.writeLTriad( start );
                        start = last = current;
                    } else {
                        stream.writeBoolean( false );
                        stream.writeLTriad( start );
                        stream.writeLTriad( last );
                        start = last = current;
                    }
                    records++;
                }
            }

            if ( start == last ) {
                stream.writeBoolean( true );
                stream.writeLTriad( start );
            } else {
                stream.writeBoolean( false );
                stream.writeLTriad( start );
                stream.writeLTriad( last );
            }
            records+=1;
        }
        this.writeShort( records );
        this.writeBytes( stream.getBuffer() );
    }
}
