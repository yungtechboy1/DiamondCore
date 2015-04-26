/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package net.trenterprises.diamondcore.desktop.network.handshake;

import io.netty.handler.codec.base64.Base64Decoder;

import java.awt.GridBagLayout;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
import net.trenterprises.diamondcore.cross.utils.ImageUtils;
import net.trenterprises.diamondcore.desktop.network.utils.PacketUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Used to handle a client login packet
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class LoginResponseTest extends HandshakePacket {
	
	// Socket info
	protected Socket s;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	// Packet info
	protected String username;
	
	public LoginResponseTest(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Decode packet
		this.decode();
		if(!Diamond.getOnlinePlayers().contains(this.username)) Diamond.getOnlinePlayers().add(this.username);
	}
	
	/**
	 * Used to retrieve the username of the player joining
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Username of player trying to join
	 * @throws IOException
	 */
	public String getPlayerName() {
		return this.username;
	}

	@Override
	public void decode() throws IOException {
		input.read(new byte[2]); // Read two unneeded bytes sent by the client
		this.username = PacketUtils.readString(this.input);
	}

	@Override
	public void sendResponse() throws IOException {
		long ServerID = new Random().nextLong();
		String token1 = "fo97f98g70fff0r8re0g7dsf9g7";
		String token2 = "cs88f7sf7as9g8ga0a9f8ad0fds";
		output.write(VarInt.writeUnsignedVarInt(5 + "F1".getBytes().length + token1.getBytes().length + token2.getBytes().length));
		output.write(VarInt.writeUnsignedVarInt((byte) 0x01));
		PacketUtils.writeString("F1", output);
		output.write(VarInt.writeUnsignedVarInt(token1.getBytes().length));
		output.write(token1.getBytes());
		output.write(VarInt.writeUnsignedVarInt(token2.getBytes().length));
		output.write(token2.getBytes());
		output.flush();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ParseException {
		Socket s = new Socket("mc.hypixel.net", 25565);
		System.out.println("CONNECTED...");
		String address = s.getInetAddress().toString().replace("/", "").split("mc.hypixel.net")[1];
		DataOutputStream output = new DataOutputStream(s.getOutputStream());
		
		output.write(VarInt.writeUnsignedVarInt(6 + address.length()));
		output.writeByte((byte) 0x00);
		output.write(VarInt.writeUnsignedVarInt(47));
		PacketUtils.writeString(address, output);
		PacketUtils.writeUnsignedShort((short) 25565, output);
		output.write(VarInt.writeUnsignedVarInt(2));
		output.flush();
		
		String username = "SuperstarGamer";
		output.write(VarInt.writeUnsignedVarInt(2 + username.getBytes().length));
		output.writeByte((byte) 0x00);
		output.write(VarInt.writeUnsignedVarInt(username.getBytes().length));
		output.write(username.getBytes());
		output.flush();
		
		System.out.println("PINGING ADDRESS " + address);
		
		DataInputStream input = new DataInputStream(s.getInputStream());
		while(input.available() == 0);
		System.out.println("RECEIVED RESPONSE");
		input.readByte();
		System.out.println("Packet ID: " + VarInt.readUnsignedVarInt(input.readByte()));
		System.out.println("Server ID: " + PacketUtils.readString(input));
		int length = VarInt.readUnsignedVarInt(new byte[] {input.readByte(), input.readByte()});
		byte[] raw = new byte[length];
		for(int i = 0; i < length; i++) raw[i] = input.readByte();
		System.out.println("Key 1: " + new String(raw));
	}

}
