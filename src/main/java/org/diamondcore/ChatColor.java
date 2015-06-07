/*
_______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
*/

package org.diamondcore;

import static org.fusesource.jansi.Ansi.ansi;

public enum ChatColor {
	
	// Minecraft
	RAW("§") {
		public String toString() {
			return RAW.value();
		}
	},
	BLACK("§0") {
		public String toString() {
			return BLACK.value();
		}
	},
	DARK_BLUE("§1") {
		public String toString() {
			return DARK_BLUE.value();
		}
	},
	DARK_GREEN("§2") {
		public String toString() {
			return DARK_GREEN.value();
		}
	},
	DARK_AQUA("§3") {
		public String toString() {
			return DARK_AQUA.value();
		}
	},
	DARK_RED("§4") {
		public String toString() {
			return DARK_RED.value();
		}
	},
	DARK_PURPLE("§5") {
		public String toString() {
			return DARK_PURPLE.value();
		}
	},
	GOLD("§6") {
		public String toString() {
			return GOLD.value();
		}
	},
	GREY("§7") {
		public String toString() {
			return GREY.value();
		}
	},
	DARK_GREY("§8") {
		public String toString() {
			return DARK_GREY.value();
		}
	},
	BLUE("§9") {
		public String toString() {
			return BLUE.value();
		}
	},
	GREEN("§a") {
		public String toString() {
			return GREEN.value();
		}
	},
	AQUA("§b") {
		public String toString() {
			return AQUA.value();
		}
	},
	RED("§c") {
		public String toString() {
			return RED.value();
		}
	},
	LIGHT_PURPLE("§d") {
		public String toString() {
			return LIGHT_PURPLE.value();
		}
	},
	YELLOW("§e") {
		public String toString() {
			return YELLOW.value();
		}
	},
	WHITE("§f") {
		public String toString() {
			return WHITE.value();
		}
	},
	OBFUSCATED("§k") {
		public String toString() {
			return OBFUSCATED.value();
		}
	},
	BOLD("§l") {
		public String toString() {
			return BOLD.value();
		}
	},
	STRIKETHROUGH("§m") {
		public String toString() {
			return STRIKETHROUGH.value();
		}
	},
	UNDERLINE("§n") {
		public String toString() {
			return UNDERLINE.value();
		}
	},
	ITALIC("§o") {
		public String toString() {
			return ITALIC.value();
		}
	},
	RESET("§r") {
		public String toString() {
			return RESET.value();
		}
	};
	
	String c;
	private ChatColor(String c) {
		this.c = c;
	}
	
	/**
	 * Used to get the value form a color
	 * 
	 * @return Color value
	 * @author Trent Summerlin
	 */
	protected String value() {
		return this.c;
	}
	
	// ANSI Colors
	private static final String ANSI = "\u001b[";
	private static final String ANSI_BLACK = (ANSI + "0;30m");
	private static final String ANSI_DARK_BLUE = (ANSI + "0;34m");
	private static final String ANSI_DARK_GREEN = (ANSI + "0;32m");
	private static final String ANSI_DARK_AQUA = (ANSI + "0;36m");
	private static final String ANSI_DARK_RED = (ANSI + "0;31m");
	private static final String ANSI_DARK_PURPLE = (ANSI + "0;35m");
	private static final String ANSI_GOLD = (ANSI + "0;33m");
	private static final String ANSI_GREY = (ANSI + "0;37m");
	private static final String ANSI_DARK_GREY = (ANSI + "1;30m");
	private static final String ANSI_BLUE = (ANSI + "1;34m");
	private static final String ANSI_GREEN = (ANSI + "1;32m");
	private static final String ANSI_AQUA = (ANSI + "1;36");
	private static final String ANSI_RED = (ANSI + "1;31m");
	private static final String ANSI_LIGHT_PURPLE = (ANSI + "1;35m");
	private static final String ANSI_YELLOW = (ANSI + "1;33m");
	private static final String ANSI_WHITE = (ANSI + "1;37m");
	private static final String ANSI_RESET = (ANSI + "22m" + ANSI + "23m" + ANSI + "24m" + ANSI + "29m" + ANSI_GREY);
	
	// TODO: Get fonts working if possible
	private static final String ANSI_OBFUSCATED = (ANSI_WHITE);
	private static final String ANSI_BOLD = (ANSI_WHITE);
	private static final String ANSI_ITALIC = (ANSI_WHITE);
	private static final String ANSI_UNDERLINE = (ANSI_WHITE);
	private static final String ANSI_STRIKETHROUGH = (ANSI_WHITE);
	
	/**
	 * Used to strip away the colors from text
	 * 
	 * @param input
	 * @return Text stripped of colors and fonts
	 * @author Trent Summerlin
	 */
	public static String stripColors(String input) {
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ChatColor.RAW.toString().toCharArray()[0]) i+= 2;
			builder.append(chars[i]);
		}
		return builder.toString();
	}
	
	/**
	 * Used to convert Minecraft colored text to Console
	 * colored text
	 * 
	 * @param input
	 * @return Converted Minecraft test
	 * @author Trent Summerlin
	 */
	public static String toConsole(String input) {
		
		if(!Diamond.getServer().isDebug())
			return ansi().render(input.replace(ChatColor.BLACK.toString(), ANSI_BLACK)
				.replace(ChatColor.DARK_BLUE.toString(), ANSI_DARK_BLUE)
				.replace(ChatColor.DARK_GREEN.toString(), ANSI_DARK_GREEN)
				.replace(ChatColor.DARK_AQUA.toString(), ANSI_DARK_AQUA)
				.replace(ChatColor.DARK_RED.toString(), ANSI_DARK_RED)
				.replace(ChatColor.DARK_PURPLE.toString(), ANSI_DARK_PURPLE)
				.replace(ChatColor.GOLD.toString(), ANSI_GOLD)
				.replace(ChatColor.GREY.toString(), ANSI_GREY)
				.replace(ChatColor.DARK_GREY.toString(), ANSI_DARK_GREY)
				.replace(ChatColor.BLUE.toString(), ANSI_BLUE)
				.replace(ChatColor.GREEN.toString(), ANSI_GREEN)
				.replace(ChatColor.AQUA.toString(), ANSI_AQUA)
				.replace(ChatColor.RED.toString(), ANSI_RED)
				.replace(ChatColor.LIGHT_PURPLE.toString(), ANSI_LIGHT_PURPLE)
				.replace(ChatColor.YELLOW.toString(), ANSI_YELLOW)
				.replace(ChatColor.WHITE.toString(), ANSI_WHITE)
				.replace(ChatColor.OBFUSCATED.toString(), ANSI_OBFUSCATED)
				.replace(ChatColor.BOLD.toString(), ANSI_BOLD)
				.replace(ChatColor.ITALIC.toString(), ANSI_ITALIC)
				.replace(ChatColor.UNDERLINE.toString(), ANSI_UNDERLINE)
				.replace(ChatColor.STRIKETHROUGH.toString(), ANSI_STRIKETHROUGH)
				.replace(ChatColor.RESET.toString(), ANSI_RESET) + ANSI_RESET).toString();
		else
			return ChatColor.stripColors(input);
	}
}
