import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import amir.markovAPI.services.Parser.*;

import java.io.IOException;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIO {


TextFileParser textFileParser;

// test data
String validFileValidInput="src/test/testdata/standard-input.txt";
String validFileEmptyInput="src/test/testdata/empty-file.txt";
String validFileInvalidContent="src/test/testdata/invalid-content.txt";
String validPathInvalidFile = "src/test/testdata/invalid-file.xml";
String invalidPath="invalid dir string / / & / @";

    @BeforeEach
    void setup(){
        // Setup code here
    }

    @Test
    public void testTextFileParser() throws Exception {
        Parser textFileParser;
        /*
            Standard tests: with valid paths
        */
        // valid file valid content
        textFileParser=Parser.getParser(validFileValidInput);
        String resValidFileDir=textFileParser.fileToString(validFileValidInput);
        assertThat(resValidFileDir.length(), not(0));
        assertTrue(resValidFileDir instanceof String);

        // valid file invalid content
        textFileParser=Parser.getParser(validFileInvalidContent);
        String resValidFileInvalidContent=textFileParser.fileToString(validFileInvalidContent);
        assertThat(resValidFileInvalidContent.length(), not(0));
        assertTrue(resValidFileInvalidContent instanceof String);

        // valid file empty content
        textFileParser=Parser.getParser(validFileEmptyInput);
        String resValidEmptyFileDir=textFileParser.fileToString(validFileEmptyInput);
        assertThat(resValidEmptyFileDir.length(), is(0));
        assertTrue(resValidEmptyFileDir instanceof String);

        /*
            Edge tests
         */
        // Invalid Path
        try{
            textFileParser=Parser.getParser(invalidPath);
            textFileParser.fileToString(invalidPath);
            assert (false);
        }catch (Exception e){
            assert (true);
        }

        // valid path, invalid file format
        try{
            textFileParser=Parser.getParser(validPathInvalidFile);
            textFileParser.fileToString(validPathInvalidFile);
            assert (false);
        }catch (Exception e){
            assert (true);
        }
    }
    @Test
    public void testTextToWords() throws Exception {
        textFileParser=new TextFileParser();

        String resValidFileDir=textFileParser.fileToString(validFileValidInput);
        assertThat(textFileParser.stringToWords(resValidFileDir).length, not(0));

        // empty text
        try {
            String resValidEmptyFileDir = textFileParser.fileToString(validFileEmptyInput);
            textFileParser.stringToWords(resValidEmptyFileDir);
            assert (false);
        }catch (Exception e){
            assert (true);
        }

        // invalid text: no alphanumeric
        try {
            String resValidFileInvalidContent = textFileParser.fileToString(validFileInvalidContent);
            textFileParser.stringToWords(resValidFileInvalidContent);
            assert (false);
        }catch (Exception e){
            assert (true);
        }

    }
}
