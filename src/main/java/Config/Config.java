package Config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    public final static String RESPONSE_FORMAT = "{\"url\": \"%s\", \"title\": \"%s\"}";

    public static String DATA_SOURCE_FILENAME = "data/enwiki-medium.txt";

    /**
     * 0 -> regular.
     * 1 -> Frequency-term.
     * 2 -> Frequency-inverse-term.
     */
    public static int FREQUENCY_CALC_METHOD = 0;

    /**
     * File path.
     * @return Path filepath.
     */
    public static Path getFilePath(){
        return Paths.get(Config.DATA_SOURCE_FILENAME);
    }
}
