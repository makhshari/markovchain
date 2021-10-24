

import static org.hamcrest.MatcherAssert.assertThat;
import amir.markovAPI.services.Markov.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestMarkov {
    MarkovModel markov;

    @BeforeEach
    void setup(){
        // Setup code here
    }
    @Test
    public void standardTestMarkovChain(){

        // Essential tests: Happy cases
        markov=new MarkovModel(1,1);
        String[] input=new String[]{"word1","word2","word3","word4","word1", "word2", "word5","word6",};
        HashMap<String, Node> nodes = markov.buildMarkovModel(input);

        assertAll("Assert validity of the markov model map",
                () -> assertThat(nodes.size()>0,is(true))
        );

        assertAll (
                "Assert creation of markov nodes ",
                () -> assertThat(nodes.containsKey("word1 word2"),is(true)),
                () -> assertThat(nodes.get("word1 word2").value,is("word1 word2")),
                () -> assertThat(nodes.containsKey("word2 word3"),is(true)),
                () -> assertThat(nodes.containsKey("word6"),is(false)),
                () -> assertThat(nodes.containsKey("word5"),is(false))
        );

        assertAll (
                "Assert connection of markov nodes",
                () -> assertThat(nodes.get("word1 word2").postfixes.size(),is(2)),
                () -> assertThat(nodes.get("word1 word2").postfixes.contains("word3 word4"),is(true)),
                () -> assertThat(nodes.get("word2 word5").postfixes.contains("word6"),is(true)),
                () -> assertThat(nodes.get("word2 word3").postfixes.contains("word4 word1"),is(true)),
                () -> assertThat(nodes.get("word4 word1").postfixes.contains("word2 word5"),is(true))
        );
    }

    @Test
    public void extremeTestMarkovChain(){

        markov=new MarkovModel(1,1);
        String[] extremeInput=new String[]{"hello      ","          word1       ","word2  ", "  .  ", "hello ","word1"," . "};
        HashMap<String, Node> edgeNodes = markov.buildMarkovModel(extremeInput);

        assertAll("Assert validity of the markov model map",
                () -> assertThat(edgeNodes.size()>0,is(true))
        );

        assertAll (
                "Assert creation of markov nodes ",
                () -> assertThat(edgeNodes.containsKey("hello word1"),is(true)),
                () -> assertThat(edgeNodes.get("hello word1").value,is("hello word1")),
                () -> assertThat(edgeNodes.containsKey("hello   word1"),is(false)),
                () -> assertThat(edgeNodes.containsKey("hello word1"),is(true)),
                () -> assertThat(edgeNodes.get("hello word1").postfixes.size(),is(2))
        );

        try{
            String[] extremeInput2=new String[0];
            markov.buildMarkovModel(extremeInput2);
            assert (false);
        }catch (Exception e){
            assert (true);
        }
    }

    @Test
    public void testGetElementsHelperFunction(){
        markov=new MarkovModel(1,1);
        String[] input=new String[]{"word1","word2","word3","word4","word1", "word2", "word5","word6"};

        assertThat(markov.getNextNElements(input,0,2),is("word1 word2 word3"));
        assertThat(markov.getNextNElements(input,0,1),is("word1 word2"));
        assertThat(markov.getNextNElements(input,4,4),is("word1 word2 word5 word6"));
    }

    @Test
    public void standardTestMarkovOutout(){
        markov=new MarkovModel(1,1);
        String[] input=new String[]{"word1","word2","word3","word4","word1", "word2", "word5","word6",};
        markov.buildMarkovModel(input);
        String output=markov.generateText(10);
        assertThat(countWordsInString(output), is(10));
    }

    @Test
    public void extremeTestMarkovOutout(){
        markov=new MarkovModel(1,1);
        String[] input=new String[]{"word1","word2","word3","word4","word1", "word2", "word5","word6",};
        markov.buildMarkovModel(input);

        try{
            markov.generateText(-3);
            markov.generateText(0);
            assert (false);
        }catch (Exception e){
            assert (true);
        }
    }

    public int countWordsInString(String text){
        return text.trim().split(" ").length;
    }
}


