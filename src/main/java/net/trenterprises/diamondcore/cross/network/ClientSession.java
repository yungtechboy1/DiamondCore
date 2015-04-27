/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.network;

import net.trenterprises.diamondcore.DiamondCoreServer;

import org.blockserver.io.BinaryReader;

/**
 * Represents a Session.
 * 
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public abstract class ClientSession {
    public DiamondCoreServer server;

    public ClientSession(DiamondCoreServer server){
        this.server = server;
    }

    public abstract void handlePacket(BinaryReader reader);
}
