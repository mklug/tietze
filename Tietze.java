package tietze;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

public class Tietze {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Set<String> generators = new HashSet<String>();
		Set<List<String>> relations = new HashSet<List<String>>();
		String input;

		//List<String> rList = new LinkedList<String>();
	
		//Initiation loop.
		for (;;) {
			// Get generators.
			Prompts.printGeneratorPrompt();
			Prompts.printUserPromptSymbol();

			//Set<String> generators = new HashSet<String>();
			checkForNoInput(scanner);	
			input = scanner.nextLine().trim();

			String[] gens = input.split(",");
			boolean generatorsInitiated = true;
			for (String g : gens) {
				generatorsInitiated = generatorsInitiated && processGenerator(g, generators);
			}

			if (generatorsInitiated) {

				// Get relations.
				Prompts.printRelationPrompt();
				Prompts.printUserPromptSymbol();

				//Set<List<String>> relations = new HashSet<List<String>>();
				checkForNoInput(scanner);	
				input = scanner.nextLine().trim();

				//
				if (input.trim().equals("")) break;


				String[] rels = input.split(",");
				boolean relationsInitiated = true;

				for (String r : rels) {

					List<String> rList = new LinkedList<String>();
					r = r.replaceAll("\\s+", "");
					relationsInitiated = relationsInitiated && processRelation(r, generators, rList);
					relations.add(rList);
				}

				if (relationsInitiated) {
					System.out.println("Group successfully initiated.");
					break;
				
				} else {
					printInvalidInput();
					generators.clear();
					relations.clear();
				}

			} else {
				printInvalidInput();
				generators.clear();
			}
		}
		GroupPresentation group = new GroupPresentation(generators, relations);



		// Main loop.
		for (;;) {

			System.out.println();
			group.printGroup();

			Prompts.printTietzePrompt();
			Prompts.printUserPromptSymbol();
			checkForNoInput(scanner);	
			input = scanner.nextLine().trim();

			if (input.equals("1")) {

				Prompts.printTietzeIPrompt();
				Prompts.printUserPromptSymbol();
				input = scanner.nextLine().trim();
				if (input.equals("1")) {
				
				} else if (input.equals("1'")) {
				
				} else {	
					printInvalidInput();
				}	



			
			} else if (input.equals("2")) {

				Prompts.printTietzeIIPrompt();
				Prompts.printUserPromptSymbol();
				checkForNoInput(scanner);				
				input = scanner.nextLine().trim();				
				if (input.equals("2")) {

					Prompts.printTietzeIIGetLetter();
					Prompts.printUserPromptSymbol();
					checkForNoInput(scanner);												
					input = scanner.nextLine().trim();
					// Need to check this is new and has the valid format.
					
					boolean validNewLetter = isValidNewLetter(group, input);

					if (validNewLetter) { 
						Prompts.printTietzeIIGetWord();
						Prompts.printUserPromptSymbol();
						checkForNoInput(scanner);
						String wordForNewLetter = scanner.nextLine().trim();
						// Need to check that this is a word in the letters.
						boolean validWordForNewLetter = isValidNewWordForNewLetter(group, wordForNewLetter);	
						if (validWordForNewLetter) {

							group.addGenerator(input);
							List<String> wordFormatted = new LinkedList<String>();
							expandExponents(wordForNewLetter, wordFormatted);
							wordFormatted.add(0, input);
							group.addRelation( wordFormatted );

						} else printInvalidInput();
					} else printInvalidInput();

				} else if (input.equals("2'")) {
			
					Prompts.printTietzeIIPrimePrompt();
					Prompts.printUserPromptSymbol();
					checkForNoInput(scanner);
					input = scanner.nextLine().trim();
					int rIndex = getIndexFromInputTIIPrime(input);
					if (rIndex == -1) {
						printInvalidInput();
					} else {
						if (isValidTIIPrime(rIndex, group)) {
						
							// add to group pres funcs for adding/removing gens/rels.

							List<String> rel = group.getRelations().get(rIndex - 1);
							String genRemove = rel.get(0);
							group.removeGenerator(genRemove);
							group.removeRelation(rIndex);
							

						} else printInvalidInput();
					} 

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

	// Gets index from word -- returns -1 if the word is invalid (not of the form ri).
	private static int getIndexFromInputTIIPrime(String input) {
		if (! StringFunctions.isLetterThenNumber(input) ) return -1;
		if ( input.charAt(0) != 'r' ) return -1;
		return Integer.parseInt( input.substring(1) );
	
	}

	// Check if the word can be removed by TII' move -- i.e. is it of the form yw where w is a word in the other generators.
	// The index i is the index of the proposed relator -- ri.  
	private static boolean isValidTIIPrime(int index, GroupPresentation group) {
	
		// Need to check the index is in bounds.
		if ( ! ( (1 <= index) && ( index <= group.getNumberRelations()) )) return false; 

		// Need to check that the word is of the valid form yw with no y/Y in w.
		List<List<String>> relations = group.getRelations();
		List<String> relForRemoval = relations.get(index-1); // need to adjust index
		String wordForRemoval = relForRemoval.get(0); // Take the first word.

		for (int j = 1 ; j < relForRemoval.size(); j++) {
			if (relForRemoval.get(j).equals(wordForRemoval) ||
				relForRemoval.get(j).equals( StringFunctions.changeCase(wordForRemoval)) ) return false;
		}
	
		// Need to check that all other relations do not contain a y/Y.	
	
		for (int j = 0; j < group.getNumberRelations(); j++) {
			if (j == index - 1) continue;
			for (int i = 0; i < relations.get(j).size(); i++) {
				if (relations.get(j).get(i).equals(wordForRemoval) ||
					relations.get(j).get(i).equals( StringFunctions.changeCase(wordForRemoval)) ) return false;
			}
		}	
		return true;
	}


	private static void printInvalidInput() {
		System.out.println("Invalid input.");		
	}

	private static void checkForNoInput(Scanner scanner) {
		if (!scanner.hasNextLine()) System.exit(64);				
	}

	private static boolean processGenerator(String g, Set<String> generators) {
		g = g.trim();
		if (StringFunctions.isLetter(g) || StringFunctions.isLetterThenNumber(g) ) {
			generators.add(g);	
			return true;
		}
		return false;
	}

	// Returns if the relation is valid as a word in the generators.
	// rList should be empty as input.
	// rList on exit has the relation expanded in it.  
	private static boolean processRelation(String r, Set<String> generators, List<String> rList) {
		r = r.trim();
		
		// Check the format is correct.
		if (isValidRelationWordFormat(r)) {
			r = expandExponents(r, rList);
			// Check that it fits with the generators.
			if (doesWordBelong(r, generators)) {
				return true;
			}
		}
		return false;
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
