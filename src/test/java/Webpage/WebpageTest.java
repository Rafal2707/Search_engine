package Webpage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class WebpageTest {
    
    private Webpage wp;
    private String title = "Title";
    private String uri = "*PAGE:http://page1.com";
    private String keyword = "keyword";
    private List<String> keywords = new ArrayList<String>();

    @BeforeAll
    void setUp() {
        this.wp = new Webpage();
        this.wp.setTitle(this.title);
        this.wp.setUri(this.uri);
        this.wp.addKeyWord(keyword);
        this.keywords.add("this.keywords_0");
        this.keywords.add("keywothis.rds_1");
        this.wp.addKeyWords(this.keywords);
    }

    @Test
    void testWebpage() {

        assertEquals(this.title, this.wp.getTitle());
        assertEquals("http://page1.com", this.wp.getUri());
        assertEquals(this.keywords, wp.getKeywords());
        this.keywords.add(0, this.keyword);
        assertEquals(this.keywords, this.wp.getKeywords());
    }
}
