package org.gmdev.jargs;

import org.gmdev.jargs.marshalers.*;

import java.util.*;

import static org.gmdev.jargs.ArgsException.ErrorCode.INVALID_ARGUMENT_NAME;
import static org.gmdev.jargs.ArgsException.ErrorCode.INVALID_FORMAT;

public class Args {

    private final Set<Character> argsFound;
    private final Map<Character, ArgumentMarshaler> marshalers;
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
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElement(elementId);

        if (elementTail.length() == 0)
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        else if (elementTail.equals("*"))
            marshalers.put(elementId, new StringArgumentMarshaler());
        else if (elementTail.equals("#"))
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        else if (elementTail.equals("##"))
            marshalers.put(elementId, new DoubleArgumentMarshaler());
        else
            throw new ArgsException(INVALID_FORMAT, elementId, elementTail);
    }

    private void validateSchemaElement(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
    }

    private void parseArgumentStrings(List<String> argsList) throws ArgsException {
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext(); ) {
            String argString = currentArgument.next();
            if(argString.startsWith("-"))
                parseArgumentCharacters(argString.substring(1));
            else {
                currentArgument.previous();
                break;
            }
        }
    }

    private void parseArgumentCharacters(String arg) throws ArgsException {
        for (int i = 0; i < arg.length(); i++)
            parseElement(arg.charAt(i));
    }

    private void parseElement(char argChar) throws ArgsException {
        if (setArgument(argChar)) {
            argsFound.add(argChar);
        } else {
            throw new ArgsException(
                    ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,
                    argChar, null);
        }
    }

    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m == null)
            return false;

        try {
            m.set(currentArgument);
            return true;
        } catch (ArgsException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
    }

    public boolean getBoolean(char arg) {
        return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public String getString(char arg) {
        return StringArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public int getInt(char arg) {
        return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public double getDouble(char arg) {
        return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
    }

    public int cardinality() {
        return argsFound.size();
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

}
