package amir.markovAPI.services.Markov;

import java.util.Map;

public interface MarkovInterface {

    public Map<String, Node> buildMarkovModel (String[] words);
    public String generateText (int textSize);
    
}
