package Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import Config.Config;
import Webpage.Webpage;

@TestInstance(Lifecycle.PER_CLASS)
class ParserTest {
    
    private Parser parser;

    @BeforeAll
    void setUp() {

        Config.DATA_SOURCE_FILENAME = "data/test-file.txt";
        this.parser = new Parser();
    }

    @AfterAll
    void tearDown() {

        this.parser = null;
        Config.DATA_SOURCE_FILENAME = "data/enwiki-medium.txt";
    }

    @Test
    void testParser() {
        
        this.parser.parseData();
        HashMap<String, List<Webpage>> invertedIndex = this.parser.getInvertedIndex();        

        int count_0 = invertedIndex.get("word2").size();
        assertEquals(1, count_0);

        int count_1 = invertedIndex.get("word1").size();
        assertEquals(2, count_1);
    }
        
}
