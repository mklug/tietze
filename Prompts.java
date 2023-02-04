package tietze;

public class Prompts {

	public static void printGeneratorPrompt() {
		System.out.println("Enter generators: ");		
	}

	public static void printRelationPrompt() {
		System.out.println("Enter relations: ");		
	}

	public static void printUserPromptSymbol() {
		System.out.print("> ");
	}

	public static void printTietzePrompt() {
		System.out.println("Apply Tietze move type 1 or 2? \nEnter: '1' or '2'.");
	}

	public static void printTietzeIPrompt() {
		System.out.println("Apply Tietze move 1 (add redundant relation) or 1' " +  
				"(remove redundant relation)? \nEnter: '1' or '1''.");
	}


	public static void printTietzeIGetRedundantRelation() {
		System.out.println("Enter a product of conjugates of the other relations " +  
				"(e.g., 'r1^{aba} R2^{Ba}') : " );
	}

	public static void printTietzeIPGetRedundantRelation() {
		System.out.println("Enter relation to be removed: ");
	}

	public static void printTietzeIPGetWordForRedundantRelation() {
		System.out.println("Enter a product of conjugates of the other relations " +
				"(e.g., 'r1^{aba} R2^{Ba}') that are equal to the relation to be removed: ");
	}

	public static void printTietzeIIPrompt() {
		System.out.println("Apply Tietze move 2 (add a redundant generator) or 2' " + 
				"(remove a redundant generator)? \nEnter: '2' or '2''.");
	}

	public static void printTietzeIIGetLetter() {
		System.out.println("Enter a new letter:");	
	}

	public static void printTietzeIIGetWord() {
		System.out.println("Enter word to equal the inverse of the new letter:");	
	}
	
	public static void printTietzeIIPrimePrompt() {
		System.out.println("Enter relation to be removed (e.g., 'r2'): ");	
	}

}
