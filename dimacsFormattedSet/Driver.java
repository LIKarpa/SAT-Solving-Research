package dimacsFormattedSet;

import java.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner; 
public class Driver {
	
	static HashSet<Set<String>> YES = (new HashSet<Set<String>>());
	static HashSet<String> ignore1 = new HashSet<String>();
	static boolean ignore2 = ignore1.add("T");
	static boolean ignore3 = YES.add(ignore1);
	static HashSet<Set<String>> NO = (new HashSet<Set<String>>());
	static HashSet<String> ignore4 = new HashSet<String>();
	static boolean ignore5 = ignore4.add("F");
	static boolean ignore6 = NO.add(ignore4);
	
	public static void main(String[] args) {
		/* Example usage for collecting experimental data. */
		ufDPPLExperiment("/Users/Liron/Downloads/uf20-91/");
		uufDPPLExperiment("/Users/Liron/Downloads/UUF50.218.1000/");
		ufHAMExperiment("/Users/Liron/Downloads/uf20-91/");
		ufRandomHAMExperiment("/Users/Liron/Downloads/uf20-91/");
		
		
	}
	
	/* ---------------- Data Collection Methods ---------------- */
	
	/* Reads in a satisfiable uniform random 3-SAT formula (files starting with uf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for the chosen DPPL literal selector to satisfy a single formula.  
	 * Repeats this for a specified number of trials. Prints "BAD" if the formula was marked
	 * as unsatisfiable.*/
	private static void ufDPPLExperiment(String filepath) {
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		/* The literal selector is set here. */
		LiteralSelector select = new dlcsSelection();
		File uf = new File(filepath);
		String[] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (!Driver.DPPL(formula, emptyAssign, select)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Reads in a unsatisfiable uniform random 3-SAT formula (files starting with uuf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for the chosen DPPL literal selector to satisfy a single formula.  
	 * Repeats this for a specified number of trials. Prints "BAD" if the formula was marked
	 * as satisfiable. */
	private static void uufDPPLExperiment(String filepath) {
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		/* The literal selector is set here. */
		LiteralSelector select = new dlcsSelection();
		File uf = new File(filepath);
		String [] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (Driver.DPPL(formula, emptyAssign, select)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Reads in a satisfiable uniform random 3-SAT formula (files starting with uf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for fullHAM to satisfy a single formula. Repeats this for a specified number of trials. 
	 * Prints "BAD" if the formula was marked as unsatisfiable.*/
	private static void ufHAMExperiment(String filepath) {
		File uf = new File(filepath);
		String [] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (!Driver.fullHAMAlg(formula)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Reads in a satisfiable uniform random 3-SAT formula (files starting with uuf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for fullHAM to output unsatisfiable for a given formula. Repeats this for a specified 
	 * number of trials. Prints "BAD" if the formula was marked as satisfiable.*/
	private static void uufHAMExperiment(String filepath) {
		File uf = new File(filepath);
		String [] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (Driver.fullHAMAlg(formula)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Reads in a satisfiable uniform random 3-SAT formula (files starting with uf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for random HAM to satisfy a single formula. Repeats this for a specified number of trials. 
	 * Prints "BAD" if the formula was marked as unsatisfiable.*/
	private static void ufRandomHAMExperiment(String filepath) {
		File uf = new File(filepath);
		String [] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (!Driver.randInit(formula)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Reads in a unsatisfiable uniform random 3-SAT formula (files starting with uuf) from the SATLIB
	 * benchmark problem https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html . Prints out the average time it 
	 * took for random HAM to output unsatisfiable for a given formula.  
	 * Repeats this for a specified number of trials. Prints "BAD" if the formula was marked
	 * as satisfiable. */
	private static void uufRandomHAMExperiment(String filepath) {
		File uf = new File(filepath);
		String [] list = uf.list();
		int trials = 4;
		
		for (int k = 1; k <= trials; k++) {
			/* Iterates through the folder to run each formula file */
			long start = System.currentTimeMillis();
			for (String filename : list) {
				Set<Set<String>> formula = Driver.fileToFormula(filepath + filename);
			
				if (Driver.randInit(formula)) {
					System.out.println("BAD");
					return;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println((double)(end-start)/(double)list.length);
		}
	}
	
	/* Produces data on how increasing the number of clauses 
	 * increases the in practice runtime of the DLCS DPPL variation. 
	 * DLCS DPPL was chosen because it appeared to be the fastest. 
	 * I choose numForms formulas from the uf200-860 folder from the SATLIB 
	 * database. For each formula, I try DLCS DPPL on subformulas 
	 * of different sizes. This way we control for the sample
	 * size of variables, isolating the effect of increasing the 
	 * clauses alone. There is some sampling bias because
	 * the formulas in uf200-860 are generated to be in the 
	 * phase transition region. So we can expect subformulas to be 
	 * relatively easy to solve. This method is to evaluate how 
	 * fast the increase in difficulty occurs. */
	private static void clauseVSRuntimeExperiment() {
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		/* The literal selector is set here. */
		LiteralSelector select = new dlcsSelection();
		File uf = new File("/Users/Liron/Downloads/uf200-860/");
		String [] list = uf.list();
		int i = 1, numForms = 10;
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf200-860/" + filename);
			/* Stops after numForms formulas have been processed */
			if (i++ == numForms) {
				break;
			}
			
			Set<Set<String>> subformula = new HashSet<Set<String>>();
			/* Computed runtime for formulas of size 0, 20, 40, 60, ...
			 * incrementing by 20 each iteration. It stops at 840 because
			 * 860 was not computing fast enough. */
			for (int j = 0; j <= 840; j+=20) {
				
				/* Populates the sub-formula*/
				int m = 0;
				subformula = new HashSet<Set<String>>();
				for (Set<String> clause : formula) {
					if (m++ == j) {
						break;
					}
					subformula.add(new HashSet<String>(clause));
				}
				
				long start = System.currentTimeMillis();
				if (!Driver.DPPL(subformula, emptyAssign, select)) {
					System.out.println("BAD");
					return;
				}
				long end = System.currentTimeMillis();
				
				/* Prints the number of clauses in current sub-formula */
				System.out.println(j);
				/* Prints how long it took to find a satisfiable assignment for the 
				 * above number of clauses. */
				System.out.println(end-start);
			}
			
		}
	}
	
	/* Prints data on how runtime changes as the sample space of possible
	 * variables increases for a fixed formula size. For 10 formulas from the
	 * given filepath, a sub-formula of size at most 500 is generated.
	 * Then the runtime for computing a satisfiable assignment for 
	 * the sub-formula is printed out. */
	private static void VariableVSRuntimeExperiment(String Filepath) {
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		/* The literal selector is set here. */
		LiteralSelector select = new dlcsSelection();
		File uf = new File(Filepath);
		String [] list = uf.list();
		int i = 0, trials = 10;
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula(Filepath + filename);
			/* Does 10 trials */
			if (i++ == trials) {
				break;
			}
			
			/* Generates a sub-formula of size 500 */
			Set<Set<String>> subformula = new HashSet<Set<String>>();
			int m = 1;
			subformula = new HashSet<Set<String>>();
			for (Set<String> clause : formula) {
				if (m++ == 500) {
					break;
				}
				subformula.add(new HashSet<String>(clause));
			}
			
			/* Computes runtime*/
			long start = System.currentTimeMillis();
			if (!Driver.DPPL(subformula, emptyAssign, select)) {
				System.out.println("BAD");
				return;
			}
			long end = System.currentTimeMillis();
			System.out.println(end-start);	
		}
	}
		
	
	
	/* ---------------- File Processing Methods ---------------- */
	
	/* Converts a DIMACS formatted file into a formula (set of sets). 
	 * Details can be found here:
	 * https://www.cs.ubc.ca/~hoos/SATLIB/benchm.html */
	public static HashSet<Set<String>> fileToFormula(String filename) {
		HashSet<Set<String>> formula = new HashSet<Set<String>>();
		boolean ufFormat = true;
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	if (!data.contains("%") && !data.isEmpty() && data.charAt(0) != 'c' && data.charAt(0) != 'p') {
		    		HashSet<String> clause = new HashSet<String>();
		    		String [] literals;
		    		if (ufFormat) {
		    			literals = data.substring(1).split(" ");
		    			ufFormat = false;
		    		} else {
		    			literals = data.split(" ");
		    		}
		    		
		    		
		    		for (String literal : literals) {
		    			if (!literal.equals("0")) {
		    				clause.add(literal);
		    			}
		    		}
		    		formula.add(clause);
		    	}
		    }
		    myReader.close();
		    
		} catch (FileNotFoundException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		}
		return formula;
	}
	
	
	/* ---------------- General Helper Methods below ---------------- */
	
	/* Converts a set to an array list*/
	public static <T> ArrayList<T> toList(Set<T> set) {
		return new ArrayList<T>(set);
	}
	
	/* Converts a formula to an array list representation. */
	public static ArrayList<ArrayList<String>> formToList(Set<Set<String>> form) {
		ArrayList<ArrayList<String>> formList = new ArrayList<ArrayList<String>>();
		
		for (Set<String> conj : form) {
			formList.add(toList(conj));
		}
		return formList;
	}
	
	/* Makes a deep copy of a formula */
	public static Set<Set<String>> formCopy(Set<Set<String>> form) {
		Set<Set<String>> newForm = new HashSet<Set<String>>();
		for (Set<String> conj : form) {
			Set<String> newConj = new HashSet<String>();
			for (String literal : conj) {
				newConj.add(literal);
			}
			newForm.add(newConj);
		}
		return newForm;
	}
	
	/* Will check whether the string is in CNF and if so convert it to
	 * set notation, returning the set representation. */
	public static Set<Set<String>> convertToSet(String form) throws Exception {
		/* Will implement later if desired. */
		throw new Exception("Unimplemented");
	}
	
	
	/* Checks to make sure that the conjuncts in the form set only contains literals. 
	 * Definitions are given in stand comment. {{"T"}} and {{"F"}} also are valid 
	 * because they will be stand in for YES NO respectively from stand method.*/
	public static boolean checkForm(Set<Set<String>> form) {
		String literal_regex = "^-?\\d+$";
		Pattern literal_pattern = Pattern.compile(literal_regex);
		
		for (Set<String> conjunct: form ) {
			for (String literal: conjunct) {
				Matcher m = literal_pattern.matcher(literal);
				if (!m.find()) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/* Returns a simplified negated literal. ex) negate("a") is "~a" and 
	 * negate("~a") is a. */
	public static String negate(String literal) {
		String literal_regex1 = "^\\d+$", literal_regex2 = "^-?\\d+$";
		Pattern literal_pattern1 = Pattern.compile(literal_regex1), 
				literal_pattern2 = Pattern.compile(literal_regex2);
		Matcher m1 = literal_pattern1.matcher(literal);
		Matcher m2 = literal_pattern2.matcher(literal);
		
		if (m1.find()) {
			return "-"+literal;
		} else {
			if (m2.find()) {
				return "" + literal.substring(1);
			} else {
				throw new IllegalArgumentException("Not a literal!");
			}
		}
	}
	
	/* Assumes all conjuncts are of size two or less. Removes all tautologies from
	 * the given formula and returns the updated formula. */
	public static Set<Set<String>> removeTaut(Set<Set<String>> form) {
		Set<Set<String>> tempForm = new HashSet<Set<String>>(form);
		for (Set<String> conjunct : tempForm) {
			ArrayList<String> conjList = toList(conjunct);
			if (conjList.size() == 2 && conjList.get(0).equals(negate(conjList.get(1)))) {
				form.remove(conjunct);
			}
		}
		return form;
	}
	
	/* Returns a Hash Set of all the positive literals in a formula. */
	public static HashSet<String> getPosLiterals(Set<Set<String>> form) {
		HashSet<String> posLits = new HashSet<String>();
		
		for (Set<String> conj : form) {
			for (String literal : conj) {
				if (literal.charAt(0) != '-') {
					posLits.add(literal);
				}
			}
		}
		return posLits;
	}
	
	
	/* ---------------- 2-SAT methods below ---------------- */
	
	
	/* Assumes all conjunct have at most 2 literals. Converts CNF form to INF graph. Each 
	 * conjunct (A V B) is converted to the implication ~A->B and ~B->A. This corresponds 
	 * to the directed edge (~A,B) and its contrapositive (~B, A). The graph is returned in 
	 * its adjacency list representation. */
	public static HashMap<String, ArrayList<String>> toGraph(Set<Set<String>> form) {
		HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
		
		removeTaut(form);
		for (Set<String> conjunct: form) {
			ArrayList<String> conjList = toList(conjunct);
			if (conjunct.isEmpty()) {
				//nothing is added to graph
			} else if (conjunct.size() == 1) {
				if (graph.get(conjList.get(0)) == null) {
					graph.put(conjList.get(0), new ArrayList<String>());
				}
			} else {
				String literal1 = "", literal2 = "";
				
				for (int i = 0; i <= 1; i++) {
					if (i == 0) {
						literal1 = conjList.get(0);
					} else {
						literal2 = conjList.get(1);
					}
				}
				if (graph.get(negate(literal1)) == null) {
					ArrayList<String> adjacent = (new ArrayList<String>());
					adjacent.add(literal2);
					graph.put(negate(literal1), adjacent);
					
				} else {
					graph.get(negate(literal1)).add(literal2);
					
				}
				//adds literal2 to vertex list (even if it will have out degree 0).
				if (graph.get(literal2) == null) {
					graph.put(literal2, new ArrayList<String>());
				}
			
				if (graph.get(negate(literal2)) == null) {
					ArrayList<String> adjacent = (new ArrayList<String>());
					adjacent.add(literal1);
					graph.put(negate(literal2), adjacent);
				} else {
					graph.get(negate(literal2)).add(literal1);
					
				}
				//adds literal1 to vertex list (even if it will have out degree 0).
				if (graph.get(literal1) == null) {
					graph.put(literal1, new ArrayList<String>());
				}
			}
		}
		return graph;
	}
	
	/* Assumes all vertices are keys in the graph. Returns the transpose graph. */
	public static HashMap<String, ArrayList<String>> graphTranspose(HashMap<String, ArrayList<String>> graph) {
		HashMap<String, ArrayList<String>> transpose = new HashMap<String, ArrayList<String>>();
		
		for (String key: graph.keySet()) {
			if (!transpose.containsKey(key)) {
				transpose.put(key, new ArrayList<String>());
			} 
			for (String literal : graph.get(key)) {
				if (!transpose.containsKey(literal)) {
					transpose.put(literal, new ArrayList<String>());
				} 
				transpose.get(literal).add(key);
			}	
		}
		return transpose;
	}
	
	
	/* Depth first search on graph while storing the order of traversal in a stack. */
	public static void dfs(HashMap<String, ArrayList<String>> graph, ArrayList<String> vertexIndices,
			int vert, boolean[] visited, ArrayList<String> comp) {
		
		if (graph.keySet().isEmpty()) {
			return ;
		}
		visited[vert] = true;
		for (String literal: graph.get(vertexIndices.get(vert))) {
			if (!visited[vertexIndices.indexOf(literal)]) {
				dfs(graph, vertexIndices, vertexIndices.indexOf(literal), visited, comp);
			}
		}
		comp.add(0, vertexIndices.get(vert));
	}
	
	/* Populates the order of traversal of a depth first search of given graph. */
	public static ArrayList<String> fillOrder(HashMap<String, ArrayList<String>> graph, boolean[] visited) {
		ArrayList<String> order = new ArrayList<String>(), graphVertices = Driver.toList(graph.keySet());
		
		// Sorting is to set convention of which vertex traversal starts at in testing
		Collections.sort(graphVertices);
		for (String literal: graphVertices) {
			if (!visited[graphVertices.indexOf(literal)]) {
				dfs(graph, graphVertices, graphVertices.indexOf(literal), visited, order);
			}
		}
		return order;
	}
	
	/* Kosaraju-Sharir's algorithm to find strongly connected components (SCC) of a directed graph. 
	 * Reference: https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm 
	 * and https://www.sanfoundry.com/java-program-kosaraju-algorithm/ */
	public static ArrayList<ArrayList<String>> getSCC(HashMap<String, ArrayList<String>> graph) {
		boolean[] visited = new boolean[graph.keySet().size()];
		ArrayList<String> order = fillOrder(graph, visited), graphVertices = Driver.toList(graph.keySet());;
		HashMap<String, ArrayList<String>> transposeGraph = graphTranspose(graph);
		
		// Sorting is to set convention of which vertex traversal starts at in testing
		Collections.sort(graphVertices);
		// Clears visited
		visited = new boolean[graph.keySet().size()];
		
		/* Gets the strongly connected components */
		ArrayList<ArrayList<String>> SCComps = new ArrayList<>();
		for (int i = 0; i < order.size(); i++) {
			String literal = order.get(i);
			int literalIndex = graphVertices.indexOf(literal);
			
			if (!visited[literalIndex]) {
				ArrayList<String> comp = new ArrayList<String>();
				dfs(transposeGraph, graphVertices, literalIndex, visited, comp);
				SCComps.add(comp);
			}
		}
		return SCComps;
	}
	
	/* twoSAT algorithm only works when all conjuncts have 
	 * exactly two literals. To account for this I preprocess the
	 * formula by getting rid of unit clauses. If {L} is a unit clause
	 * and C is a conjunct with L then eliminate C because it
	 * evaluates to true. If C is a conjunct with ~L then
	 * ~L can be eliminated from the conjunct (unless C is a unit clause
	 * in which case throws illegal argument exception so twoSAT knows
	 * to return false). */
	public static Set<Set<String>> twoSATPreprocess(Set<Set<String>> form) {
		HashSet<Set<String>> newForm = new HashSet<Set<String>>(form);
		boolean unitClauseExists = true;
		
		while (unitClauseExists) {
			for (Set<String> conjunct: newForm) {
				if (conjunct.size() == 1) {
					String literal = "", negLiteral = "";
					// Only loops through one element
					for (String lit : conjunct) {
						literal = lit;
						negLiteral = negate(lit);
					}
					// Clears conjuncts with the literal
					for (Set<String> conj: newForm) {
						if (conj.contains(literal)) {
							// Note that toGraph algorithm can take care of empty conjuncts.
							conj.clear();
						}
						if (conj.contains(negLiteral)) {
							//
							if (conj.size() == 1) {
								throw new IllegalArgumentException();
							}
							conj.remove(negLiteral);
						}
					}
				}
			}
			/* After some literals are removed from size two conjuncts 
			 * they might be left as unit clauses. I want to keep 
			 * repeating this process until all that is left is
			 * empty clauses or size two clauses which toGraph can handle.*/
			unitClauseExists = false;
			for (Set<String> conj: newForm) {
				if (conj.size() == 1) {
					unitClauseExists = true;
				}
			} 
		}
		return newForm;
	}
	
	
	/* Reference: https://cp-algorithms.com/graph/2SAT.html 
	 * Given a formula in CNF where all conjuncts are of length at most two
	 * returns whether that formula is satisfiable or not. */
	public static boolean twoSAT(Set<Set<String>> form) {
		ArrayList<ArrayList<String>> sccs; 
		
		try {
			sccs = getSCC(toGraph(twoSATPreprocess(form)));
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		// Checks whether a literal and its negation appear in the same scc
		for (ArrayList<String> comp : sccs) {
			for (String literal : comp) {
				if (comp.contains(negate(literal))) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/* ---------------- SAT methods ---------------- */
	
	/* Returns a pureLiteral (positive or negative) if one exists. 
	 * Returns the empty string otherwise. Input is a set of all the literals
	 * that appear in the string. A literal is pure if its negation
	 * does not appear in the set of literals. Assumes set of strings
	 * only contains literals. */
	public static String getPureLiteral(Set<String> literals) {
		for (String lit : literals) {
			String neg = negate(lit);
			if (!literals.contains(neg)) {
				return lit;
			}
		}
		return "";
	}
	
	/* Given a formula and a literal, a new formula is returned that
	 * is a simplified version of the original formula where all instances
	 * of literal are true and the negation is false. This means
	 * every conjunct with literal in it is removed and every instance
	 * of its negation is deleted (unless the negation is in a unit clause
	 * in which case NO is returned). *NOTE*, the method returns a modified version
	 * of the parameter. The actual inputed formula is modified*/
	public static Set<Set<String>> reduce(Set<Set<String>> form, String literal) {
		String negLiteral = negate(literal);
		// Clears conjuncts with the literal
		for (Set<String> conj: form) {
			if (conj.contains(literal)) {
				conj.clear();
			}
			if (conj.contains(negLiteral)) {
					if (conj.size() == 1) {
						return NO;
					}
				conj.remove(negLiteral);
			}
		}
		
		return form;
	}
	
	/* 	Evaluates the given formula under the given assignment. 
	 *  Returns YES if every conjunct evaluates to true and NO if some conjunct evaluates 
	 *  to false. Returns the formula if the assignment is not a total function
	 *  on the set of literals in the formula. The procedure is as follows.
	 *  For each literal that is assigned to true, reduce the formula accordingly. The
	 *  formula is empty by the end iff formula evaluated to true. 
	 *  Proof. If not empty then all remaining literals are assigned false 
	 *  so all remaining conjuncts evaluate to false. If formula is empty then every
	 *  conjunct had some disjunct which evaluated to true (by construction of the reduce algorithm).
	 *  Note there is no consistency check for assign. By design of other algorithms, assign should
	 *  map negations to different truth values. */
	public static Set<Set<String>> evaluate(Set<Set<String>> form, HashMap<String, String> assign) {
		HashSet<String> literals = new HashSet<String>(), trueKeys = new HashSet<String>();
		Set<Set<String>> newForm = new HashSet<Set<String>>(form);
		// Gets all the literals in the formula. 
		for (Set<String> conj : form) {
			literals.addAll(conj);
		}
		
		/* If the assignment is not a complete assignment the formula is returned. 
		 * If not, then either YES or NO is returned depending on whether
		 * all conjuncts have a literal which was assigned to true
		 * or whether some conjunct is all false, respectively. */
		if (!assign.keySet().containsAll(literals)) {
			return form;
		} else {
			// Populates trueKeys with all the keys that map to true.
			assign.forEach((k, v) -> {if (v.equals("T")) {trueKeys.add(k);}});
			// Does the reductions
			for (String key : trueKeys) {
				newForm = reduce(newForm, key);
				if (newForm.equals(NO)) {
					return NO;
				}
			}
			// Checks if conjuncts are empty
			for (Set<String> conjunct : newForm) {
				if (!conjunct.isEmpty()) {
					return NO;
				} 
			}
			return YES;
		}
	}
	
	
	/* Takes in a formula and a partial assignment.
	 * form represents a formula in conjunctive normal form (CNF). It is a set of sets that 
	 * represent the conjuncts of the CNF. Each conjunct contains the literals that
	 * would be the disjuncts of the conjuncts. A literal is a lower case letter or 
	 * its negation, denoted by ~. Assign maps strings (literals) to strings where the 
	 * range is {"T", "F"}. The reason for using strings over booleans is to make evaluation 
	 * easier. The function returns YES if the formula is satisfiable, NO if not, or an easier 
	 * formula if unknown. To represent a return of YES, the set {{"T"}} is returned and to represent
	 * a return of NO, the set {{"F"}} is returned. */
	public static Set<Set<String>> stand(Set<Set<String>> form, HashMap<String, String> assign) {
		Set<Set<String>> newForm = new HashSet<Set<String>>(form);
		boolean run2SAT = true, unitClauseExists = false;
		Set<String> literals = new HashSet<String>();
		String unitLiteral = "";
		
		// Checks to make sure all strings in conjuncts are valid literals.
		if (checkForm(form) == false) {
			throw new IllegalArgumentException("Not a formula!");
		}
		/* Makes run2SAT false if some conjunct has more than two literals and 
		 * unitClauseExists true if there is a unit clause. Also creates
		 * the set of all variables. */
		for (Set<String> conjunct: form ) {
			if (conjunct.size() > 2) {
				run2SAT = false;
			}
			if (conjunct.size() == 1) {
				unitClauseExists = true;
				// Only one iteration
				for (String lit: conjunct) {
					unitLiteral = lit;
					break;
				}
			}
			literals.addAll(conjunct);
		}
		
		/* This is the core of the stand algorithm that reduces the formula. 
		 * I combine the negPURE and the posPURE cases because my negate
		 * method handles ~a and a as simply literals that are logically
		 * equivalent to negations of one another. */
		String pureLiteral = getPureLiteral(literals);
		boolean pureLiteralExists = !pureLiteral.equals("");
		while (run2SAT || unitClauseExists || pureLiteralExists) {
			if (run2SAT) {
				return twoSAT(form)? YES : NO;
			} else if (unitClauseExists) {
				/*assign.put(unitLiteral, "T");
				assign.put(negate(unitLiteral), "F");*/
				newForm = reduce(newForm, unitLiteral);
			} else if (pureLiteralExists) {
				/*assign.put(pureLiteral, "T");
				assign.put(negate(pureLiteral), "F");*/
				newForm = reduce(newForm, pureLiteral);
			}
			
			/* The check for YES and NO are not in original algorithm 
			 * but are needed in case reduce returns YES OR NO
			 * which is needed because reduction on a & ~a
			 * with a assigned to true should return NO since
			 * ~a is in a unit clause so can't be eliminated. */
			if (newForm.equals(YES)) {
				return YES;
			} else if (newForm.equals(NO)) {
				return NO;
			} else {
				/* Repeats code from before the while loop. It would
				 * be more code to create a separate function for each 
				 * case. */
				literals.clear();
				run2SAT = true;
				unitClauseExists = false;
				for (Set<String> conjunct: newForm) {
					if (conjunct.size() > 2) {
						run2SAT = false;
					}
					if (conjunct.size() == 1) {
						unitClauseExists = true;
						// Only one iteration
						for (String lit: conjunct) {
							unitLiteral = lit;
							break;
						}
					}
					literals.addAll(conjunct);
				}
				pureLiteral = getPureLiteral(literals);
				pureLiteralExists = !pureLiteral.equals("");
			}
		}
		
		
		return newForm;
		
	}
	
	/* Takes in a formula, a partial assignment, and a literal selector algorithm. 
	 * The literal selector algorithm has an implementation of a particular way to select
	 * a literal from those unassigned in assign in a more efficient way than random. 
	 * The method returns a boolean of true if the formula is satisfiable and false otherwise.
	 * stand is the base case for the algorithm. The recursive step is testing
	 * out for a particular literal true and false to see if either option leads to
	 * a satisfiable assignment. If a literal cannot be true or false then the formula
	 * is unsatisfiable. */
	public static boolean DPPL(Set<Set<String>> form, HashMap<String, String> assign, LiteralSelector obj) {
		Set<Set<String>> standResult = stand(form, assign), tempResult;
		
		if (standResult.equals(YES)) {
			return true;
		} else if (standResult.equals(NO)) {
			return false;
		} else {
			String literal = obj.getLiteral(form);
			
			assign.put(literal, "T");
			assign.put(negate(literal), "F");
			tempResult = formCopy(standResult);
			reduce(tempResult, literal);
			
			if (DPPL(tempResult, assign, obj)) {
				return true;
			} else {
				assign.put(literal, "F");
				assign.put(negate(literal), "T");
				tempResult = formCopy(standResult);
				reduce(tempResult, negate(literal));
				if (DPPL(tempResult, assign, obj)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	static int temp = 0;
	
	
	/* Takes in a conjunct and a total assignment on that conjunct.
	 * Returns true if the given set of literals evaluates to true (at least one
	 * of the literals is assigned to true) and false otherwise.  */
	public static boolean evalConj(Set<String> conj, HashMap<String, String> assign) {
		
		if (conj.isEmpty()) {
			return true;
		}
		
		for (String literal : conj) {
			if (assign.get(literal).equals("T")) {
				return true;
			}
		}
		return false;
	}
	
	/* Takes in a formula and a total assignment on that formula.
	 * Returns true if the given set of literals evaluates to true (at least one
	 * of the literals is assigned to true) and false otherwise.  */
	public static boolean evalForm(Set<Set<String>> form, HashMap<String, String> assign) {
		
		for (Set<String> conj : form) {
			if (!evalConj(conj, assign)) {
				return false;
			}
		}
		return true;
	}
	
	
	/* Given a formula, assignment, and hamming bound, returns whether the
	 * 3SAT formula is satisfiable or not. For details and explanation 
	 * see "The Satisfiability Problem: Algorithms and Analyses" by 
	 * Schoning and Toran. */
	public static boolean hamAlg(Set<Set<String>> form, HashMap<String, String> assign,
			int bound) {
		
		// ArrayList because order of conjuncts should be fixed for simplicity of testing.
		ArrayList<Set<String>> conjList = new ArrayList<Set<String>>(form);
		Set<String> changeConj = new HashSet<String>();
		boolean twoConjExists = false;
		
		/* If the given assignment satisfies the formula then return true. */
		if (evalForm(form, assign)) {
			return true;
		}
		
		/* If hamming bound reaches 0 then return false because
		 * that path through the hamming ball has been exhausted. */
		if (bound <= 0) {
			return false;
		}
		
		/* Checks if there is a conjunct of size 2 and if so
		 * stores it. Otherwise stores the last size 3 conjunct. 
		 * IMPORTANT: There should not be a conjunct of size 1 
		 * because the method calling hamAlg should have 
		 * used STAND to get rid of unit clauses. */
		for (Set<String> conj : conjList) {
			if (!evalConj(conj, assign)) {
				if (conj.size() == 2) {
					changeConj = conj;
					twoConjExists = true;
					break;
				} else if (conj.size() == 3) {
					changeConj = conj;
				} else if (conj.size() == 0) {		
				} else {
					throw new IllegalArgumentException("Conjuncts of invalid size");
				}
			}	
		}
		
		// If all conjuncts evaluated to true then the formula is satisfiable with the given assignment.
		if (changeConj.isEmpty()) {
			return true;
		} else {
			/* Attempts to change false conjuncts by trying to change 
			 * the assignment of one of the literals in that conjunct to true. 
			 * They would all be false if the conjunct was initially false. 
			 * Then the algorithm is recursively called with the new assignment. */
			ArrayList<String> literalList  = new ArrayList<String>(changeConj);
			for (String literal : literalList) {
				HashMap<String, String> newAssign = new HashMap<String, String>(assign);
				newAssign.put(literal, "T");
				newAssign.put(negate(literal), "F");
				
				if (hamAlg(form, newAssign, bound - 1)) {
					
					return true;
				}
			}
			
			return false;
		}
		
	}
	
	/* Implements the fullHAM algorithm. Given a formula returns whether the
	 * 3SAT formula is satisfiable or not. It explores the entire space
	 * of possible function transformations starting from the false assignment
	 * (all positive literals assigned false) and the true assignment
	 * (all positive literals assigned to true) with a hamming radius
	 * of n/2. These starting functions and radius form a covering code 
	 * so the method is deterministic. */
	public static boolean fullHAMAlg(Set<Set<String>> form) {
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		Set<Set<String>> standResult = stand(form, emptyAssign);
		HashMap<String, String> falseAssign = new HashMap<String, String>(), 
				trueAssign = new HashMap<String, String>();
		HashSet<String> literals = new HashSet<String>();
		
		/* Stand clears unit clauses and returns the correct result
		 * for simple cases. */
		if (standResult.equals(YES)) {
			return true;
		} else if (standResult.equals(NO)) {
			return false;
		} else {
			Set<Set<String>> copyForm1 =  Driver.formCopy(standResult),
					copyForm2 =  Driver.formCopy(standResult);
			
			for (Set<String> conj : form) {
				literals.addAll(conj);
			}
			/* Populates 0^n (falseAssign) where all pos-literals are set to false
			 * and 1^n (trueAssign) where all pos-literals are set to true */
			for (String literal : literals) {
				if (literal.charAt(0) == '-') {
					falseAssign.put(literal, "F");
					trueAssign.put(literal, "T");
				} else if (literal.charAt(0) != '-') {
					falseAssign.put(literal, "T");
					trueAssign.put(literal, "F");
				} else {
					throw new IllegalArgumentException("Invalid formula. ");
				}
			}
			
			/* HAM ball radius bound is (n+1)/2 where n is the number of pos variables.
			 * n+1 instead of n to make sure entire space is covered when n is odd. */
			if (hamAlg(copyForm1, falseAssign, ((literals.size()/2)+1)/2)) {
				return true;
			} else {
				return hamAlg(copyForm2, trueAssign, ((literals.size()/2)/2)+1);
			}
		}
	}
	
	/* Given a formula, returns a random total assignment on that formula. */
	public static HashMap<String, String> getRandomAssignment(Set<Set<String>> form) {
		HashMap<String, String> randAssign = new HashMap<String, String>();
		HashSet<String> vars = Driver.getPosLiterals(form);
		
		
		for (String literal : vars) {
			int zeroOrOne= (int)Math.round(Math.random());
			randAssign.put(literal, zeroOrOne == 1? "T" : "F");
			randAssign.put(negate(literal), zeroOrOne == 1? "F" : "T");
		}
		return randAssign;
	}
	
	/* Implements random initialization algorithm. I use a hamming distance of 1/4 because
	 * that is optimal (per Schoning and Toran). Moreover, I set my error constant to 200
	 * because that ensured no errors during testing. The algorithm is not deterministic and
	 * can technically, but very rarely, fail to give the correct answer. Because it is
	 * hard to find an efficient covering code, we start with randomly generated initial assignments
	 * and search the space of functions that are within a 1/4 hamming radius away from that assignment. */
	public static boolean randInit(Set<Set<String>> form) {
		HashMap<String, String> randAssign = new HashMap<String, String>();
		Set<Set<String>> standResult = stand(form, randAssign);
		
		randAssign.clear();
		if (standResult.equals(YES)) {
			return true;
		} else if (standResult.equals(NO)) {
			return false;
		} else {
			for (int i = 1; i <= 200; i++) {
				randAssign = getRandomAssignment(form);
				/* (Driver.getPosLiterals(form).size()/4)+1) is # of pos literals divided by 4.
				 * The plus 1 is in case the number of literals is less than 4. */ 
				if (hamAlg(form, randAssign, (Driver.getPosLiterals(form).size()/4)+1)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	/* recSevenAlg is still in testing. DO NOT USE. */
	
	/* Only for 3SAT. Takes in a formula, a partial assignment. The method returns a boolean of 
	 * true if the formula is satisfiable and false otherwise. stand is the base case for the algorithm. 
	 * The recursive step is testing out true and false values for each
	 * literal in a specific conjunct to see if any of the possibilities leads to
	 * a satisfiable assignment. If one is found true is returned. 
	 * If no assignment can satisfy the formula then the formula is unsatisfiable so false is returned.
	 * The innovation of this method is to only test those assignments that do not
	 * automatically make an entire conjunct false. */
	public static boolean recSevenAlg(Set<Set<String>> form) {
		HashMap<String, String> empty = new HashMap<String, String>();
		Set<Set<String>> standResult = stand(form, empty) , tempResult;
		System.out.println(temp++);
		if (standResult.equals(YES)) {
			return true;
		} else if (standResult.equals(NO)) {
			return false;
		} else {
			/* Computes whether a conjunct of size two exists. Also 
			 * stores that conjuncts. */
			boolean existsTwoConj = false;
			Set<String> setConj = new HashSet<String>();
			for (Set<String> conjunct : standResult) {
				if (conjunct.size() != 0) {
					setConj = conjunct;
				} else {
					continue;
				}
				if (conjunct.size() == 2) {
					existsTwoConj = true;
					break;
				}
			}
			
			ArrayList<String> listConj = new ArrayList<String>(setConj);
			if (existsTwoConj) {
				/* Case one for a conjunct of size two. Tests the 3 assignment
				 * cases that do not immediately lead to an unsuccessful assignment. 
				 * Returns false if none of the cases succeed. */
				
				// first literal true and second false
				/*assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "F");
				assign.put(negate(listConj.get(1)), "T");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, listConj.get(0)).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(1))).equals(NO)) {
					return false;
				}
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				// first literal false and second true
				/*assign.put(listConj.get(0), "F");
				assign.put(negate(listConj.get(0)), "T");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, listConj.get(1)).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(0))).equals(NO)) {
					return false;
				}
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				// both literals true
				/*assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, listConj.get(0)).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, listConj.get(1)).equals(NO)) {
					return false;
				}
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				return false;
			} else {
				/* Case two for a conjunct of size three. Tests the 7 assignment
				 * cases that do not immediately lead to an unsuccessful assignment. 
				 * Returns false if none of the cases succeed. */
				//1. T F F 
				/*assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "F");
				assign.put(negate(listConj.get(1)), "T");
				assign.put(listConj.get(2), "F");
				assign.put(negate(listConj.get(2)), "T");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, listConj.get(0)).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(2))).equals(NO)) {
					return false;
				}
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				//2. F T F
			/*	assign.put(listConj.get(0), "F");
				assign.put(negate(listConj.get(0)), "T");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");
				assign.put(listConj.get(2), "F");
				assign.put(negate(listConj.get(2)), "T");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, negate(listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, listConj.get(1)).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(2))).equals(NO)) {
					return false;
				}
				
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				//3. F F T
				/*assign.put(listConj.get(0), "F");
				assign.put(negate(listConj.get(0)), "T");
				assign.put(listConj.get(1), "F");
				assign.put(negate(listConj.get(1)), "T");
				assign.put(listConj.get(2), "T");
				assign.put(negate(listConj.get(2)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, negate(listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, listConj.get(2)).equals(NO)) {
					return false;
				}
				
				if (recSevenAlg(tempResult)) {
					return true;
				}
				//4. T T F 
				/*assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");
				assign.put(listConj.get(2), "F");
				assign.put(negate(listConj.get(2)), "T");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, (listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(2))).equals(NO)) {
					return false;
				}
				
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				//5. T F T 
				/*assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "F");
				assign.put(negate(listConj.get(1)), "T");
				assign.put(listConj.get(2), "T");
				assign.put(negate(listConj.get(2)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, (listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, negate(listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(2))).equals(NO)) {
					return false;
				}
				
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				//6. F T T 
			/*	assign.put(listConj.get(0), "F");
				assign.put(negate(listConj.get(0)), "T");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");
				assign.put(listConj.get(2), "T");
				assign.put(negate(listConj.get(2)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, negate(listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(2))).equals(NO)) {
					return false;
				}
			
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				//7. T T T 
			/*	assign.put(listConj.get(0), "T");
				assign.put(negate(listConj.get(0)), "F");
				assign.put(listConj.get(1), "T");
				assign.put(negate(listConj.get(1)), "F");
				assign.put(listConj.get(2), "T");
				assign.put(negate(listConj.get(2)), "F");*/
				tempResult = formCopy(standResult);
				if (reduce(tempResult, (listConj.get(0))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(1))).equals(NO)) {
					return false;
				}
				if (reduce(tempResult, (listConj.get(2))).equals(NO)) {
					return false;
				}
			
				if (recSevenAlg(tempResult)) {
					return true;
				} 
				return false;
			}
		}
	}

	
	
}
