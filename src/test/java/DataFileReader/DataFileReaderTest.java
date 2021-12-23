package DataFileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
class DataFileReaderTest {
    
    private DataFileReader dataFileReader;
    private final int linesCount = 1652278;

    @BeforeAll
    void setUp() {

        this.dataFileReader = new DataFileReader(); 
    }

    @AfterAll
    void tearDown() {
        
        this.dataFileReader = null;
    }

    @Test
    void checkReturnedLinesCount() {
        assertEquals(this.linesCount, this.dataFileReader.getRawData().size());
    }
}
