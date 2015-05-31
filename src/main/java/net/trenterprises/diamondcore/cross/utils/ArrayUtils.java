/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.utils;

import java.util.ArrayList;

/**
 * Used to convert list's to arraylist's and vice-versa
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class ArrayUtils {
	
	/**
	 * Used to convert a object list to a object arraylist
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param oldList
	 * 		The old un-converted  list
	 * @return Converted string list
	 */
	public static ArrayList<Object> toArraylist(Object[] oldList) {
		ArrayList<Object> newList = new ArrayList<Object>();
		for(int i = 0; i < oldList.length; i++) newList.add(oldList[i]);
		return newList;
	}
	
	/**
	 * Used to convert a String array to a String ArrayList
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param oldList
	 * 		The old un-converted string list
	 * @return Converted string list
	 */
	public static ArrayList<String> toArraylist(String[] oldList) {
		ArrayList<String> newList = new ArrayList<String>();
		for(int i = 0; i < oldList.length; i++) newList.add(oldList[i]);
		return newList;
	}
	
	/**
	 * Used to see if a arraylist contains a object
	 * 
	 * @author Trent Summerlin
	 * @verion 1.0
	 * @param array
	 * 		The arraylist to cycle through
	 * @param object
	 * 		The object to check
	 * @return Whether or whether not the array contains the specified object
	 */
	public static boolean contains(ArrayList<?> array, Object object) {
		boolean contains = false;
		for(int i = 0; i < array.size(); i++) {
			if(!contains) {
				if(array.get(i).equals(object)) contains = true;
				else {
					System.out.println(array.get(i) + "\n----------");
				}
			} else break;
		}
		return contains;
	}
	
	/**
	 * Used to stich together arguments if needed
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Sticthed array
	 */
	public static String stitchArray(String[] args, int start) {
		if(start == -1) start = 0;
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < args.length; i++) builder.append(args[i] + (i+1 != args.length ? " " : ""));
		return builder.toString();
	}
	
}
