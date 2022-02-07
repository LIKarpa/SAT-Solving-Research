package dimacsFormattedSet;
import java.util.HashMap;
import java.util.Set;

public class dlcsSelection implements LiteralSelector {
	
	/* Counts the number of times a literal appears. Returns the literal, x, for which 
	 * occurrences x + occurrences -x is the largest. */
	public String getLiteral(Set<Set<String>> form) {
		// Maps a positive literal to the number of times it appears.
		HashMap<String, Integer> numPosLiteral = new HashMap<String, Integer>();
		
		// Populates a map from positive literals to the number of times it or its negation appears
		for (Set<String> conj : form) {
			for (String literal : conj) {
				if (literal.charAt(0) != '-') {
					if (numPosLiteral.containsKey(literal)) {
						int current = numPosLiteral.get(literal);
						numPosLiteral.put(literal, current + 1);
					} else {
						numPosLiteral.put(literal, 1);
					}
				} else {
					if (numPosLiteral.containsKey(Driver.negate(literal))) {
						int current = numPosLiteral.get(Driver.negate(literal));
						numPosLiteral.put(Driver.negate(literal), current + 1);
					} else {
						numPosLiteral.put(Driver.negate(literal), 1);
					}
				}
			}
		}
		
		// If no positive literals exist in the map then Stand should have taken care of the formula. 
		if (Driver.getPosLiterals(form).isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		// Returns the postive literal for which (it or its negation) appears the most times.
		int max = 0;
		String maxLit = "";
		for (String key : numPosLiteral.keySet()) {
			if (numPosLiteral.get(key) > max) {
				max = numPosLiteral.get(key);
				maxLit = key;
			}
		}
		return maxLit;
		
		
	}
}
