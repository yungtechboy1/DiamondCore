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
