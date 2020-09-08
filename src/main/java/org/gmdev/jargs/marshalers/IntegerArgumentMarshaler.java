package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.gmdev.jargs.exception.ErrorCode.INVALID_INTEGER;
import static org.gmdev.jargs.exception.ErrorCode.MISSING_INTEGER;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {

    private int intValue = 0;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new ArgsException(INVALID_INTEGER, parameter);
        }
    }

    public static int getValue(ArgumentMarshaler am) {
        if (am instanceof IntegerArgumentMarshaler)
            return ((IntegerArgumentMarshaler) am).intValue;
        return 0;
    }

}
