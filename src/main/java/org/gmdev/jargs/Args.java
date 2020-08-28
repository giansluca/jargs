package org.gmdev.jargs;

import org.gmdev.jargs.marshalers.ArgumentMarshaler;

import java.util.*;

public class Args {

    private final String schema;
    private boolean valid = true;
    private final Set<Character> unexpectedArguments = new TreeSet<>();
    private final Set<Character> argsFound = new HashSet<>();
    private final Map<Character, ArgumentMarshaler> marshalers = new HashMap<>();
    private final List<String> argsList;
    private Iterator<String> currentArgument;

    private char errorArgumentId = '\0';
    private String errorParameter = "TILT!";
    private ArgsException.ErrorCode errorCode = ArgsException.ErrorCode.OK;

    public Args(String schema, String[] args) throws ArgsException {
        this.schema = schema;
        argsList = Arrays.asList(args);
        parse();
    }

    private void parse() throws ArgsException {
        if (schema.length() == 0 && argsList.size() == 0)
            return;

        parseSchema();
        try {
            parseArguments();
        } catch (ArgsException e) {
        }
    }

    private void parseSchema() throws ArgsException {
        for (String element : schema.split(","))
            if (element.length() > 0)
                parseSchemaElement(element.trim());
    }

    private void parseSchemaElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElement(elementId);

        if (isBooleanSchemaElement(elementTail))
            marshalers.put(elementId, new BooleanArgumentMarshaler_2());
        else if (isStringSchemaElement(elementTail))
            marshalers.put(elementId, new StringArgumentMarshaler_2());
        else if (isIntegerSchemaElement(elementTail))
            marshalers.put(elementId, new IntegerArgumentMarshaler_2());
        else
            throw new ArgsException(
                    String.format("Argument %c has invalid format %s", elementId, elementTail));
    }

    private void validateSchemaElement(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(
                    String.format("Bad character: %s in Args format: %s", elementId, schema));
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
        for (currentArgument = argsList.iterator(); currentArgument.hasNext(); )
            parseArgument(currentArgument.next());
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
            errorCode = ArgsException.ErrorCode.UNEXPECTED_ARGUMENT;
            valid = false;
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
            valid = false;
            errorArgumentId = argChar;
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
        try {
            return m != null && (Boolean) m.get();
        } catch (ClassCastException e) {
             return false;
        }
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

    public class BooleanArgumentMarshaler_2 extends ArgumentMarshaler {
        private boolean booleanValue = false;

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
            booleanValue = true;
        }

        @Override
        public Object get() {
            return booleanValue;
        }
    }

    public class IntegerArgumentMarshaler_2 extends ArgumentMarshaler {
        private int intValue = 0;

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
            String parameter = null;
            try {
                parameter = currentArgument.next();
                intValue = Integer.parseInt(parameter);
            } catch (NoSuchElementException e) {
                errorCode = ArgsException.ErrorCode.MISSING_INTEGER;
                throw new ArgsException();
            } catch (NumberFormatException e) {
                errorParameter = parameter;
                errorCode = ArgsException.ErrorCode.INVALID_INTEGER;
                throw new ArgsException();
            }
        }

        @Override
        public Object get() {
            return intValue;
        }
    }

    public class StringArgumentMarshaler_2 extends ArgumentMarshaler {
        private String stringValue = "";

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
            try {
                stringValue = currentArgument.next();
            } catch (NoSuchElementException e) {
                errorCode = ArgsException.ErrorCode.MISSING_STRING;
                throw new ArgsException();
            }
        }

        @Override
        public Object get() {
            return stringValue;
        }

    }
}
