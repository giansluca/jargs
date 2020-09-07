package org.gmdev.jargs;

import org.gmdev.jargs.marshalers.*;

import java.util.*;

import static org.gmdev.jargs.ArgsException.ErrorCode.*;

public class Args {

    private Set<String> argsFound;
    private Map<String, ArgumentMarshaler> marshalers;
    private ListIterator<String> currentArgument;

    public Args(String schema, String[] args) throws ArgsException {
        if (schema == null || args == null)
            throw new ArgsException("FATAL: Not null violation error");

        argsFound = new HashSet<>();
        marshalers = new HashMap<>();

        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
    }

    private void parseSchema(String schema) throws ArgsException {
        for (String schemaElement : schema.split(","))
            if (schemaElement.length() > 0)
                parseSchemaElement(schemaElement.trim());
    }

    private void parseSchemaElement(String schemaElement) throws ArgsException {
        String elementName = schemaElement.substring(0, schemaElement.length() - 1);
        String elementType = schemaElement.substring(schemaElement.length() - 1);

        validateSchemaElement(elementName, elementType);

        if (elementType.equals("%"))
            marshalers.put(elementName, new BooleanArgumentMarshaler());
        else if (elementType.equals("*"))
            marshalers.put(elementName, new StringArgumentMarshaler());
        else if (elementType.equals("#"))
            marshalers.put(elementName, new IntegerArgumentMarshaler());
        else if (elementType.equals("@"))
            marshalers.put(elementName, new DoubleArgumentMarshaler());
        else
            throw new ArgsException(INVALID_SCHEMA_ELEMENT_TYPE, elementType);
    }

    private void validateSchemaElement(String elementName, String elementType) throws ArgsException {
        if (elementName.isBlank())
            throw new ArgsException(EMPTY_SCHEMA_ELEMENT_NAME, elementName+elementType);

        for (char c : elementName.toCharArray())
            if (!Character.isLetter(c))
                throw new ArgsException(INVALID_SCHEMA_ELEMENT_NAME, elementName+elementType);
    }

    private void parseArgumentStrings(List<String> argsList) throws ArgsException {
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext(); ) {
            String argString = currentArgument.next();
            if(argString.startsWith("-"))
                parseArgument(argString.substring(1));
            else
                throw new ArgsException(INVALID_ARGUMENT_NAME, argString, null);
        }
    }

    private void parseArgument(String argChar) throws ArgsException {
        ArgumentMarshaler am = marshalers.get(argChar);
        if (am == null)
            throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);

        argsFound.add(argChar);
        try {
            am.set(currentArgument);
        } catch (ArgsException e) {
            e.setErrorArgumentName(argChar);
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
