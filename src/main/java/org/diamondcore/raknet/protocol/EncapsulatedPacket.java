package org.jukeboxmc.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.ToString;
import org.jukeboxmc.raknet.utils.BinaryStream;
import org.jukeboxmc.raknet.utils.Reliability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString ( exclude = "buffer" )
public class EncapsulatedPacket {

    public ByteBuf buffer;

    public Reliability reliability;

    public int messageIndex = -1;
    public int sequenceIndex = -1;
    public int orderIndex = -1;
    public int orderChannel = -1;

    public boolean split = false;

    public int splitCount = -1;
    public int splitIndex = -1;
    public int splitID = -1;

    public boolean needACK = false;
    public int identifierACK = -1;

    public static EncapsulatedPacket fromBinary( ByteBuf buffer ) {
        EncapsulatedPacket packet = new EncapsulatedPacket();

        int flags = buffer.readUnsignedByte();
        int value = ( flags & 0b11100000 ) >> 5;
        packet.reliability = Reliability.fromId( value );
        packet.split = ( flags & 0b00010000 ) != 0;
        int size = ( buffer.readUnsignedShort() + 7 ) >> 3;

        if ( packet.reliability == null ) {
            System.out.println( "NULL" );
            return null;
        }

        if ( packet.reliability.isReliable() ) {
            packet.messageIndex = buffer.readUnsignedMediumLE();
        }

        if ( packet.reliability.isSequenced() ) {
            packet.sequenceIndex = buffer.readUnsignedMediumLE();
        }

        if ( packet.reliability.isOrdered() || packet.reliability.isSequenced() ) {
            packet.orderIndex = buffer.readUnsignedMediumLE();
            packet.orderChannel = buffer.readUnsignedByte();
        }

        if ( packet.split ) {
            packet.splitCount = buffer.readInt();
            packet.splitID = buffer.readUnsignedShort();
            packet.splitIndex = buffer.readInt();
        }

        packet.buffer = buffer.readSlice( size );
        System.out.println( packet.toString() );
        return packet;
    }

    public byte[] toBinary() {
        BinaryStream stream = new BinaryStream();
        int flags = this.reliability.ordinal() << 5;
        if ( split ) {
            flags |= 0b00010000;
        }
        stream.writeByte( flags ); // flags
        stream.writeShort( (short) ( buffer.readableBytes() << 3 ) ); // size

        if ( reliability.isReliable() ) {
            stream.writeLTriad( this.messageIndex );
        }

        if ( reliability.isSequenced() ) {
            stream.writeLTriad( this.sequenceIndex );
        }

        if ( reliability.isOrdered() || reliability.isSequenced() ) {
            stream.writeLTriad( orderIndex );
            stream.writeByte( orderChannel );
        }

        if ( split ) {
            stream.writeInt( splitCount );
            stream.writeShort( (short) splitID );
            stream.writeInt( splitIndex );
        }

        stream.getBuffer().writeBytes( this.buffer, this.buffer.readerIndex(), this.buffer.readableBytes() );
        return stream.toByteBuffer();
    }

    public int getTotalLength() {
        return 3 + this.reliability.getSize() + ( this.split ? 10 : 0 ) + this.buffer.readableBytes();
    }
}
