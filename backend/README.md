# Backend API with Spring MVC 
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
    │   │               ├── Main.java 
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
        │           ├── MarkovApiApplicationTests.java # spring test file
        │           └── test-services # testing api services
        │               ├── TestIO.java # testing IO functionalities
        │               └── TestMarkov.java # testing functionalities of markov chain
        └── testdata # test files that are used in test methods
            ├── empty-file.txt
            ├── invalid-content.txt
            ├── invalid-file.xml
            └── standard-input.txt
```  
