package autoComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        TreeNode temp = root;

        for (int i = 0; i < word.length(); i++) {   
            Character character = word.charAt(i);
            temp.children.putIfAbsent(character, new TreeNode());
            temp = temp.children.get(character);

        }
        
        if (!temp.isWord) {
            temp.isWord = true;
            size++;
        }
        
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){        
        TreeNode temp = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (!temp.children.containsKey(c)) {
                return false;
            }
            temp = temp.children.get(c);
        }
        if (temp.isWord) {
            return true;
        }
        return false;

    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> words = new ArrayList<>();
        TreeNode current = root;
    
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!current.children.containsKey(c)) {
                return words;
            }
            current = current.children.get(c);
        }
    
        if (current.isWord) {
            words.add(prefix);
        }
        findWords(current, prefix, words);
        return words;
    }
    
    private void findWords(TreeNode node, String word, ArrayList<String> result) {   
        for (Map.Entry<Character, TreeNode> entry : node.children.entrySet()) {
            Character key = entry.getKey();
            TreeNode next = entry.getValue();
            String newWord = word + key;
    
            if (next.isWord == true) {
                result.add(newWord);
            }
    
            findWords(next, newWord, result);
        }
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
