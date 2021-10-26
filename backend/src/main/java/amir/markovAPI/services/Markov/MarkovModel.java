package amir.markovAPI.services.Markov;

import java.util.*;

/**
 * This class represents the whole markov chain model 
 */

public class MarkovModel implements MarkovInterface{

    /**
     How many subsequent words to consider
     */
    private final int prefixSize;
    private final int postfixSize;

    //useful for knowing about when sentences end 
    private final HashSet<Character> punctuations=new HashSet(Arrays.asList('.','?','!',';'));;

    /**
     * Markov table:
     * for the sentence: "word1 word2 word3 word4 word5"
     * "word1 word2 word3" -> "word4 word5"
     * 
     * We keep "word1 word2 word3" in markovNodes
     * each node will have a list of postfixes. So:
     * 
     * "word1 word2 word3" -> node N
     * node N.postfixes = "word4 word5"
     * 
     * 
     **/


    /*
     getting the node
     Used string as key instead of list to avoid problems when the list changed
     */
    private HashMap<String, Node> markovNodes;

    public MarkovModel(int prefixSize, int postfixSize) {
        if (prefixSize < 1 || postfixSize<1 ) throw new IllegalArgumentException("Invalid Input: prefix and postfix should be more than or equal to 1");
        this.prefixSize = prefixSize;
        this.postfixSize = postfixSize;
    }

    /**
     * Build the Markov Model:
     *
     * prefix sequence (key) -> postfix sequence (value)
     * each node is a sequence of words
     **/
    public HashMap<String, Node> buildMarkovModel(String[] words) {
        if(words.length==0){
            throw new IllegalArgumentException("Cannot build markov model out of empty input!");
        }
        this.markovNodes=new HashMap<>();
        for (int i = 0; i < words.length - prefixSize - postfixSize  ; i++) {
            String prefix=getNextNElements (words,i,prefixSize).trim();
            String postfix=getNextNElements(words,i+prefixSize+1,postfixSize).trim();

            Node markovNode =
                    this.markovNodes.containsKey(prefix) ?
                            this.markovNodes.get(prefix):new Node(prefix);

            markovNode.postfixes.add(postfix);
            this.markovNodes.put(prefix,markovNode);
        }
        return this.markovNodes;
    }

    /*
     Give back next N elements of an array starting from startIndex as a space separated string
     - startIndex is included -
     */
    public String getNextNElements(String[] arr, int startIndex, int N){
        StringBuilder sb=new StringBuilder();
        for(int i=startIndex; i<=startIndex+N && i<arr.length  ;i++){
            sb.append(arr[i].trim()).append(" ");
        }
        return sb.toString().trim();
    }

    public String generateText(int maxSize) {
        if(maxSize<=0){
            throw new IllegalArgumentException("Output<=0 Error: output size should be at least one");
        }
        if(this.markovNodes.size()==0){
            throw new IllegalArgumentException("prefix+postfix>= input Error: These hyper parameters lead to an empty markov chain :( ");
        }
        //generate random number with the size of the dictionary we have
        Random r = new Random();
        int randomNumber = r.nextInt(this.markovNodes.size());

        // Use the random prefix as the starting point of the sentence
        String prefix = (String) this.markovNodes.keySet().toArray()[randomNumber];
        List<String> output = new ArrayList<>(Arrays.asList(prefix.split(" ")));

        while (true) {
            // read possible suffixes for this prefix string
            List<String> postfixes=new ArrayList<>();
            if(this.markovNodes.containsKey(prefix)){
                postfixes = this.markovNodes.get(prefix).postfixes;
            }else{
                randomNumber = r.nextInt(this.markovNodes.size());
                prefix = (String) this.markovNodes.keySet().toArray()[randomNumber];
                continue;
            }
            randomNumber = r.nextInt(postfixes.size());
            String[] selectedPostfix=postfixes.get(randomNumber).split(" ");
            for(String str:selectedPostfix){
                output.add(str.trim());
            }

            if (output.size() >= maxSize) {
                // we have enough, so return the current output
                return output.stream().limit(maxSize).reduce("", (a, b) -> a + " " + b);
            }

            // going for the next word
            String lastWord=output.get(output.size()-1).trim();
            if( punctuations.contains(lastWord.charAt(lastWord.length()-1))){
                // we have reached the end of sentence. Start a new sentence randomly
                randomNumber = r.nextInt(this.markovNodes.size());
                prefix = (String) this.markovNodes.keySet().toArray()[randomNumber];
            }else{
                // continue the previous sentence
                StringBuilder nextPrefix=new StringBuilder();
                for(int i=output.size()-prefixSize;i<output.size();i++){
                    nextPrefix.append(output.get(i));
                }
                prefix=nextPrefix.toString();
            }
        }
    }
    
    /*
    Helper function for debugging: Print the Markov chain
    */
    public void printChain(){
        for (String key:markovNodes.keySet()){
            System.out.println("\n"+key+"  --> "+markovNodes.get(key).postfixes.size());
            markovNodes.get(key).postfixes.stream().forEach(e -> System.out.println(e+"-"));
        }
    }

}