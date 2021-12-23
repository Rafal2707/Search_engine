package Search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Parser.Parser;
import Webpage.Webpage;

public class Search {
    
    private HashMap<String, List<Webpage>> invertedIndex;
    public HashMap<String, Integer> keywordOccurrence;

    public Search(Boolean init) {

        // Parse the data.
        if (init) {
            Parser p = new Parser();
            p.parseData();
            this.invertedIndex = p.getInvertedIndex();
            this.keywordOccurrence = p.kwCount.getKeywordsOccurrence();
        }
    }

    /**
     * Search by the inverted index.
     * 
     * @param searchTerm
     * @return matching websites.
     */
    public Set<Webpage> searchByInvertedIndex(String searchTerm) {

        Set<Webpage> result = new HashSet<Webpage>();
        Iterator it = this.invertedIndex.entrySet().iterator();
        while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry)it.next();
            var key = pair.getKey();
            if (key.toString().contains(searchTerm)) {

                result.addAll(this.invertedIndex.get(key));          
            }
        }
    
        return result;
      }
}
