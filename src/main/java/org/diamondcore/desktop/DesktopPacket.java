package org.diamondcore.desktop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.diamondcore.utils.VarInt;

public class DesktopPacket {
	
	// Output
	private final byte pid;
	private final DataOutputStream out;
	private final ByteArrayOutputStream raw;
	
	// Input
	private final DataInputStream in;
	
	public DesktopPacket(byte pid) throws IOException {
		this.pid = pid;
		this.out = new DataOutputStream(this.raw = new ByteArrayOutputStream());
		this.out.write(VarInt.writeUnsignedVarInt(this.pid));
		
		this.in = null;
	}
	
	public DesktopPacket(DataInputStream in) throws IOException {
		this.in = in;
		
		this.pid = -1;//(byte) VarInt.readUnsignedVarInt(in.readByte());
		this.out = null;
		this.raw = null;
	}
	
	public DesktopPacket(byte[] b) throws IOException {
		this(new DataInputStream(new ByteArrayInputStream(b)));
	}
	
	public void write(byte[] b) throws IOException {
		out.write(b);
	}
	
	public void writeByteArray(byte[] b) throws IOException {
		VarInt.writeUnsignedVarInt(b.length, out);
		out.write(b);
	}
	
	public byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		for(int i = 0; i < len; i++)
			b[i] = in.readByte();
		return b;
	}
	
	public byte[] readByteArray() throws IOException {
		int len = VarInt.readUnsignedVarInt(in);
		return read(len);
	}
	
	public void writeByte(byte b) throws IOException {
		out.writeByte((byte) b);
	}
	
	public byte read() throws IOException {
		return in.readByte();
	}
	
	public void writeUByte(byte b) throws IOException {
		out.writeByte(((byte) b) & 0xFF);
	}
	
	public byte readU() throws IOException {
		return (byte) in.readUnsignedByte();
	}
	
	public void writeBoolean(boolean b) throws IOException {
		out.writeByte(b ? 0x01 : 0x00);
	}
	
	public boolean readBoolean() throws IOException {
		return (in.readByte() == (byte) 0x01);
	}
	
	public void writeShort(short s) throws IOException {
		out.writeShort(s);
	}
	
	public short readShort() throws IOException {
		return in.readShort();
	}
	
	public void writeUShort(short s) throws IOException {
		out.writeChar((char) s);
	}
	
	public short readUShort() throws IOException {
		return (short) in.readUnsignedShort();
	}
	
	public void writeInt(int i) throws IOException {
		out.writeInt(i);
	}
	
	public int readInt() throws IOException {
		return in.readInt();
	}
	
	public void writeLong(long l) throws IOException {
		out.writeLong(l);
	}
	
	public long readLong() throws IOException {
		return in.readLong();
	}
	
	public void writeFloat(float f) throws IOException {
		out.writeFloat(f);
	}
	
	public float readFloat() throws IOException {
		return in.readFloat();
	}
	
	public void writeDouble(double d) throws IOException {
		out.writeDouble(d);
	}
	
	public double readDouble() throws IOException {
		return in.readDouble();
	}
	
	public void writeString(String s) throws IOException {
		byte[] b = s.getBytes();
		VarInt.writeUnsignedVarInt(b.length, out);
		out.write(b);
	}
	
	public String readString() throws IOException {
		int len = VarInt.readUnsignedVarInt(in);
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < len; i++)
			b.append((char) in.read());
		return b.toString();
	}
	
	public void writeVarInt(int i) throws IOException {
		VarInt.writeUnsignedVarInt(i, out);
	}
	
	public int readVarInt() throws IOException {
		return VarInt.readUnsignedVarInt(in);
	}
	
	public void writeVarLong(long l) throws IOException {
		VarInt.writeUnsignedVarLong(l, out);
	}
	
	public long readVarLong() throws IOException {
		return VarInt.readUnsignedVarLong(in);
	}
	
	public void writeUUID(UUID u) throws IOException {
		out.writeLong(u.getMostSignificantBits());
		out.writeLong(u.getLeastSignificantBits());
	}
	
	public UUID readUUID() throws IOException {
		long high = in.readLong();
		long low = in.readLong();
		return new UUID(high, low);
	}
	
	public byte readPacketID() {
		return pid;
	}
	
	public byte[] toByteArray() throws IOException {
		ByteArrayOutputStream packet = new ByteArrayOutputStream();
		packet.write(VarInt.writeUnsignedVarInt(raw.size()));
		packet.write(raw.toByteArray());
		
		return packet.toByteArray();
	}
	
}
