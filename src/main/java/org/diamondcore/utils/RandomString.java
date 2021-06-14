/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.utils;

import java.util.Random;

/**
 * Used to generate a random string for things like
 * <br>
 * a session ID
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class RandomString {
	
	protected final char[] alphabet = {
					'a', 'b', 'c', 'd',
					'e', 'f', 'g', 'h',
					'i', 'j', 'k', 'l', 
					'm', 'n', 'o', 'p', 
					'q', 'r', 's', 't', 
					'u', 'v', 'w', 'x', 
					'y', 'z', '1', '2',
					'3', '4', '5', '6',
					'7', '8', '9', '0'
									  };
	
	public String nextString(int len) {
		String data = "";
		int max = alphabet.length;
		for(int i = 0; i < len; i++) {
			int pos = (new Random().nextInt(max));
			data += alphabet[pos];
		}
		return data;
	}
	
	public String nextString() {
		return nextString(10);
	}
	
	public static void main(String[] args) {
		System.out.println(new RandomString().nextString(25));
	}
	
}
