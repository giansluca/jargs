package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.exception.JargsException;

import java.util.Iterator;

public class BooleanArgumentMarshaler implements ArgumentMarshaler {

    private boolean booleanValue = false;

    @Override
    public void set(Iterator<String> currentArgument) throws JargsException {
        booleanValue = true;
    }

    public static boolean getValue(ArgumentMarshaler am) {
        if (am != null && am instanceof BooleanArgumentMarshaler)
             return ((BooleanArgumentMarshaler) am).booleanValue;

        return false;
    }

}
