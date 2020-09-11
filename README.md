Jargs argument parser.

- Usage example:\
    java -jar example.jar -first test -second 99    

- Schema example: String schema = "first*, second#";
- Arguments example : String args = {"-first", "test", "-second", "99"};

- Types:\
    % : boolean\
    \* : String\
    \# : int\
    @ : double
     

schemaElement = elementName + elementType

argument --> argumentName - argumentValue

[![Maven Central](https://img.shields.io/maven-central/v/io.github.giansluca/jargs.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.giansluca%22%20AND%20a:%22jargs%22)