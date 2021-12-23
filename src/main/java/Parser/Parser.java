package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataFileReader.DataFileReader;
import Keyword.KeywordCount;
import Webpage.Webpage;

public class Parser {
    
    private HashMap<String, List<Webpage>> invertedIndex;
    public KeywordCount kwCount;

    public Parser() {  

        this.invertedIndex = new HashMap<String, List<Webpage>>();
        this.kwCount = new KeywordCount();
    }

    /**
     * Parse the raw lines from the data file.
     * 
     * @return void.
     */
    public void parseData() {

        List<String> rawLines = getRawData();
        int rawLinesCount = rawLines.size();
        int lastIndex = 0;
        String line, title;
        List<String> keywords = new ArrayList<String>();

        // Iterate through the rawLines from the data file.
        for (int i = 1; i < rawLinesCount; i++) {

            line = rawLines.get(i);
            if(line.startsWith("*PAGE") || i == rawLinesCount - 1) {

                title = rawLines.get(lastIndex + 1);
                int counter = i;
                if (i == rawLinesCount - 1) {
                    counter++;
                }
                keywords = rawLines.subList(lastIndex + 2, counter);

                // No title, or no keyword, move on.
                if (
                    !Character.isUpperCase(title.charAt(0))
                    || keywords.size() < 1
                ) {
                    
                    // Set pointer to the last `*PAGE` instance.
                    lastIndex = i;  
                    continue;
                }

                // Create and insert Webpage instance.
                this.createAndInsertWebpage(title, rawLines.get(lastIndex), keywords);
                
                // Set pointer to the last `*PAGE` instance.
                lastIndex = i;            
            }
        }
    }

    /**
     * Create And Insert Webpage into tge invertedIndex variable.
     * 
     * @param String title - title of the webpage.
     * @param String rawUri - raw URI of the webpage.
     * @param List<String> keywords - keywords of the website.
     * @return void.
    */
    private void createAndInsertWebpage(String title, String rawUri, List<String> keywords) {

        // Create a Webpage instance.
        Webpage wp = new Webpage();
        wp.setUri(rawUri);
        wp.setTitle(title);
        wp.addKeyWords(keywords);
        
        List<Webpage> wps; 

        // Iterate through the list between two adjacent `*PAGE` instance. 
        for (String keyword : keywords) {

            // Keyword is not in the HashMap.
            if (this.invertedIndex.get(keyword) == null) {

                wps = new ArrayList<Webpage>();  
                wps.add(wp);
                this.invertedIndex.put(keyword, wps);              
            } else {          
                        
                this.invertedIndex.get(keyword).add(wp);
            }  
            this.kwCount.addKeyword(keyword);
        }
    }

    /**
     * Get inverted indexed data.
     * 
     * @return HashMap<String, List<Webpage>>
     */
    public HashMap<String, List<Webpage>> getInvertedIndex() {

        return this.invertedIndex;
    }

    /**
    * Reads raw data from DataFileReader
    *
    * @return List<String> the raw lines from the data file.
    */
    private List<String> getRawData() {

        DataFileReader fr = new DataFileReader();
        return fr.getRawData();        
    }

}
