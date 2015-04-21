package net.trenterprises.diamondcore.desktop.network.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ImageUtils {
	
	private ImageUtils() {}
	
	public static String encodeToString(File file) throws IOException{
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
	    try {
	    	byte[] buffer = new byte[4096];
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ( (read = ios.read(buffer)) != -1 ) ous.write(buffer, 0, read);
	    } finally {
	        try {
	        	if ( ous != null ) ous.close();
	        	if ( ios != null ) ios.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }
	    return Base64.encodeToString(ous.toByteArray());
	}
	
}
