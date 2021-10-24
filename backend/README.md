# Backend API with Spring MVC 


# Project Structure
```bash
.
├── pom.xml
├── samples
│   └── input.txt
└── src
    ├── main # production code
            ├──java/amir/markovAPI
    │   │           ├── MarkovApiApplication.java #Spring Main File
    │   │           ├── controllers      #routers
    │   │           │   └── MarkovController.java 
    │   │           └── services #all business logic
    │   │               ├── Main.java # For running the standalone app
    │   │               ├── Markov # services related to Markov Chain
    │   │               │   ├── MarkovInterface.java # markov chain interface
    │   │               │   ├── MarkovModel.java # markov chain implementation
    │   │               │   └── Node.java # represent each state in Markov chain
    │   │               └── Parser # services related to reading/parsing files
    │   │                   ├── DocFileParser.java # parsing doc files
    │   │                   ├── Parser.java # general parsing functionalities
    │   │                   └── TextFileParser.java # parsing text files
        │   └── resources
        │       ├── application.properties

    └── test # test code
        ├── java/amir/markovAPI
        │           ├── MarkovApiApplicationTests.java # testing general functionalities of spring
        │           └── test-services # testing api services
        │               ├── TestIO.java # testing IO functionalities
        │               └── TestMarkov.java # testing functionalities of markov chain
        └── testdata # all test data that are used in test methods
            ├── empty-file.txt
            ├── invalid-content.txt
            ├── invalid-file.xml
            └── standard-input.txt
```  

# Steps to run the server (API or Standalone app)

The project use Maven for dependency management. So make sure you can use maven in your environment.

- Firs you need to run and package the source files by running Maven. In the root of backend directory (here), run this command:
  ```
  mvn package
  ```
If you do not have Maven installed, follow (these steps)[https://maven.apache.org/install.html] to install it for your environment.

## Run the server on localhost (API):
  Simply run the below command to have the server running in your localhost:8080 (as the default address).
  ```
  mvn spring-boot:run
  ```
  Then you can call APIs. Currently there is only one API running:
  GET ```BASE_URL/output```
  Query Parameters (all required):
  **input** -> String
  **outputSize** -> Positive natural integer
  **prefixSize** -> Positive natural integer
  **postfixSize** -> Positive natural integer

  - Missing parameters -> You'll get a *bad request* error.
  - Valid input -> You'll get the response as a string (Not JSON)
  - Invalid input -> You'll get the error as a string (Not JSON). The format is ** Server error:{Error message}

## Run as a standalone app:
  ```
  java -cp target/markovAPI-0.0.1-SNAPSHOT.jar.original amir.markovAPI.services.Main samples/{FILE.txt} {PREFIX_SIZE} {POSTFIX_SIZE} {OUTPUT_SIZE}
  ```
  For example with the given sample input files you can run 
  ```
  java -cp target/markovAPI-0.0.1-SNAPSHOT.jar.original amir.markovAPI.services.Main samples/input.txt 4 2 10
  ```
  which builds the markov chain with 4 prefix words and 2 postfix. If you want to run it with custom text files, put them in samples directory and run the same command with the correct file name. 


# Steps to run automated tests
When you first run ```mvn package```, you can see the result of automated tests in the console. If you want to re-run the test cases, simply run  
```mvn test```
