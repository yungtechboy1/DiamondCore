package org.jukeboxmc.raknet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RakNet {

    public static void main( String[] args ) {
        Listener listener = new Listener();
        if ( listener.listen( "127.0.0.1", 19132 ) ) {
            System.out.println( "RakNet wurde gestartet" );
        }else{
            System.out.println( "Server konnte nicht gestartet werden" );
        }
    }

}
