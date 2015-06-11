package org.diamondcore.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public final class SplashChooser {
	
	private SplashChooser() {}
	
	public static String chooseSplash() throws IOException {
		Scanner s = new Scanner(SplashChooser.class.getResource("/splashes.txt").openStream());
		ArrayList<String> a = new ArrayList<String>();
		while(s.hasNextLine())
			a.add(s.nextLine());
		s.close();
		return a.get(new Random().nextInt(a.size()));
	}
	
}
