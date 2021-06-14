package org.jukeboxmc.raknet.protocol;

import org.jukeboxmc.raknet.utils.Identifiers;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ACK extends AcknowledgePacket {

    public ACK() {
        super( Identifiers.AcknowledgePacket );
    }

    @Override
    public String toString() {
        return "ACK{" +
                "packetId=" + packetId +
                '}';
    }
}
