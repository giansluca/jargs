package io.github.giansluca.jargs.marshalers;

import io.github.giansluca.jargs.exception.ErrorCode;
import io.github.giansluca.jargs.exception.JargsArgumentException;
import io.github.giansluca.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {

    private int intValue = 0;

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(ErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new JargsArgumentException(ErrorCode.INVALID_INTEGER, null, parameter);
        }
    }

    public static int getValue(ArgumentMarshaler am) {
        if (am instanceof IntegerArgumentMarshaler)
            return ((IntegerArgumentMarshaler) am).intValue;
        return 0;
    }

}
