package dimacsFormattedSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class shortestClauseSelection implements LiteralSelector{
	
	/* Returns a literal from one of the shortest clauses. */
	public String getLiteral(Set<Set<String>> form) {
		ArrayList<ArrayList<String>> formList = Driver.formToList(form);
		ArrayList<String> conjunct = new ArrayList<String>();
		int minLen = -1;
		
		for (ArrayList<String> conj : formList) {
			if (conj.isEmpty()) {
				continue;
			}
			if ((conj.size() < minLen && conj.size() > 0) || minLen == -1) {
				minLen = conj.size();
				conjunct = conj;
			}
		}
		
		if (conjunct.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return conjunct.get(0);
		
	}
}
