# Markov Chain Text Generator
This app is a full-stack implementation for text-generation using a Stochastic Modeling called **Markov Chain**.  This app is in React JS and Spring web MVC. This page gives a high-level undestanding of the app and how to use it. For installing and configuring backend and frontend individually, there are specific readme files in their own directory: 

[Configuring frontend](https://github.com/makhshari/markovchain/tree/main/frontend)

[Configuring backend](https://github.com/makhshari/markovchain/tree/main/backend)

![UI flow](./ui-flow.gif)

# About the App


## How to use this app
There are two ways to use this app:
### Web Application (frontend + backend)

As it is shown in the GIF above, you can use it as a web application. All you need to do is to enter a story (and its' name), and tune three hyperparameters:

- Number of words as prefix.
- Number of words as postfix.
- Number of words to generate.

** Please note that the input story and all of these fields are required as server is set to return ```error``` otherwise. We could set them to 1 as a default value, but this doesn't yield the best way to use the app.

** To get a better understanding of what these parameters mean, you need to read the next section.  

#### How to run web application:
1- Run backend server on your localhost following the instruction here: [Running backend server on localhost](https://github.com/makhshari/markovchain/tree/main/backend#run-the-server-on-localhost-api)

2- Have the frontend up and running following the instruction here: [Configuring frontend](https://github.com/makhshari/markovchain/tree/main/frontend)


** Note that if your server is not running on ```http://localhost:8080```, you need to set the new address in the frontend global variables.

### Only API 
After [running backend server on localhost](https://github.com/makhshari/markovchain/tree/main/backend#run-the-server-on-localhost-api), you can use the backend server as a general API and send request to it from other clients, such as Postman or your custom web client.

### Standalone Java Application
This option is good if your input is a text file as the frontend still does not have the uploading file feature (TBD). Currently the Java app supports ```.txt```,```.doc```, and ```.docx``` formats, but the implementation is pretty flexible for adding more formats.  

The only thing you need to do for running it is to follow the steps for [running the server as a standalone Java application](https://github.com/makhshari/markovchain/tree/main/backend#run-as-a-standalone-app). You can simply put your input text file in the appropriate directory, run a CMD command with the mentioned required hyperparameters, and get the generated text on terminal.

# About Text Generation with Markov Chain

# About This implementation of Markov Chain

# Error handling

## Assumptions: What is considered **valid/invalid text**?
- **Normal input**: As a rule of thumb, we expect normal input to have non-empty text, and positive natural values for prefix, postfix, and output size. Also, the normal usage suggests that output size be smaller than (or equal to) the input size. The prefix or postfix size exceeding the size of the input text is also not considered a normal input.

As the nature of Markov chain text generation algorithm suggests, to be able to consider all hyper parameters in the generated text we need to have:

1- ```prefix-size + postfix-size <= input-size```
2- ```output-size <= input-size```

The first condition is required for a minimal chain with the user postfix and prefix size.
And in order to generate reasonabele output without too much duplication, it's better to have the second condition too.

These conditions describe a perfect input.

However, we need to have a strategy for each of these non-standard scenarios to have a robus application. Below I will describe my strategy to deal with each of these ```bad inputs```:

## Backend Error Handling
The server is designed to be robust against faulty inputs. Below I will describe how the server reacts to different situations.

- **Empty input**:
  - Server returns an error asking user to enter a valid file.   
- **Non-empty invalid input**: 
    - These are files have some content but the content is invalid after our filtering.
    - **Our server filters only recognize alphanumeric characters (a-zA-Z0-9) and valid punctuation marks (such as ',' or '.' or '?' or '!').** Other characters will be considered invalid and will be removed (replaced by empty character) before passing into the markov chain functions. 
    - For example, if your file only contains '*' or '~', without a single alphanumeric character, the server will return error. However, if there is more than zero valid alphanumeic character, server will consider them.
    - A TBD for this section is to build a dictionary of valid characters as a **global** file, so that all sub-systems would be aware of them. Currently, they are only a hashset in the MarkovModel class.
- **Valid input with faulty hyper-parameters**: 
  - ```output-size >= input-size```
      - Server does not return an error and tries to meet user expectation (output-size) by generating repetetive words. It's not recommended to have output-size more than input-size if you are looking for reasonable output.
  - ```prefix-size + postfix-size >= input-size```
    - The server interprets this input as an invalid input and throws exception. The reason is that this input leads to an empty markov chain.
  - ```Bad hyper-parameters: negative/float/empty```
    - Server returns and exception and guide you to enter valid parameters. 


## Frontend Error Handling
- The React app is just a representation of how this app can be used in UI. Currently, the frontend is not robust against faulty inputs and might break with non-standard inputs. **Current version of frontend is meant to be used with standard inputs.**

- **Large inputs**: The configured Spring MVC returns ```Request header is too large``` if your input text is super-large. If you want to test frontend with large inputs you need to set ```server.max-http-header-size``` parameter in the ``` application.properties``` file in the compiled target directory as a very large number (like 10000000).

- **Missing hyperparameters (prefix/postfix/output)**: : The frontend simply does not update the generated text when it does not get the required parameters, as it expects you to enter all required fields. A to-be-done for this section is to add a error/warning message in the UI to let user know about their mistake.


- **Bad inputs**: If UI is sending everything that is requried to the server, everything depend on how server handle each scenario which is discussed above. For the case of errors, the front end simply prints the server error on the page which is of course not ideal. A to-be-done for this section is to parse server error and guide user to correct their bad inputs.


# Testing

There are automated tests for testing below functionalities:

- **Testing IO Functionalities**: These tests will measure how our system reacts to invalid input files.
- **Testing Markov Functionalities**: These tests will measure how certain functionalities related to markov chain operate in regards to different inputs. ```Building markov chain```and ```Generating text with markov chain``` are tested with different inputs and hyper paramteres.