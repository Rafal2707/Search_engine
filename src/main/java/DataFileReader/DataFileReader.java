package DataFileReader;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Config.Config;

public class DataFileReader {
    
    private List<String> lines;

    // Constructor
    public DataFileReader() {
        
        lines = new ArrayList<>();
    }

    /**
     * Reads the config.txt to get the path for the data file.
     * 
     * @return ArrayList of Strings containing the raw data. 
     */    
    public List<String> getRawData() {

        try {
            lines = Files.readAllLines(Config.getFilePath());
        } catch (Exception e) {
            System.out.println(e);
        }
        return lines;
    }
}
