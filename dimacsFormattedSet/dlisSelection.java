package dimacsFormattedSet;
import java.util.HashMap;
import java.util.Set;

public class dlisSelection implements LiteralSelector {
	
	/* Counts the number of times a positive literal appears. Returns the positive literal, x, for which 
	 * occurences of x is the largest. */
	public String getLiteral(Set<Set<String>> form) {
		// Maps a positive literal to the number of times it appears.
		HashMap<String, Integer> numPosLiteral = new HashMap<String, Integer>();
		
		// Populates a map from positive literals to the number of times it appears
		for (Set<String> conj : form) {
			for (String literal : conj) {
				if (literal.charAt(0) != '-') {
					if (numPosLiteral.containsKey(literal)) {
						int current = numPosLiteral.get(literal);
						numPosLiteral.put(literal, current + 1);
					} else {
						numPosLiteral.put(literal, 1);
					}
				} 
			}
		}
		
		// If no positive literals exist then Stand should have taken care of the formula. 
		if (Driver.getPosLiterals(form).isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		// Returns the postive literal that appears the most times.
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
