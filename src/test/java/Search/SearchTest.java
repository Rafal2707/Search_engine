package Search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import Config.Config;
import Webpage.Webpage;

@TestInstance(Lifecycle.PER_CLASS)
class SearchTest {
    
    private Search search;
   
    @BeforeAll
    void setUp() {

        Config.DATA_SOURCE_FILENAME = "data/test.txt";
        Config.FREQUENCY_CALC_METHOD = 0;
        this.search = new Search(true); 
    }
    
    @AfterAll
    void tearDown() {

        this.search = null;
        Config.DATA_SOURCE_FILENAME = "data/enwiki-medium.txt";
    }

    @Test
    void testSearch() {

        Set<Webpage> result = this.search.searchByInvertedIndex("word2");
        assertEquals(1, result.size());

        result = this.search.searchByInvertedIndex("word1");
        assertEquals(2, result.size());
    }
}
