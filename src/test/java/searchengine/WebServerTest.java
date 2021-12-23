
package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import Config.Config;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {
    WebServer server = null;

    @BeforeAll
    void setUp() {
        Config.DATA_SOURCE_FILENAME = "data/test-file.txt";
        Config.FREQUENCY_CALC_METHOD = 0;
        
        try {
            while (server == null) {
                try {
                    server = new WebServer();
                } catch (BindException e) {
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        server.server.stop(0);
        server = null;
        Config.DATA_SOURCE_FILENAME = "data/enwiki-medium.txt";
    }

    @Test
    void lookupWebServer() {
        String baseURL = String.format("http://localhost:%d/search?q=", 
            server.server.getAddress().getPort());
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"Title1\"}, {\"url\": \"http://page2.com\", \"title\": \"Title2\"}]", 
            httpGet(baseURL + "word1"));
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"Title1\"}]",
            httpGet(baseURL + "word2"));
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"Title2\"}]", 
            httpGet(baseURL + "word3"));
        assertEquals("[]", 
            httpGet(baseURL + "word4"));
    }

    private String httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
