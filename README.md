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