package tietze;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

public class Tietze {

	public static void main(String[] args) {

		// Initiation stage.  
		Scanner scanner = new Scanner(System.in);

		// Get generators.
		Prompts.printGeneratorPrompt();
		Prompts.printUserPromptSymbol();

		Set<String> generators = new HashSet<String>();
		checkForNoInput(scanner);	
		String input = scanner.nextLine();

		try {
			String[] gens = input.split(",");
			for (String g : gens) {
				g = processGenerator(g);
				generators.add(g);
			}
		} catch (InitiationException e){
			System.out.println("Invalid generator input.");
			System.exit(64);
		}


		// Get relations.
		Prompts.printRelationPrompt();
		Prompts.printUserPromptSymbol();

		Set<List<String>> relations = new HashSet<List<String>>();
		checkForNoInput(scanner);	
		input = scanner.nextLine();

		try {
			String[] rels = input.split(",");
			for (String r : rels) {
				r = r.trim();
				List<String> rList = new LinkedList<String>();
				processRelation(r, generators, rList);
				relations.add(rList);
			}
		} catch (InitiationException e){
			System.out.println("Invalid relation input.");
			System.exit(64);
		}

		System.out.println("Group successfully initiated.");
		GroupPresentation group = new GroupPresentation(generators, relations);
		
		
		// Main loop.
		for (;;) {

			System.out.println();
			group.printGroup();

			Prompts.printTietzePrompt();
			Prompts.printUserPromptSymbol();
			checkForNoInput(scanner);	
			String optionIorII = scanner.nextLine().trim();

			if (optionIorII.equals("1")) {

				Prompts.printTietzeIPrompt();
				Prompts.printUserPromptSymbol();
				String optionI = scanner.nextLine().trim();
				if (optionI.equals("1")) {
				
				} else if (optionI.equals("1'")) {
				
				} else {	
					printInvalidInput();
				}	



			
			} else if (optionIorII.equals("2")) {

				Prompts.printTietzeIIPrompt();
				Prompts.printUserPromptSymbol();
				String optionII = scanner.nextLine().trim();				
				if (optionII.equals("2")) {

					Prompts.printTietzeIIGetLetter();
					Prompts.printUserPromptSymbol();				
					String newLetter = scanner.nextLine().trim();
					// Need to check this is new and has the valid format.
					
					boolean validNewLetter = isValidNewLetter(group, newLetter);

					if (validNewLetter) { 

						Prompts.printTietzeIIGetWord();
						Prompts.printUserPromptSymbol();				
						String wordForNewLetter = scanner.nextLine().trim();
						// Need to check that this is a word in the letters.
						
						boolean validWordForNewLetter = isValidNewWordForNewLetter(group, wordForNewLetter);	
						if (validWordForNewLetter) {

							generators.add( newLetter );

							List<String> wordFormatted = new LinkedList<String>();
							expandExponents(wordForNewLetter, wordFormatted);
							wordFormatted.add(0, newLetter);
						       	relations.add( wordFormatted );
							
							//group = null;	
							//GroupPresentation group = new GroupPresentaion(generators, relations);
							group = new GroupPresentation(generators, relations);
						
							
						} else printInvalidInput();
					} else printInvalidInput();




				} else if (optionII.equals("2'")) {
			
					Prompts.printTietzeIIPrimePrompt();
					Prompts.printUserPromptSymbol();				







				} else printInvalidInput();

			} else printInvalidInput();
		}
	}

	private static boolean isValidNewLetter(GroupPresentation group, String newLetter) {
		Set<String> oldGenerators = group.getGenerators();
	       	if (oldGenerators.contains(newLetter)) return false;
		if (StringFunctions.isLetter(newLetter) || StringFunctions.isLetterThenNumber(newLetter)) {
			return true;
		}
		return false;
	} 

	private static boolean isValidNewWordForNewLetter(GroupPresentation group, String wordForNewLetter) {
		if (isValidRelationWordFormat(wordForNewLetter)) {

			Set<String> oldGenerators = group.getGenerators();
			
			List<String> list = new LinkedList<String>();
			String expanded = expandExponents(wordForNewLetter, list);
			if (doesWordBelong(expanded, oldGenerators)) {
				return true;
			}
		}
		return false;
	} 






	private static void printInvalidInput() {
		System.out.println("Invalid input.");		
	}

	private static void checkForNoInput(Scanner scanner) {
		if (!scanner.hasNextLine()) System.exit(64);				
	}

	private static String processGenerator(String g)throws InitiationException {
		g = g.trim();
		if (StringFunctions.isLetter(g)) return g;
		if (StringFunctions.isLetterThenNumber(g)) return g;
		throw new InitiationException("Invalid generator entry.");
	}

	private static void processRelation(String r, Set<String> generators, List<String> rList)throws InitiationException {
		
		// Check the format is correct.
		if (isValidRelationWordFormat(r)) {
			r = expandExponents(r, rList);

			// Check that it fits with the generators.
			if (doesWordBelong(r, generators)) {
				return;
			}
		}
		throw new InitiationException("Invalid generator entry."); 
	}

	// Checks that the word is of the form a2A3^{-3}b12A3^{2}
	private static boolean isValidRelationWordFormat(String str) {
		boolean isValid = false;
		if ( str.matches("([a-zA-Z]+\\d*(\\^\\{(\\-)?\\d+\\})?)+") ) {
			isValid = true;
		}
		return isValid;
	}

	// Give input the raw word "a^{3}b". And an empty list [].
	// Returns the word expanded aaab and fills the list [a,a,a,b].  
	private static String expandExponents(String str, List<String> list) {

		if (list.size() != 0) {
			System.out.println("Error: Nonempty list passed to exponent expansion");
		}

		str = str.replace("^","");
		String[] strSplit = str.split("[\\{\\}]");
		
		for (int i = 1; i < strSplit.length; i++) {
			
			// Positive exponent.
			if (strSplit[i].matches("(\\d)+")) {
				int exp = Integer.parseInt(strSplit[i]);
				strSplit[i] = "";

				String ll = StringFunctions.returnLastLetter(strSplit[i-1]); 
				for (int j = 1; j < exp; j++) {
					strSplit[i] += ll;
				}
			}

			// Negative exponent.
			if (strSplit[i].matches("\\-(\\d)+")) {
				int exp = -Integer.parseInt(strSplit[i]);
				strSplit[i] = "";

				String ll = StringFunctions.returnLastLetter(strSplit[i-1]); 
				ll = StringFunctions.changeCase(ll);
				for (int j = 1; j <= exp+1; j++) {
					strSplit[i] += ll;
				}
			}
		}

		str = String.join("", strSplit);
		String strCopy = str;

		// A bit hacky...
		String s;
		do {
			s = StringFunctions.returnFirstLetter(str);			
			str = StringFunctions.removeFirstLetter(str);
			list.add(s);
		} while (str != "");

		return strCopy;
	}


	// Takes as input the fully exponentiated form.
	private static boolean doesWordBelong(String word, Set<String> generators) {
		boolean out = true;

		String str;
		do {
			str = StringFunctions.returnFirstLetter(word);
			out = (out && (generators.contains( str ) || 
					generators.contains(StringFunctions.changeCase(str))));

			word = StringFunctions.removeFirstLetter(word);
		} while (word != "");
		return out;	
	}
}

class InitiationException extends Exception {
	public InitiationException(String s)
    	{
		super(s);
    	}
}
