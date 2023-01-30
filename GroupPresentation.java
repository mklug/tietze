package tietze;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class GroupPresentation {
	
	private final Set<String> generators;
	private final Set<List<String>> relations;


	public GroupPresentation(Set<String> generators, Set<List<String>> relations) {
		this.generators = generators;

		for (List<String> rel : relations) {
			reduceWord(rel);
		//	if re lis empty delete...
		}
		this.relations = relations;
	}

	public Set<String> getGenerators() {
		return generators;
	}

	public Set<List<String>> getRelations() {
		return relations;	
	}


	public void printGroup() {
		printGenerators();
		printRelations();
	}

	public void printGenerators() {
		System.out.println("Group generators: ");
		System.out.println(generators);
	}

	public void printRelations() {
		System.out.println("Group relations: ");
		System.out.println(relations);
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
