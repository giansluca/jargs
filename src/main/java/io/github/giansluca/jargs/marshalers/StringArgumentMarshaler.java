package io.github.giansluca.jargs.marshalers;

import io.github.giansluca.jargs.exception.ErrorCode;
import io.github.giansluca.jargs.exception.JargsArgumentException;
import io.github.giansluca.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaler implements ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(ErrorCode.MISSING_STRING);
        }
    }

    public static String getValue(ArgumentMarshaler am) {
        if (am instanceof StringArgumentMarshaler)
            return ((StringArgumentMarshaler) am).stringValue;
        return "";
    }

}
