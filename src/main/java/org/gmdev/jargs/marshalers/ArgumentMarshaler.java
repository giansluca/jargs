package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;

public abstract class ArgumentMarshaler {

    public abstract void set(Iterator<String> currentArgument);

    public abstract void set(String value) throws ArgsException;

    public abstract Object get();

}
