package net.trenterprises.diamondcore.desktop;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.trenterprises.diamondcore.cross.borrowed.Varint;
import net.trenterprises.diamondcore.cross.file.FileUtils;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

public class TestPing {
	
	public static void main(String[] args) throws IOException {
		String json = FileUtils.readFromFile(new File("/Users/Trent/Desktop/json.txt")).replace("<MOTHER>", derp(new File("/Users/Trent/Desktop/favicon.png")));
		ServerSocket ss = new ServerSocket(25565);
		Socket s;
		BinaryReader reader;
		DataInputStream input;
		while(true) {
			try {
				ss.setSoTimeout(1000);
				s = ss.accept();
				reader = new BinaryReader(s.getInputStream());
				input = new DataInputStream(s.getInputStream());
				
				System.out.println(input.available());
				System.out.println("Packet length?: " + reader.readByte());
				System.out.println("PacketID: " + Varint.readUnsignedVarInt(new byte[] { reader.readByte() }));
				System.out.println("Protocol: " + Varint.readUnsignedVarInt(new byte[] { reader.readByte() }));
				
				int length = Varint.readUnsignedVarInt(new byte[] { reader.readByte() });
				byte[] rawString = new byte[length];
				for(int i = 0; i < length; i++) rawString[i] = reader.readByte();
				String address = new String(rawString);
				System.out.println("Server address: " + address);
				
				System.out.println("Server port: " + input.readUnsignedShort());
				System.out.println("Next state: " + Varint.readUnsignedVarInt(new byte[] { reader.readByte() }));
				
				DataOutputStream output = new DataOutputStream(s.getOutputStream());
				BinaryWriter writer = new BinaryWriter(output);
				writer.write(Varint.writeUnsignedVarInt(3 + json.getBytes().length));
				writer.writeByte((byte) 0x00);
				writer.write(Varint.writeUnsignedVarInt(json.getBytes().length));
				writer.write(json.getBytes());
				
				s.shutdownInput();
				s.shutdownOutput();
				reader.close();
				writer.flush();
				writer.close();
				input.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String derp(File file) throws IOException{
	    return Base64.encodeToString(read(file));
	}
	
	public static byte[] read(File file) throws IOException {
	    ByteArrayOutputStream ous = null;
	    InputStream ios = null;
	    try {
	        byte[] buffer = new byte[4096];
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ( (read = ios.read(buffer)) != -1 ) {
	            ous.write(buffer, 0, read);
	        }
	    } finally { 
	        try {
	             if ( ous != null ) 
	                 ous.close();
	        } catch ( IOException e) {
	        }

	        try {
	             if ( ios != null ) 
	                  ios.close();
	        } catch ( IOException e) {
	        }
	    }
	    return ous.toByteArray();
	}
	
}
