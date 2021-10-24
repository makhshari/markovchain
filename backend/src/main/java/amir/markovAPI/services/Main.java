package amir.markovAPI.services;

import amir.markovAPI.services.Markov.*;
import amir.markovAPI.services.Parser.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        String FILEPATH;
        int PREFIXSIZE;
        int POSTFIXSIZE;
        int OUTPUTSIZE;
        try {
            FILEPATH = args[0];
            PREFIXSIZE=Integer.valueOf(args[1]);
            POSTFIXSIZE=Integer.valueOf(args[2]);
            OUTPUTSIZE=Integer.valueOf(args[3]);
        }catch (Exception e){
            throw new Exception("Missing/invalid arguments: Enter input file name as a String and prefix/postfix/output size as positive natural integers");
        }
        // get input from the input file
        Parser parser= Parser.getParser(FILEPATH);
        String input= parser.fileToString(FILEPATH);


        String output=processInput(input,PREFIXSIZE,POSTFIXSIZE,OUTPUTSIZE);
        System.out.println(output);

        System.exit(0);
    }
    // Pass the hyper paramters and the input text to the markov funtions
    public static String processInput(String input, int PREFIXSIZE, int POSTFIXSIZE, int OUTPUTSIZE) throws Exception{

        String[] words=Parser.stringToWords(input);

        // Initialize the Markov class
        MarkovModel markovModel = new MarkovModel(PREFIXSIZE,POSTFIXSIZE);

        // Build the markov chain
        markovModel.buildMarkovModel(words);

        // Generate string with the built chain
        return markovModel.generateText(OUTPUTSIZE);
    }
}
