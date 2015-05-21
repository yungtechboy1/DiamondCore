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
	
	RAW("Â§") {
		public String toString() {
			return RAW.value();
		}
	},
	BLACK("Â§0") {
		public String toString() {
			return BLACK.value();
		}
	},
	DARK_BLUE("Â§1") {
		public String toString() {
			return DARK_BLUE.value();
		}
	},
	DARK_GREEN("Â§2") {
		public String toString() {
			return DARK_GREEN.value();
		}
	},
	DARK_AQUA("Â§3") {
		public String toString() {
			return DARK_AQUA.value();
		}
	},
	DARK_RED("Â§4") {
		public String toString() {
			return DARK_RED.value();
		}
	},
	DARK_PURPLE("Â§5") {
		public String toString() {
			return DARK_PURPLE.value();
		}
	},
	GOLD("Â§6") {
		public String toString() {
			return GOLD.value();
		}
	},
	GREY("Â§7") {
		public String toString() {
			return GREY.value();
		}
	},
	DARK_GREY("Â§8") {
		public String toString() {
			return DARK_GREY.value();
		}
	},
	BLUE("Â§9") {
		public String toString() {
			return BLUE.value();
		}
	},
	GREEN("Â§a") {
		public String toString() {
			return GREEN.value();
		}
	},
	AQUA("Â§b") {
		public String toString() {
			return AQUA.value();
		}
	},
	RED("Â§c") {
		public String toString() {
			return RED.value();
		}
	},
	LIGHT_PURPLE("Â§d") {
		public String toString() {
			return LIGHT_PURPLE.value();
		}
	},
	YELLOW("Â§e") {
		public String toString() {
			return YELLOW.value();
		}
	},
	WHITE("Â§f") {
		public String toString() {
			return WHITE.value();
		}
	},
	OBFUSCATED("Â§k") {
		public String toString() {
			return OBFUSCATED.value();
		}
	},
	BOLD("Â§l") {
		public String toString() {
			return BOLD.value();
		}
	},
	STRIKETHROUGH("Â§m") {
		public String toString() {
			return STRIKETHROUGH.value();
		}
	},
	UNDERLINE("Â§n") {
		public String toString() {
			return UNDERLINE.value();
		}
	},
	ITALIC("Â§o") {
		public String toString() {
			return ITALIC.value();
		}
	},
	RESET("Â§r") {
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
