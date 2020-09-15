Jargs argument parser.

- Usage example:\
    java -jar example.jar -first first-string -second 2 -third -forth 4.44
    
    
    public static void main(String[] args) {
        String schema = "first*, second#, third%, forth@";
        Jargs arguments = null;
         
        try {
            arguments = new Jargs(schema, args);
        } catch (JargsException e) {
            e.printStackTrace();
        }
         
        String stringValue = arguments.getString("first");
        int intValue = arguments.getInt("second");
        boolean boolValue = arguments.getBoolean("third");
        double doubleValue = arguments.getDouble("forth");   
    }
    
- Types:\
    % : boolean\
    \* : String\
    \# : int\
    @ : double
     

schemaElement = elementName + elementType
argument --> argumentName - argumentValue


[![Maven Central](https://img.shields.io/maven-central/v/io.github.giansluca/jargs.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.giansluca%22%20AND%20a:%22jargs%22)