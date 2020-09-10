package org.gmdev.jargs;

import org.gmdev.jargs.exception.JargsArgumentException;
import org.gmdev.jargs.exception.JargsException;
import org.gmdev.jargs.exception.JargsSchemaException;
import org.gmdev.jargs.marshalers.*;

import java.util.*;

import static org.gmdev.jargs.exception.ErrorCode.*;

public class Jargs {

    private Set<String> argsFound;
    private Map<String, ArgumentMarshaler> marshalers;
    private ListIterator<String> currentArgument;

    public Jargs(String schema, String[] args) throws JargsException {
        if (schema == null || args == null)
            throw new JargsException("FATAL: Not null violation error");

        argsFound = new HashSet<>();
        marshalers = new HashMap<>();

        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
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

        if (elementType.equals("%"))
            marshalers.put(elementName, new BooleanArgumentMarshaler());
        else if (elementType.equals("*"))
            marshalers.put(elementName, new StringArgumentMarshaler());
        else if (elementType.equals("#"))
            marshalers.put(elementName, new IntegerArgumentMarshaler());
        else if (elementType.equals("@"))
            marshalers.put(elementName, new DoubleArgumentMarshaler());
        else
            throw new JargsSchemaException(INVALID_SCHEMA_ELEMENT_TYPE, elementType);
    }

    private void validateSchemaElement(String elementName, String elementType) throws JargsException {
        if (elementName.isBlank())
            throw new JargsSchemaException(EMPTY_SCHEMA_ELEMENT_NAME, elementName+elementType);

        for (char c : elementName.toCharArray())
            if (!Character.isLetter(c))
                throw new JargsSchemaException(INVALID_SCHEMA_ELEMENT_NAME, elementName);
    }

    private void parseArgumentStrings(List<String> argsList) throws JargsException {
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext(); ) {
            String argument = currentArgument.next();
            if(argument.startsWith("-"))
                parseArgument(argument.substring(1));
            else
                throw new JargsArgumentException(INVALID_ARGUMENT_NAME, argument, null);
        }
    }

    private void parseArgument(String argument) throws JargsException {
        ArgumentMarshaler am = marshalers.get(argument);
        if (am == null)
            throw new JargsArgumentException(UNEXPECTED_ARGUMENT, argument, null);

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
