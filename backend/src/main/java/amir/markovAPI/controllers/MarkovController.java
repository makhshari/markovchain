package amir.markovAPI.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import amir.markovAPI.services.*;

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
            return Main.processInput(input,prefixSize,postfixSize,outputSize);

            /* If we want to return JSON:
            return Collections.singletonMap("response", markovModel.generateText(outputSize) );
            */
        }catch (Exception e){
         return "** Server error:"+ e.toString();
        }
    }

    @PostMapping(path= "/input" )
    public String getWelcome( @RequestParam (required = true) String input){
        return input+"*****";
        //build markov chain
    }

}