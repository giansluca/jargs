package org.gmdev.jargs;

import org.gmdev.jargs.marshalers.ArgumentMarshaler;
import org.gmdev.jargs.marshalers.BooleanArgumentMarshaler;
import org.gmdev.jargs.marshalers.IntegerArgumentMarshaler;
import org.gmdev.jargs.marshalers.StringArgumentMarshaler;

import java.text.ParseException;
import java.util.*;

public class Args {

    private final String schema;
    private final String[] args;
    private boolean valid = true;
    private final Set<Character> unexpectedArguments = new TreeSet<>();
    private final Map<Character, ArgumentMarshaler> marshalers = new HashMap<>();
    private final Set<Character> argsFound = new HashSet<>();
    private int currentArgument;
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;

    enum ErrorCode {
        OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT, TEST_CODE
    }

    public Args(String schema, String[] args) throws ParseException, ArgsException {
        this.schema = schema;
        this.args = args;
        parse();
    }

    private void parse() throws ParseException {
        if (schema.length() == 0 && args.length == 0)
            return;

        parseSchema();
        try {
            parseArguments();
        } catch (ArgsException e) {
        }
    }

    private void parseSchema() throws ParseException {
        for (String element : schema.split(","))
            if (element.length() > 0)
                parseSchemaElement(element.trim());
    }

    private void parseSchemaElement(String element) throws ParseException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElement(elementId);

        if (isBooleanSchemaElement(elementTail))
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        else if (isStringSchemaElement(elementTail))
            marshalers.put(elementId, new StringArgumentMarshaler());
        else if (isIntegerSchemaElement(elementTail))
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        else
            throw new ParseException(
                    String.format("Argument %c has invalid format %s", elementId, elementTail), 0);
    }

    private void validateSchemaElement(char elementId) throws ParseException {
        if (!Character.isLetter(elementId))
            throw new ParseException(
                    String.format("Bad character: %s in Args format: %s", elementId, schema), 0);
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    private void parseArguments() throws ArgsException {
        for (currentArgument = 0; currentArgument < args.length; currentArgument++)
            parseArgument(args[currentArgument]);
    }

    private void parseArgument(String arg) throws ArgsException {
        if (arg.startsWith("-"))
            parseElements(arg);
    }

    private void parseElements(String arg) throws ArgsException {
        for (int i = 1; i < arg.length(); i++)
            parseElement(arg.charAt(i));
    }

    private void parseElement(char argChar) throws ArgsException {
        if (setArgument(argChar)) {
            argsFound.add(argChar);
        } else {
            unexpectedArguments.add(argChar);
            errorCode = ErrorCode.UNEXPECTED_ARGUMENT;
            valid = false;
        }
    }

    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshalers.get(argChar);
        try {
            if (m instanceof BooleanArgumentMarshaler)
                setBooleanArg(m);
            else if (m instanceof StringArgumentMarshaler)
                setStringArg(m);
            else if (m instanceof IntegerArgumentMarshaler)
                setIntArg(m);
            else
                return false;
        } catch (ArgsException e) {
            valid = false;
            errorArgumentId = argChar;
            throw e;
        }

        return true;
    }

    private void setBooleanArg(ArgumentMarshaler m) {
        try {
            m.set("true");
        } catch (ArgsException e) {
        }
    }

    private void setStringArg(ArgumentMarshaler m) throws ArgsException {
        currentArgument++;
        try {
            m.set(args[currentArgument]);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorCode = ErrorCode.MISSING_STRING;
            throw new ArgsException();
        }
    }

    private void setIntArg(ArgumentMarshaler m) throws ArgsException {
        currentArgument++;
        String parameter = null;
        try {
            parameter = args[currentArgument];
            m.set(parameter);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorCode = ErrorCode.MISSING_INTEGER;
            throw new ArgsException();
        } catch (ArgsException e) {
            errorParameter = parameter;
            errorCode = ErrorCode.INVALID_INTEGER;
            throw e;
        }
    }

    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0)
            return "-[" + schema + "]";
        else
            return "";
    }

    public void setErrorCode(ErrorCode code) {
        errorCode = code;
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c", errorArgumentId);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Invalid integer parameter for -%c", errorArgumentId);
            case UNEXPECTED_ARGUMENT:
                return unexpectedArgumentMessage();
            case OK:
                throw new Exception("TILT: Should not get here");
        }

        return "";
    }

    private String unexpectedArgumentMessage() {
        StringBuilder message = new StringBuilder("Argument(s) -");
        for (char c : unexpectedArguments)
            message.append(c);

        message.append(" unexpected.");

        return message.toString();
    }

    public boolean getBoolean(char arg) {
        ArgumentMarshaler m = marshalers.get(arg);
        boolean b = false;
        try {
            b = m != null && (Boolean) m.get();
        } catch (ClassCastException e) {
            b = false;
        }

        return b;
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am == null ? "" : (String) am.get();
        } catch (ClassCastException e) {
            return "";
        }
    }

    public int getInt(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am == null ? 0 : (Integer) am.get();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

    public boolean isValid() {
        return valid;
    }
}
