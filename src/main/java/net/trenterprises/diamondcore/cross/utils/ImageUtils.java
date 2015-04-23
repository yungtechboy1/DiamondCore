package net.trenterprises.diamondcore.cross.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class ImageUtils {
	
	private ImageUtils() {}
	
	public static String toString(File image) throws IOException {
        return new String(Base64.encode(toByteArray(image)));  
    } 
	
	protected static byte[] toByteArray(File file) {
		try {
			FileInputStream fileInputStream = null;
			byte[] bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			for(int i = 0; i < bFile.length; i++) bFile[i] = (byte) fileInputStream.read();
			fileInputStream.close();
			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
