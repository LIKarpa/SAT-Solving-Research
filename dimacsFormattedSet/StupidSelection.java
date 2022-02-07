package dimacsFormattedSet;
import java.util.ArrayList;
import java.util.Set;

public class StupidSelection implements LiteralSelector{
	
	/* Returns a literal from the first non-empty clause. */
	public String getLiteral(Set<Set<String>> form) {
		ArrayList<ArrayList<String>> formList = Driver.formToList(form);
		
		for (ArrayList<String> conjunct : formList) {
			if (!conjunct.isEmpty()) {
				return conjunct.get(0);
			}
		}
		
		// If nothing is returned then wierd input was given
		throw new IllegalArgumentException();
	}
}
