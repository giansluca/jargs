package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;
import org.gmdev.jargs.exception.JargsArgumentException;
import org.gmdev.jargs.exception.JargsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.gmdev.jargs.exception.ErrorCode.*;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue;

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new JargsArgumentException(MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw new JargsArgumentException(INVALID_DOUBLE, null, parameter);
        }
    }

    public static double getValue(ArgumentMarshaler am) {
        if (am instanceof DoubleArgumentMarshaler)
            return ((DoubleArgumentMarshaler) am).doubleValue;
        return 0;
    }
}
