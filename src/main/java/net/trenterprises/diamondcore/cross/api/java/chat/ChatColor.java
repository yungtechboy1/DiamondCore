/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.java.chat;

public enum ChatColor {
	
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
	
	protected String value() {
		return this.c;
	}
	
	public static String stripColors(String input) {
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ChatColor.RAW.toString().toCharArray()[0]) i+= 2;
			builder.append(chars[i]);
		}
		return builder.toString();
	}
	
}
