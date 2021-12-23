package Webpage;

import java.util.ArrayList;
import java.util.List;

import Config.Config;

public class Webpage {

    private String uri;
    private String title;
    private List<String> keywords;
    public Double score = 0.0;

    public Webpage() {
        
        this.keywords = new ArrayList<String>();
    }

    /**
     * Get URI.
     * 
     * @return page's uri.
     */
    public String getUri(){

        return uri;
    }

    /**
     * Get title.
     * 
     * @return page's title.
     */
    public String getTitle(){

        return title;
    }

    /**
     * Get keywords.
     * 
     * @return page's keywords.
     */
    public List<String> getKeywords(){

        return keywords;
    }

    /**
     * Set page's URI.
     * 
     * @param line
     */
    public void setUri(String line){

        uri = line.substring(6);
    }

    /**
     * Set page's title.
     * 
     * @param title
     */
    public void setTitle(String title){

        this.title = title;
    }

    /**
     * Add keyword to the list.
     * 
     * @param keyword
     */
    public void addKeyWord(String keyword) {

        this.keywords.add(keyword);
    }

    /**
     * Add multiple keyword to the list.
     * 
     * @param keywords
     */
    public void addKeyWords(List<String> keywords) {

        this.keywords = keywords;
    }

    /**
     * Return the formatted page's title and URI.
     * 
     * @return formatted page's title and URI.
     */
    public String toString() {
        
        return String.format(Config.RESPONSE_FORMAT, 
            this.getUri(), this.getTitle());
    }
}
