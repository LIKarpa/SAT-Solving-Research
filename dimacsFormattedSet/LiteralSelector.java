package dimacsFormattedSet;

import java.util.ArrayList;
import java.util.Set;

public interface LiteralSelector {
	public String getLiteral(Set<Set<String>> form);
}
