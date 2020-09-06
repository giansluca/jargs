package org.gmdev.jargs;

import org.gmdev.jargs.marshalers.*;

import java.util.*;

import static org.gmdev.jargs.ArgsException.ErrorCode.*;

public class Args {

    private final Set<String> argsFound;
    private final Map<String, ArgumentMarshaler> marshalers;
    private ListIterator<String> currentArgument;

    public Args(String schema, String[] args) throws ArgsException {
        argsFound = new HashSet<>();
        marshalers = new HashMap<>();

        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
    }

    private void parseSchema(String schema) throws ArgsException {
        for (String element : schema.split(","))
            if (element.length() > 0)
                parseSchemaElement(element.trim());
    }

    private void parseSchemaElement(String element) throws ArgsException {
        String elementId = element.substring(0, element.length() - 1);
        String elementTail = element.substring(element.length() - 1);

        validateSchemaElement(elementId);

        if (elementTail.equals("%"))
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        else if (elementTail.equals("*"))
            marshalers.put(elementId, new StringArgumentMarshaler());
        else if (elementTail.equals("#"))
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        else if (elementTail.equals("@"))
            marshalers.put(elementId, new DoubleArgumentMarshaler());
        else
            throw new ArgsException(INVALID_FORMAT, elementId, elementTail);
    }

    private void validateSchemaElement(String elementId) throws ArgsException {
        if (elementId.length() == 0)
            throw new ArgsException(INVALID_ARGUMENT_NAME);

        for (char c : elementId.toCharArray())
            if (!Character.isLetter(c))
                throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
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
            e.setErrorArgumentId(argChar);
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
