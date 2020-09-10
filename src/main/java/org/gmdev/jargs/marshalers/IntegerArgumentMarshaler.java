package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.exception.JargsArgumentException;
import org.gmdev.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.gmdev.jargs.exception.ErrorCode.INVALID_INTEGER;
import static org.gmdev.jargs.exception.ErrorCode.MISSING_INTEGER;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {

    private int intValue = 0;

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new JargsArgumentException(INVALID_INTEGER, null, parameter);
        }
    }

    public static int getValue(ArgumentMarshaler am) {
        if (am instanceof IntegerArgumentMarshaler)
            return ((IntegerArgumentMarshaler) am).intValue;
        return 0;
    }

}
