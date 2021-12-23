package Keyword;

import java.util.HashMap;

public class KeywordCount {
    
    private HashMap<String, Integer> keywordsOccurrence = new HashMap<String, Integer>();

    /**
     * Adds the keyword and the occurrence score to the hashmap.
     * 
     * @param keyword String
     */
    public void addKeyword(String keyword) {

        int newValue = 1;
        if (this.keywordsOccurrence.get(keyword) != null) {

            newValue = this.keywordsOccurrence.get(keyword) + 1;
        }

        this.keywordsOccurrence.put(keyword, newValue);
    }
    
    /**
     * Get keywords Occurrence.
     * @return keywordsOccurrence HashMap<String, Integer>
     */
    public HashMap<String, Integer> getKeywordsOccurrence() {

        return this.keywordsOccurrence;
    }
}
