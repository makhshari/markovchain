package amir.markovAPI.services;

import amir.markovAPI.services.Markov.*;
import amir.markovAPI.services.Parser.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        String FILEPATH=args[0];
        int PREFIXSIZE=Integer.valueOf(args[1]);
        int POSTFIXSIZE=Integer.valueOf(args[2]);
        int OUTPUTSIZE=Integer.valueOf(args[3]);

        Parser parser= Parser.getParser(FILEPATH);
        String[] words=parser.stringToWords(parser.fileToString(FILEPATH));

        // construct the transformer, supplying the prefix length to use
        MarkovModel markovModel = new MarkovModel(PREFIXSIZE,POSTFIXSIZE);

        // train the transformer on our training set
        markovModel.buildMarkovModel(words);
        // this could be done multiple times...

        // get our output string
        String output = markovModel.generateText(OUTPUTSIZE);

        // print it to the console
        System.out.println(output);

        System.exit(0);
    }
}
