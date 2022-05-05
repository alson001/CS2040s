import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        // TODO: Create your TrieNode class here.
        TrieNode[] presentChars = new TrieNode[62];
        boolean isEnd;
        TrieNode(){
            isEnd = false;
            for (int i = 0; i < 62; i++)
                presentChars[i] = null;
        }
    }

    private TrieNode root;

    public Trie() {
        // TODO: Initialise a trie class here.
        root = new TrieNode();
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        // TODO
        int length = s.length();
        int index;

        TrieNode temp = root;

        for (int i = 0; i < length; i++)
        {
            index = s.charAt(i) - '0';
            if (index >= 0 && index < 17){
                index = index;
            } else if (index >= 17 && index < 42) {
                index = index - 7;
            } else if (index >= 49 && index <= 74) {
                index = index - 13;
            } else {
                return;
            }
            if (temp.presentChars[index] == null) {
                temp.presentChars[index] = new TrieNode();
            }

            temp = temp.presentChars[index];
        }
        // mark last node as leaf
        temp.isEnd = true;
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        // TODO
        int length = s.length();
        int index;
        TrieNode temp = root;
        for (int i = 0; i < length; i++)
        {
            index = s.charAt(i) - '0';
            if (index >= 0 && index < 17){
                index = index;
            } else if (index >= 17 && index < 42) {
                index = index - 7;
            } else if (index >= 49 && index <= 74) {
                index = index - 13 ;
            } else {
                return false;
            }
            if (temp.presentChars[index] == null) {
                return false;
            }
            temp = temp.presentChars[index];
        }
        // mark last node as leaf
        return temp.isEnd;
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO

        int index;
        TrieNode temp = root;
        for (int k = 0; k < s.length(); k++) {
            if (s.charAt(k) == '.') {
                // if there is '.', fill based on what is in the dictionary
                for (int l = 0; l < 62; l++){
                    if (temp.presentChars[l] != null) {
                        int index2 = l;
                        if (index2 >= 0 && index2 <= 9) {
                            index2 = index2 + 48;
                        } else if (index2 >= 10 && index2 <= 35) {
                            index2 = index2 + 55;
                        } else if (index2 > 35 && index2 <= 61) {
                            index2 = index2 + 61;
                        }
                        StringBuilder tempWord = new StringBuilder(s);
                        tempWord.setCharAt(k, (char) index2);
                        prefixSearch(tempWord.toString(), results, limit);
                    }
                }
            }

            // Traverse to the last character of prefix through the nodes
            index = s.charAt(k) - '0';
            if (index >= 17 && index < 49) {
                index = index - 7;
            } else if (index >= 49 && index <= 74) {
                index = index - 7 - 6 ;
            } else {
                return;
            }
            if (temp.presentChars[index] == null) {
                return;
            }
            temp = temp.presentChars[index];
        }

        // wordFormer() form words based on prefix
        wordFormer(s, temp, results, limit);
    }

    void wordFormer(String s, TrieNode node, ArrayList<String> results, int limit) {
        // return if size touches the limit
        if (results.size() == limit) {
            return;
        }

        // add to list if there is end signal
        if (node.isEnd) {
            results.add(s);
        }

        // traverse to search for available characters to increase size of prefix
        for (int i = 0; i < 62; i++) {
            if (node.presentChars[i] != null){
                int index = i;
                if (index >= 0 && index <= 9) {
                    index = index + 48;
                } else if (index >= 10 && index <= 35) {
                    index = index + 55;
                } else if (index > 35 && index <= 61){
                    index = index + 61;
                }
                StringBuilder temp = new StringBuilder(s);
                temp.append((char) index);
                wordFormer(temp.toString(), node.presentChars[i], results, limit);
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        String[] result3 = t.prefixSearch("pe.", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
        for (int i = 0; i < 5; i++) {
            System.out.println(result2[i]);
        }
    }
}
