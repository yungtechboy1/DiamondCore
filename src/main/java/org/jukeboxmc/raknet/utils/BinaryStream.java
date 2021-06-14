package org.jukeboxmc.raknet.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BinaryStream {

    public ByteBuf buffer;

    public BinaryStream() {
        this.buffer = Unpooled.buffer(0);
        this.buffer.retain();
    }

    public BinaryStream( ByteBuf buffer ) {
        this.buffer = buffer;
        this.buffer.retain();
    }

    public void fill( ByteBuf buffer ) {
        this.buffer = buffer;
    }

    public final int readAvailable() {
        return this.buffer.readableBytes();
    }

    public final int written() {
        return this.buffer.writerIndex();
    }

    public void setReadPosition( int position ) {
        this.buffer.readerIndex( position );
    }

    public ByteBuf readSlice( int length ) {
        return this.buffer.retainedSlice( this.buffer.readerIndex(), length );
    }

    public byte readByte() {
        return this.buffer.readByte();
    }

    public int readUnsignedByte() {
        return this.buffer.readUnsignedByte();
    }

    public void writeByte( int value ) {
        this.buffer.writeByte( value );
    }

    public void writeBytes( byte[] value ) {
        this.buffer.writeBytes( value );
    }

    public void writeBytes( ByteBuf value ) {
        this.buffer.writeBytes( value );
    }

    public void expectByte( int val ) {
        int readedByte = this.readByte();
        if ( readedByte != val ) throw new RuntimeException();
    }

    public long readLong() {
        return this.buffer.readLong();
    }

    public void expectedLong( long expected ) {
        long got = this.readLong();
        if ( expected != got ) throw new RuntimeException();
    }

    public void writeLong( long value ) {
        this.buffer.writeLong( value );
    }

    public void readMagic() {
        this.expectedLong( 0x00ffff00fefefefeL );
        this.expectedLong( 0xfdfdfdfd12345678L );
    }

    public void writeMagic() {
        this.writeLong( 0x00ffff00fefefefeL );
        this.writeLong( 0xfdfdfdfd12345678L );
    }

    public int readUnsignedShort() {
        return this.buffer.readUnsignedShort();
    }

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;

        while ( ( ( b = this.readByte() ) & 0x80 ) != 0 ) {
            value |= ( b & 0x7F ) << i;
            i += 7;
            if ( i > 35 ) {
                throw new RuntimeException( "VarInt too big" );
            }
        }

        return value | ( b << i );
    }

    public void writeUnsignedVarInt( int value ) {
        while ( ( value & 0xFFFFFF80 ) != 0L ) {
            this.writeByte( (byte) ( ( value & 0x7F ) | 0x80 ) );
            value >>>= 7;
        }

        this.writeByte( (byte) ( value & 0x7F ) );
    }

    public String readString() {
        short length = this.readShort();
        byte[] bytes = new byte[length];
        this.buffer.readBytes( buffer );
        return new String( bytes );
    }

    public void writeString( String value ) {
        this.buffer.writeShort( value.length() );
        this.buffer.writeBytes( value.getBytes() );
    }

    @SneakyThrows
    public InetSocketAddress readAddress() {
        int version = this.readUnsignedByte();
        int size;
        if ( version == 4 ) {
            size = 4;
        } else if ( version == 6 ) {
            size = 16;
        } else {
            throw new RuntimeException("Version: " + version);
        }

        byte[] raw = new byte[size];
        for ( int i = 0; i < size; i++ ) {
            raw[i] = (byte) this.readByte();
        }
        InetAddress addr = InetAddress.getByAddress( raw );

        int port = this.readUnsignedShort();
        return new InetSocketAddress( addr, port );
    }

    public void writeAddress( InetSocketAddress address ) {
        byte[] bytes = address.getAddress().getAddress();
        this.writeByte( bytes.length == 4 ? 4 : 6 );
        for ( byte b : bytes ) this.writeByte( b & 0xFF );
        this.writeShort( (short) address.getPort() );
    }

    public boolean readBoolean() {
        return this.readByte() != 0;
    }

    public void writeBoolean( boolean value ) {
        this.writeByte( value ? 1 : 0 );
    }

    public short readShort() {
        return this.buffer.readShort();
    }

    public void writeShort( short value ) {
        this.buffer.writeShort( value );
    }

    // aka uint24le
    public int readTriad() {
        return this.buffer.readMedium();
    }

    public void writeTriad( int value ) {
        this.buffer.writeMedium( value );
    }

    public int readInt() {
        return this.buffer.readInt();
    }

    public void writeInt( int value ) {
        this.buffer.writeInt( value );
    }

    public int readLInt() {
        return this.buffer.readIntLE();
    }

    public void writeLInt( int value ) {
        this.buffer.writeIntLE( value );
    }

    public int readLTriad() {
        return this.buffer.readMediumLE();
    }

    public void writeLTriad( int value ) {
        this.buffer.writeMediumLE( value );
    }

    public void appendBytes( byte[] bytes ) {
        this.buffer = Unpooled.wrappedBuffer( appendBytes( this.buffer.array(), bytes ) );
    }

    public static byte[] subBytes( byte[] bytes, int start, int length ) {
        int len = Math.min( bytes.length, start + length );
        return Arrays.copyOfRange( bytes, start, len );
    }

    public static byte[] subBytes( byte[] bytes, int start ) {
        return subBytes( bytes, start, bytes.length - start );
    }

    public static byte[][] splitBytes( byte[] bytes, int chunkSize ) {
        int length = ( bytes.length + chunkSize - 1 ) / chunkSize;
        byte[][] splits = new byte[length][chunkSize];
        int chunks = 0;

        for ( int i = 0; i < bytes.length; i += chunkSize ) {
            if ( ( bytes.length - i ) > chunkSize ) {
                splits[chunks] = Arrays.copyOfRange( bytes, i, i + chunkSize );
            } else {
                splits[chunks] = Arrays.copyOfRange( bytes, i, bytes.length );
            }
            chunks++;
        }

        return splits;
    }

    public static byte[] appendBytes( byte[] bytes1, byte[]... bytes2 ) {
        int length = bytes1.length;
        for ( byte[] bytes : bytes2 ) {
            length += bytes.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate( length );
        buffer.put( bytes1 );
        for ( byte[] bytes : bytes2 ) {
            buffer.put( bytes );
        }
        return buffer.array();
    }

    public static byte[] addToArray( byte[] bytes1, byte bytes2 ) {
        int length = bytes1.length + 1;
        ByteBuffer buffer = ByteBuffer.allocate( length );
        buffer.put( bytes1 );
        buffer.put( bytes2 );
        return buffer.array();
    }

    public ByteBuf bufferToByteBuf( byte[] buffer ) {
        return Unpooled.wrappedBuffer( buffer );
    }

    public byte[] toByteBuffer() {
        return this.buffer.array();
    }

    public int consumePadding() {
        int value = this.readAvailable();
        while ( this.readAvailable() > 0 ) {
            this.expectByte( 0 );
        }
        return value;
    }

    public int getWrittenOffset() {
        return this.written();
    }

    public int getReadOffset() {
        return this.buffer.readerIndex();
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    protected boolean isReadable() {
        return this.buffer.isReadable();
    }

    protected boolean feof() {
        ByteBuf byteBuf = buffer.duplicate().readerIndex( 0 );
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return !( this.getReadOffset() >= 0 && this.getReadOffset() + 1 <= bytes.length);
    }

}
