package amir.markovAPI.services.Parser;

import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;
public abstract class Parser {


    /*
     INPUT  -> The name of the input file -> path will find it based on the name -> Files are in the /resoruces directory
     OUTPUT -> A String of the file's content
     */

    public static Parser getParser(String filepath) throws Exception {
        if(FileSystems.getDefault().getPathMatcher("glob:**.txt").matches(Paths.get(filepath))){
            return new TextFileParser();
        }

        else if(FileSystems.getDefault().getPathMatcher("glob:**.doc").matches(Paths.get(filepath))){
            return new DocFileParser();
        }

        else if(FileSystems.getDefault().getPathMatcher("glob:**.docx").matches(Paths.get(filepath))){
            return new DocFileParser();
        }
        else{
            throw new Exception ("Invalid file format");
        }
    }

    public abstract String fileToString(String fileName) throws IOException;


    /* *
      INPUT: A long text in the format of a string
      OUTPUT -> an array of texts' words
     * */
    public static String[] stringToWords(String text) throws Exception {

        // trim white spaces - remove extra enters - and then return words
        String[] words= Arrays.stream(text.replace("\n", "").replace("\r", "").trim().toLowerCase().split(" "))
                .filter(str -> str.length()>0)
                .toArray(String[]::new);

        // more detailed validations on input words
        words=validateSentences(words);
        if (words.length==0){
            throw new Exception("No valid word in the input string!");
        }
        return words;
    }

    public static String[] validateSentences(String[] words){
        if(words.length==0){
            return new String[0];
        }
        int countAlphaNumerics=0;
        List<String> validated=new ArrayList<>();
        if(isAlphaNumeric(words[0])) validated.add(words[0]);
        for(int i=1; i<words.length;i++){

            // filter consecutive non-AlphaNumeric characters
            if(!isAlphaNumeric(words[i]) && !isAlphaNumeric(words[i-1])){
                //only consider the first one (i-1) and ignore the following (i)
                continue;
            }

            // remove additional spaces
            if(words[i].length()==0) continue;

            if(isAlphaNumeric(words[i])){
                countAlphaNumerics++;
            }
            validated.add(words[i]);
        }
        if(countAlphaNumerics==0){
            throw new IllegalArgumentException("Couldn't find a single valid alphanumeric word in the input!");
        }
        return validated.stream().toArray(String[]::new);
    }

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
}
