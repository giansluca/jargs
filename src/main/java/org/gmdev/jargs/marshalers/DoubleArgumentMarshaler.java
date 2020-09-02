package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.gmdev.jargs.ArgsException.ErrorCode.*;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw new ArgsException(INVALID_DOUBLE, parameter);
        }
    }

    @Override
    public Object get() {
        return doubleValue;
    }
}