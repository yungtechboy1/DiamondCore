/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.utils;

import java.io.DataInput;
import java.io.IOException;

public class StringUtils {
	
	public static String readString(int len, DataInput in) throws IOException {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < len; i++)
			builder.append((char) in.readByte());
		return builder.toString();
	}
	
}
