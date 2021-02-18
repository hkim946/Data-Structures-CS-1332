import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Hye Lim Kim
 * @version 1.0
 * @userid hkim946
 * @GTID 903197983
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern does not exist");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text does not exist");
        }
        List<Integer> indexList = new ArrayList<>();
        for (int t = 0; t <= text.length() - pattern.length(); t++) {
            int i = 0;
            while (i < pattern.length()) {
                if (comparator.compare(pattern.charAt(i), text.charAt(i + t)) == 0) {
                    if (i < pattern.length() - 1) {
                        i++;
                    } else {
                        indexList.add(t);
                        i++;
                    }
                } else {
                    break;
                }
            }
        } return indexList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern does not exist.");
        }
        int[] failureTable = new int[pattern.length()];
        int i = 0; //prefix index
        int j = 1; //query index

        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(j), pattern.charAt(i)) == 0) {
                failureTable[j++] = ++i;
            } else if (i == 0) {
                failureTable[j++] = i;
            } else {
                i = failureTable[i - 1];
            }
        }
        return failureTable;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern does not exist.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text does not exist.");
        }

        List<Integer> indexList = new ArrayList<>();
        if (text.length() != 0 && pattern.length() <= text.length()) {
            int[] failureTable = buildFailureTable(pattern, comparator);
            int i = 0; //text index
            int j = 0; //pattern index
            int m = pattern.length();
            int n = text.length();

            while (i < n) {
                if (i + (m - j) <= n && comparator.compare(text.charAt(i), pattern.charAt(j)) == 0) {
                    if (j == m - 1) { //at last letter of pattern
                        indexList.add(i - j);
                        j = failureTable[j];
                        i++;
                    } else {
                        i++;
                        j++;
                    }
                } else if (j == 0) {
                    i++;
                } else {
                    j = failureTable[j - 1];
                }
            }
        }
        return indexList;
    }



    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern does not exist.");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern does not exist.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text does not exist.");
        }
        ArrayList<Integer> indexList = new ArrayList<>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;
        while (i < text.length()) {
            char current = text.charAt(i);
            if (comparator.compare(current, pattern.charAt(j)) == 0) {
                if (j == 0) {
                    indexList.add(i);
                    i += pattern.length();
                    j = pattern.length() - 1;
                } else {
                    i--;
                    j--;
                }
            } else {
                i += pattern.length() - Math.min(j, 1
                        + lastTable.getOrDefault(current, -1));
                j = pattern.length() - 1;
            }
        }
        return indexList;
    }
}
