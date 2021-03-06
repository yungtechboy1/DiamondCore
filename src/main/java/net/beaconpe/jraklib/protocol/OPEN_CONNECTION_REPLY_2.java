/*
   JRakLib networking library.
   This software is not affiliated with RakNet or Jenkins Software LLC.
   This software is a port of PocketMine/RakLib <https://github.com/PocketMine/RakLib>.
   All credit goes to the PocketMine Project (http://pocketmine.net)
 
   Copyright (C) 2015 BlockServerProject & PocketMine team

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.beaconpe.jraklib.protocol;

import net.beaconpe.jraklib.JRakLib;

import java.net.InetSocketAddress;

/**
 * OPEN_CONNECTION_REPLY_2 (Not encapsulated, 0x08)
 */
public class OPEN_CONNECTION_REPLY_2 extends Packet{
    public static byte ID = 0x08;
    public long serverID;
    public InetSocketAddress clientAddress;
    public short mtuSize;


    public byte getID() {
        return 0x08;
    }

    @Override
    protected void _encode() {
        put(JRakLib.MAGIC);
        putLong(serverID);
        putAddress(clientAddress.getHostString(), clientAddress.getPort(), (byte) 4);
        putShort(mtuSize);
        putByte((byte) 0x00); //server security
    }

    @Override
    protected void _decode() {
        offset = offset + 15;
        serverID = getLong();
        clientAddress = getAddress();
        mtuSize = getShort();
        //server security
    }
}
