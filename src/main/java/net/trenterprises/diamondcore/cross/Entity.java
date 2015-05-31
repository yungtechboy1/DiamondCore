package net.trenterprises.diamondcore.cross;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity {
	
	protected static ArrayList<Integer> idList = new ArrayList<Integer>();
	
	public static int generateEntityID() {
		int id = new Random().nextInt();
		while(idList.contains(id)) id = new Random().nextInt();
		return id;
	}
	
}
