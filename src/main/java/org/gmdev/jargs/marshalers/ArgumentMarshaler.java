package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;

public interface ArgumentMarshaler {

    void set(Iterator<String> currentArgument) throws ArgsException;
}
