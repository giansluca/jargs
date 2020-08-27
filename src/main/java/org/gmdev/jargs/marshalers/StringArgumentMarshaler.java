package org.gmdev.jargs.marshalers;

import java.util.Iterator;

public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(Iterator<String> currentArgument) {
    }

    @Override
    public void set(String value) {
        stringValue = value;
    }

    @Override
    public Object get() {
        return stringValue;
    }


}
