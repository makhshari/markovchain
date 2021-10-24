package amir.markovAPI.services.Markov;

import java.util.*;

/**
 * Transforms a body of text according to a Markov Chain algorith.
 */

public class MarkovModel implements MarkovInterface{

    /**
     How many subsequent words to consider
     */
    private final int prefixSize;
    private final int postfixSize;

    private final HashSet<Character> punctuations=new HashSet(Arrays.asList('.','?','!',';'));;

    /**
     * Markov table:
     * for the sentence: "word1 word2 word3 word4 word5"
     * "word1 word2 word3" -> [word4, word5]
     * String -> List
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
    public void printChain(){
        for (String key:markovNodes.keySet()){
            System.out.println("\n"+key+"  --> "+markovNodes.get(key).postfixes.size());
            markovNodes.get(key).postfixes.stream().forEach(e -> System.out.println(e+"-"));
        }
    }

    public String generateText(int maxSize) {
        if(maxSize<=0){
            throw new IllegalArgumentException("output size should be at least one");
        }

        Random r = new Random();
        int n = 0;
        // Grab a random starting point
        int rn = r.nextInt(this.markovNodes.size());
        String prefix = (String) this.markovNodes.keySet().toArray()[rn];

        // Create the output with this starting point as its only content
        List<String> output = new ArrayList<>(Arrays.asList(prefix.split(" ")));

        while (true) {
            // read possible suffixes for this prefix string
            List<String> postfixes=new ArrayList<>();
            if(this.markovNodes.containsKey(prefix)){
                postfixes = this.markovNodes.get(prefix).postfixes;
            }else{
                rn = r.nextInt(this.markovNodes.size());
                prefix = (String) this.markovNodes.keySet().toArray()[rn];
                continue;
            }
            rn = r.nextInt(postfixes.size());
            String[] selectedPostfix=postfixes.get(rn).split(" ");
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
                rn = r.nextInt(this.markovNodes.size());
                prefix = (String) this.markovNodes.keySet().toArray()[rn];
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


}