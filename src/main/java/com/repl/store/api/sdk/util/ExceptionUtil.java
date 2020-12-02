package com.repl.store.api.sdk.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    /**
     * Get a string representation of a stacktrace
     * @param t The exception to extract the stacktrace from
     * @return The stacktrace in String format
     * */
    public static String getStacktrace(Throwable t) {
        StringWriter sw = new StringWriter();

        try ( PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
        }

        return sw.toString();
    }

}
