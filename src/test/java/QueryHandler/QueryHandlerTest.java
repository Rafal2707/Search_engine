package QueryHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import Config.Config;


@TestInstance(Lifecycle.PER_CLASS)
class QueryHandlerTest {

    private QueryHandler handler;
   
   @BeforeAll
    void setUp() {

        Config.DATA_SOURCE_FILENAME = "data/test.txt";
        Config.FREQUENCY_CALC_METHOD = 0;
        handler = new QueryHandler(); 
    }
   
    @AfterAll
    void tearDown() {

        this.handler = null;
        Config.DATA_SOURCE_FILENAME = "data/enwiki-medium.txt";
        Config.FREQUENCY_CALC_METHOD = 0;
    }

    @Test
    void testSearchByOr() {

        String result = this.handler.getResult("word2 OR word3");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}, {\"url\": \"http://page2.com\", \"title\": \"B\"}]", result);

        result = this.handler.getResult("word2 OR word4");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}]", result);

        result = this.handler.getResult("word1 OR word2 OR word3");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}, {\"url\": \"http://page2.com\", \"title\": \"B\"}]", result);
    }

    @Test
    void testSearchByAnd() {

        String result = this.handler.getResult("word2 word3");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}]", result);
    }

    @Test
    void testResultNoRanking(){

        String result = this.handler.getResult("word1");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}, {\"url\": \"http://page2.com\", \"title\": \"B\"}]", result);
    }

    @Test
    void testResultFrequencyRanking(){

        Config.FREQUENCY_CALC_METHOD = 1;

        String result = this.handler.getResult("word3");
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"B\"}, {\"url\": \"http://page1.com\", \"title\": \"A\"}]", result);
    }

    @Test
    void testResultFrequencyInverseRanking(){

        Config.FREQUENCY_CALC_METHOD = 2;
        Config.DATA_SOURCE_FILENAME = "data/test-frequency-inverse.txt";
        this.handler = new QueryHandler(); 

        String result = this.handler.getResult("word3");
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"A\"}, {\"url\": \"http://page2.com\", \"title\": \"B\"}]", result);
    }
}

