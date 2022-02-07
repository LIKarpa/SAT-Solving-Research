package dimacsFormattedSet;
import java.util.ArrayList;
import java.util.Set;

public class LongestClauseSelection implements LiteralSelector {
	
	/* Returns a literal from one of the longest clauses. */
	public String getLiteral(Set<Set<String>> form) {
		ArrayList<ArrayList<String>> formList = Driver.formToList(form);
		ArrayList<String> conjunct = new ArrayList<String>();
		int maxLen = -1;
		
		for (ArrayList<String> conj : formList) {
			if (conj.size() > maxLen) {
				maxLen = conj.size();
				conjunct = conj;
			}
		}
		
		if (conjunct.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return conjunct.get(0);
		
	}
}
