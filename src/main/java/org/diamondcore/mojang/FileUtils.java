/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.mojang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * --- NOTE ---
 * 
 * THIS CLASS WILL BE DELETED SOON!
 * 
 * --- NOTE ---
 *
 */
public class FileUtils {
	
	public static String readFromURL(URL url) throws IOException {
		InputStream in = url.openStream();
		StringBuilder builder = new StringBuilder();
		int c = -1;
		while((c = in.read()) != -1) builder.append((char) c);
		return builder.toString();
	}
	
}
