package org.jukeboxmc.raknet.protocol;

import org.jukeboxmc.raknet.utils.Identifiers;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NACK extends AcknowledgePacket {

    public NACK() {
        super( Identifiers.NacknowledgePacket );
    }
}
