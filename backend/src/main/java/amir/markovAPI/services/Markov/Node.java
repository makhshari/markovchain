package amir.markovAPI.services.Markov;
import java.util.ArrayList;
import java.util.List;

/**
 * Markov table:
 * for the sentence: "word1 word2 word3 word4 word5"
 * "word1 word2 word3" -> [word4, word5]
 * String -> List
 **/

public class Node {
    public String value;
    public List<String> postfixes;

    public Node(String value){
        this.value=value;
        this.postfixes=new ArrayList<>();
    }
}
