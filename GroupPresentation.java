package tietze;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class GroupPresentation {
	
	private Set<String> generators;
	private List<List<String>> relations;


	public GroupPresentation(Set<String> generators, Set<List<String>> relationsAsSet) {
		this.generators = generators;

		List<List<String>> relationsForOutput = new ArrayList<>();

		for (List<String> rel : relationsAsSet) {
			reduceWord(rel);
			if (! (rel.size() == 0) ) relationsForOutput.add(rel);
		}
		this.relations = relationsForOutput;
	}

	public Set<String> getGenerators() {
		return generators;
	}

	public List<List<String>> getRelations() {
		return relations;	
	}

	public void removeGenerator(String gen) {
		gen = gen.trim();
		generators.remove(gen);
	}

	public void removeRelation(List<String> relation) {
		relations.remove(relation);
	}

	// Note the index shift.
	public void removeRelation(int indexOfRelation) {
		relations.remove(indexOfRelation - 1);
	}

	// Note : Does not do the checking for compatability.  Must be done by client.
	public void addGenerator(String gen) {
		generators.add(gen);
	}

	// Note : Does not do the checking for compatability.  Must be done by client.
	public void addRelation(List<String> rel) {
		relations.add(rel);
	}

	public int getNumberRelations() {
		return relations.size();
	}



	public void printGroup() {
		printGenerators();
		printRelations();
	}

	public void printGenerators() {
		System.out.println("Group generators: ");
		System.out.println(generators);
	}

	// Note the index shift when printing the relations.
	public void printRelations() {
		System.out.println("Group relations: ");
		int rIndex;
		for (int i = 0; i < relations.size(); i++) {
			rIndex = i + 1;
			System.out.println("r" + rIndex + " = " + relations.get(i)); 
		}
	}

	public void reduceWord(List<String> word) {

		boolean changed;
		do {
			changed = false;
			if (word.size() <= 1) break;

			for (int i = 1; i < word.size(); i++) {
				if ( word.get(i-1).equals(StringFunctions.changeCase(word.get(i))) ) {
					word.remove(i);
					word.remove(i-1);
					changed = true;
				} 
			}
		} while (changed);
	}
}
