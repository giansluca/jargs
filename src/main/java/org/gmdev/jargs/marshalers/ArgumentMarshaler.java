package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;

public interface ArgumentMarshaler {

    public abstract void set(Iterator<String> currentArgument) throws ArgsException;

    public abstract Object get();

}
