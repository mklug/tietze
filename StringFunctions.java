package tietze;

public class StringFunctions {

	public static char changeCase(char c) {
		if (('a' <= c) && (c <= 'z')) return Character.toUpperCase(c);
		if (('A' <= c) && (c <= 'Z')) return Character.toLowerCase(c);
		return c;
	}

	public static String changeCase(String str) {
		String out = "";
		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			c = changeCase(c);
			
			out += c;
		}
		return out;
	}

	public static boolean isLetter(String g) {
		if (g.length() != 1) return false;
		char c = g.charAt(0);
	       	return isCharLetter(c);
	}

	public static boolean isCharLetter(char c) {
		return (c >= 'a' && c <= 'z') ||
                         (c >= 'A' && c <= 'Z');
	}

        public static boolean isDigit(char c) {
                return c>='0' && c <= '9';
        }

	public static boolean isLetterThenNumber(String g) {
		boolean out = true;
		if (g.length() < 2) return false;
		out = out && isCharLetter(g.charAt(0));
		g = g.substring(1);

		char c;
		for (int i = 0; i < g.length(); i++) {
			c = g.charAt(i);
			out = out && isDigit(c);	
		}
		return out;
	}

		// Returns the first letter for example: a12Aba15 --> a12
	public static String returnFirstLetter(String word) {
		if (word == "") return "";
		String out = word.substring(0,1);
		word = word.substring(1);
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (isDigit(c)) {
				out += c;	
			} else break;	
		}
		return out;
	}

	public static String returnLastLetter(String word) {
		if (returnFirstLetter(word) == "") return "";

		String str;
		do {
			str = returnFirstLetter(word);			
			word = removeFirstLetter(word);
		} while (word != "");
		return str;	
	}
	
	// a12A3b15 --> A3b15
	public static String removeFirstLetter(String word) {
		String firstLetter = returnFirstLetter(word);
		int l = firstLetter.length();
		if (l == word.length()) return "";
		return word.substring(l, word.length());
	}


}
