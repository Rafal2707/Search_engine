package Keyword;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class KeywordCountTest {
    
    private List<String> input = new ArrayList<String>();
    private KeywordCount kwCount = new KeywordCount();

    @BeforeAll
    void setUp() {

        this.input.add("word_0");
        this.input.add("word_0");
        this.input.add("word_0");
        this.input.add("word_0");
        this.input.add("word_0");
        this.input.add("word_1");
        this.input.add("word_1");
        this.input.add("word_2");

        for (String keyword : this.input) {

            this.kwCount.addKeyword(keyword);
        }
    }

    @AfterAll
    void tearDown() {

        this.kwCount = null;
    }

    @Test
    void testKeywordCounter() {

        HashMap<String, Integer> result = this.kwCount.getKeywordsOccurrence();

        assertEquals(5, result.get("word_0"));
        assertEquals(2, result.get("word_1"));
        assertEquals(1, result.get("word_2"));
    }
}
