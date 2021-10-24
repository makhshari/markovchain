package amir.markovAPI.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import amir.markovAPI.services.Markov.*;
import amir.markovAPI.services.Parser.*;

import java.util.Collections;

@RestController
public class MarkovController {

    //generate text
    @GetMapping(path= "/generate",  produces = "application/json" )
    @CrossOrigin(origins = "*")
    public Object getWelcome(@RequestParam (required = true) int postfixSize,
                             @RequestParam (required = true) int prefixSize,
                             @RequestParam (required = true) int outputSize ,
                             @RequestParam (required = true) String input

    ) throws Exception {
        try {
            String[] words = Parser.stringToWords(input);
            MarkovModel markovModel = new MarkovModel(prefixSize, postfixSize);
            markovModel.buildMarkovModel(words);
            return markovModel.generateText(outputSize);
            //return Collections.singletonMap("response", markovModel.generateText(outputSize) );
        }catch (Exception e){
         return "error";
        }
    }

    @PostMapping(path= "/input" )
    public String getWelcome( @RequestParam (required = true) String input){
        return input+"*****";
        //build markov chain
    }

}