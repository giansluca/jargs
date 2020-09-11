package io.github.giansluca.jargs.marshalers;

import io.github.giansluca.jargs.exception.ErrorCode;
import io.github.giansluca.jargs.exception.JargsArgumentException;
import io.github.giansluca.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue;

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(ErrorCode.MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw new JargsArgumentException(ErrorCode.INVALID_DOUBLE, null, parameter);
        }
    }

    public static double getValue(ArgumentMarshaler am) {
        if (am instanceof DoubleArgumentMarshaler)
            return ((DoubleArgumentMarshaler) am).doubleValue;
        return 0;
    }
}
