package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;
import org.gmdev.jargs.exception.JargsArgumentException;
import org.gmdev.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.gmdev.jargs.exception.ErrorCode.MISSING_STRING;

public class StringArgumentMarshaler implements ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(MISSING_STRING);
        }
    }

    public static String getValue(ArgumentMarshaler am) {
        if (am instanceof StringArgumentMarshaler)
            return ((StringArgumentMarshaler) am).stringValue;
        return "";
    }

}
