package io.github.giansluca.jargs;

import io.github.giansluca.jargs.exception.JargsException;

public class Main {

    public static void main(String[] args) {
        String schema = "first*, second#, third%, forth@";
        Jargs arguments = null;

        try {
            arguments = new Jargs(schema, args);
        } catch (JargsException e) {
            e.printStackTrace();
        }

        String stringValue = arguments.getString("first");
        int intValue = arguments.getInt("second");
        boolean boolValue = arguments.getBoolean("third");
        double doubleValue = arguments.getDouble("forth");

        System.out.println(
                String.format("%s, %d, %b, %f", stringValue, intValue, boolValue, doubleValue));
    }
}
