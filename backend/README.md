# Backend API with Spring MVC 
```bash
.
├── pom.xml
├── samples
│   └── input.txt
└── src
    ├── main
            ├──java/amir/markovAPI
    │   │           ├── MarkovApiApplication.java #Spring Main File
    │   │           ├── controllers      #Routers
    │   │           │   └── MarkovController.java
    │   │           └── services #Business Logic
    │   │               ├── Main.java 
    │   │               ├── Markov # Services related to Markov Chain
    │   │               │   ├── MarkovInterface.java # Markov chain interface
    │   │               │   ├── MarkovModel.java # Markov chain implementation
    │   │               │   └── Node.java # Represent each state in Markov chain
    │   │               └── Parser # Services related to reading/parsing files
    │   │                   ├── DocFileParser.java # parsing doc files
    │   │                   ├── Parser.java # Main parsing functionalities
    │   │                   └── TextFileParser.java # parsing text files
        │   └── resources
        │       ├── application.properties

    └── test
        ├── java/amir/markovAPI
        │           ├── MarkovApiApplicationTests.java
        │           └── test-services
        │               ├── TestIO.java
        │               └── TestMarkov.java
        └── testdata
            ├── empty-file.txt
            ├── invalid-content.txt
            ├── invalid-file.xml
            └── standard-input.txt
```  
