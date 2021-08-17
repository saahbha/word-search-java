import java.util.Hashtable;
import java.util.Set;

public class BoyerMoore
{
	/**
	 * The last Occurrence function
	 * @param S
	 * @return
	 */
	public static Hashtable<Character, Integer> lastOccurrenceFunction(String S) {
		Hashtable<Character, Integer> lastOccurrenceFunction = new Hashtable<Character, Integer>();
		for (int i = S.length() - 1; i >= 0; i--) {
			char current = S.charAt(i);
			if (!lastOccurrenceFunction.containsKey(current)) {
				lastOccurrenceFunction.put(current, i);
			}
		}
		return lastOccurrenceFunction;
	}

	/**
	 * Run the Boyer Moore Pattern Matching
	 * @param T
	 * @param P
	 * @return
	 */
	public static int find(String T, String P) {
		int n = T.length();
		int m = P.length();
		if (m > n) return -1;
		Hashtable<Character, Integer> L = lastOccurrenceFunction(P);

		int i = m - 1;
		int j = m - 1;
		do {
			//match
			char t = T.charAt(i);
			char p = P.charAt(j);
			if (t == p) {
				if (j == 0) return i;
				else {
					i--;
					j--;
				}
			}
			//no match
			else {
				int l = -1;
				if (L.containsKey(t)) l = L.get(t);
				i = i + m - Math.min(j, 1 + l);
				j = m - 1;
			}
		} while (!(i > n - 1));

		return -1;
	}



}
