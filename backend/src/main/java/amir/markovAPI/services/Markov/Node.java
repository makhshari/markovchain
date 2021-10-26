package amir.markovAPI.services.Markov;
import java.util.ArrayList;
import java.util.List;

/**
 * Markov table:
 * for the sentence: "word1 word2 word3 word4 word5"
 * if prefix=3 and postfix=2
 * "word1 word2 word3" -> "word4 word5"
 * 
 * Node class:
 * value -> word1 word2 word3
 * postfixes -> ["word4 word5", ...]
 **/

public class Node {
    public String value;
    public List<String> postfixes;

    public Node(String value){
        this.value=value;
        this.postfixes=new ArrayList<>();
    }
}
