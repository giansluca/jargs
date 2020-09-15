package io.github.giansluca.jargs;

import io.github.giansluca.jargs.exception.ErrorCode;
import io.github.giansluca.jargs.exception.JargsArgumentException;
import io.github.giansluca.jargs.exception.JargsException;
import io.github.giansluca.jargs.exception.JargsSchemaException;
import io.github.giansluca.jargs.marshalers.*;

import java.util.*;

public class Jargs {

    private final Set<String> argsFound;
    private final Map<String, ArgumentMarshaler> marshalers;
    private final ListIterator<String> currentArgument;

    public Jargs(String schema, String[] args) throws JargsException {
        validateInput(schema, args);

        argsFound = new HashSet<>();
        marshalers = new HashMap<>();
        currentArgument = Arrays.asList(args).listIterator();

        parseSchema(schema);
        parseArgumentStrings();
    }

    private void validateInput(String schema, String[] args) throws JargsException {
        if (schema == null || args == null)
            throw new JargsException("FATAL: Not null violation error");

        if (schema.isBlank() || args.length == 0)
            throw new JargsException("FATAL: Not blank violation error");
    }

    private void parseSchema(String schema) throws JargsException {
        for (String schemaElement : schema.split(","))
            if (schemaElement.length() > 0)
                parseSchemaElement(schemaElement.trim());
    }

    private void parseSchemaElement(String schemaElement) throws JargsException {
        String elementName = schemaElement.substring(0, schemaElement.length() - 1);
        String elementType = schemaElement.substring(schemaElement.length() - 1);

        validateSchemaElement(elementName, elementType);

        switch (elementType) {
            case "%":
                marshalers.put(elementName, new BooleanArgumentMarshaler());
                break;
            case "*":
                marshalers.put(elementName, new StringArgumentMarshaler());
                break;
            case "#":
                marshalers.put(elementName, new IntegerArgumentMarshaler());
                break;
            case "@":
                marshalers.put(elementName, new DoubleArgumentMarshaler());
                break;
            default:
                throw new JargsSchemaException(
                        ErrorCode.INVALID_SCHEMA_ELEMENT_TYPE, elementType);
        }
    }

    private void validateSchemaElement(
            String elementName, String elementType) throws JargsException {

        if (elementName.isBlank())
            throw new JargsSchemaException(
                    ErrorCode.EMPTY_SCHEMA_ELEMENT_NAME, elementName+elementType);

        for (char c : elementName.toCharArray())
            if (!Character.isLetter(c))
                throw new JargsSchemaException(
                        ErrorCode.INVALID_SCHEMA_ELEMENT_NAME, elementName);
    }

    private void parseArgumentStrings() throws JargsException {
        while(currentArgument.hasNext()) {
            String argument = currentArgument.next();
            if(argument.startsWith("-"))
                parseArgument(argument.substring(1));
            else
                throw new JargsArgumentException(ErrorCode.INVALID_ARGUMENT_NAME, argument, null);
        }
    }

    private void parseArgument(String argument) throws JargsException {
        ArgumentMarshaler am = marshalers.get(argument);
        if (am == null)
            throw new JargsArgumentException(ErrorCode.UNEXPECTED_ARGUMENT, argument, null);

        argsFound.add(argument);
        try {
            am.set(currentArgument);
        } catch (JargsArgumentException e) {
            e.setErrorArgumentName(argument);
            throw e;
        }
    }

    public boolean getBoolean(String arg) {
        return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public String getString(String arg) {
        return StringArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public int getInt(String arg) {
        return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public double getDouble(String arg) {
        return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public int cardinality() {
        return argsFound.size();
    }

    public boolean has(String arg) {
        return argsFound.contains(arg);
    }

}
