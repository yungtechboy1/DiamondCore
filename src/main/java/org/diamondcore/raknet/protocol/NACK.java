package org.diamondcore.raknet.protocol;

import org.diamondcore.raknet.utils.Identifiers;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NACK extends AcknowledgePacket {

    public NACK() {
        super( Identifiers.NacknowledgePacket );
    }
}
