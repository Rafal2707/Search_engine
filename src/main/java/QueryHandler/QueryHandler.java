package QueryHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import Config.Config;
import Search.Search;
import Webpage.Webpage;

public class QueryHandler {

    private static final String SEARCH_SEPARATOR = " OR ";
    private static final String HTTP_SPACE = "%20";
    private Search search;
    
    public QueryHandler() {

        search = new Search(true);
    }

    /**
     * Get result.
     * 
     * @param rawSearchTerm
     * @return matching websites as a string.
     */
    public String getResult(String rawSearchTerm) {

        String searchTerm = this.prepareSearchTerm(rawSearchTerm);
        List<Webpage> result = this.doSearch(searchTerm);

        List<String> response = new ArrayList<String>();
        for (Webpage page : result) {

            response.add(page.toString());
        }

        return response.toString();
    }    

    /**
     * Trimming and replacing unwanted characters, such as HTTP encoded spaces.
     * 
     * @param rawSearchTerm String.     
     * @return the trimmed search string.
     */
    private String prepareSearchTerm(String rawSearchTerm) {

        String searchTerm = rawSearchTerm.replaceAll(HTTP_SPACE, " ");
        return searchTerm.trim().replaceAll(" +", " ");
    }

    /**
     * Split and organize search terms.
     * 
     * @param rawSearchTerm the raw search input.
     * @return search terms.
     */
    private List<String> splitSearchterm(String rawSearchTerm) {

        List<String> terms = new ArrayList<String>();

        if (rawSearchTerm.contains(SEARCH_SEPARATOR)) {

            terms = Arrays.asList(rawSearchTerm.split(SEARCH_SEPARATOR));
        } else {

            terms.add(rawSearchTerm);
        }

        return terms;
    }

    /**
     * Executing search.
     * 
     * @param searchTerm the raw search input.
     * @return matching list of websites.
     */
    private List<Webpage> doSearch(String searchTerm) {

        List<String> terms = this.splitSearchterm(searchTerm);

        Set<Webpage> result = new HashSet<Webpage>();

        for (String term : terms) {

            if (term.contains(" ")) {
                
                String[] tmp = searchTerm.split(" ");
                Set<Webpage> firstTermSet = this.search.searchByInvertedIndex(tmp[0]);
                firstTermSet = this.calculateScores(firstTermSet, tmp[0], "and");
                for (int i = 1; i < tmp.length; i++) {

                    Set<Webpage> nthTermSet = this.search.searchByInvertedIndex(tmp[i]);
                    nthTermSet = this.calculateScores(nthTermSet, tmp[i], "and");
                    firstTermSet.retainAll(nthTermSet);
                }
                
                result.addAll(firstTermSet);
                
            } else {
                
                result.addAll(this.search.searchByInvertedIndex(term));
                result = this.calculateScores(result, term, "or");
            }

        }
        if (Config.FREQUENCY_CALC_METHOD > 0) {
            return result.stream().sorted((e1, e2) -> 
                e2.score.compareTo(e1.score)).collect(Collectors.toList());
        }
        return new ArrayList<Webpage>(result);
    }

    /**
     * Calculate frequency scores.
     * 
     * @param result the matching webstes.
     * @param term search string.
     * @param relation `And` or `or` relation.
     * @return the matching webstes assigned with scores.
     */
    private Set<Webpage> calculateScores(Set<Webpage> result, String term, String relation) {

        int occurrence = 0;
        int keywordCount = 0;
        for (Webpage wp : result) {

            List<String> keywords = wp.getKeywords();

            occurrence = 0;
            for (String keyword : keywords) {

                if (keyword.equals(term)) {
                    occurrence++;
                }
            }
            if (occurrence > 0) {

                keywordCount = wp.getKeywords().size();
                wp.score = this.calculateScore(wp.score, occurrence, keywordCount, relation);
                if (Config.FREQUENCY_CALC_METHOD == 2) {

                    wp.score = this.calculateInverseScore(wp.score, term);
                }
            }            
        }        
        return result;
    }

    /**
     * Calculate score. 
     * 
     * @param currentScore frequency score.
     * @param occurrence of the string.
     * @param count amount of the keywords in the document.
     * @param relation `And` or `or` relation.
     * @return score.
     */
    private Double calculateScore(Double currentScore, int occurrence, int count, String relation) {

        Double score = ((double)occurrence / (double)count) * 100.0;
        // `AND` relation.
        if (relation == "and") {

            return Double.sum(currentScore, score);
        }

        // `OR` relation.
        return (score > currentScore) ? score : currentScore;
    }

    /**
     * Calculate inverse score. 
     * 
     * @param score frequency score.
     * @param keyword
     * @return inverse score.
     */
    private Double calculateInverseScore(Double score, String keyword) {
        
        int totalOccurrence = search.keywordOccurrence.get(keyword);

        return Math.log10(totalOccurrence / score);
    }
}
