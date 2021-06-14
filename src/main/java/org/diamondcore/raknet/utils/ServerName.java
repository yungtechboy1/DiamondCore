package org.jukeboxmc.raknet.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jukeboxmc.raknet.Listener;

import java.util.StringJoiner;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class ServerName {

    private String motd = "§bJukeboxMC";
    private String name = "Line 2";
    private int onlinePlayers = 0;
    private int maxPlayers = 20;
    private String gameMode = "Survival";
    private long serverId;

    public ServerName( Listener listener ) {
        this.serverId = listener.serverId;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner( ";" );
        stringJoiner.add( "MCPE" );
        stringJoiner.add( this.motd );
        stringJoiner.add( Integer.toString( Identifiers.Protocol ) );
        stringJoiner.add( Identifiers.Version );
        stringJoiner.add( Integer.toString( this.onlinePlayers ) );
        stringJoiner.add( Integer.toString( this.maxPlayers ) );
        stringJoiner.add( Long.toString( this.serverId ) );
        stringJoiner.add( this.name );
        stringJoiner.add( this.gameMode );
        return stringJoiner.toString();
    }
}
