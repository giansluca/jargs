package org.gmdev.jargs.marshalers;

import java.util.Iterator;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {

    private boolean booleanValue = false;

    @Override
    public void set(Iterator<String> currentArgument) {
        booleanValue = true;
    }

    @Override
    public void set(String value) {
    }

    @Override
    public Object get() {
        return booleanValue;
    }


}
