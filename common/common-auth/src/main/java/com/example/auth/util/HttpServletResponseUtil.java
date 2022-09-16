package com.example.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author cjy
 */
public final class HttpServletResponseUtil {

    private static final String UTF8 = "UTF-8";
    private static final String CONTENT_TYPE = "application/json";

    private HttpServletResponseUtil() {
        throw new AssertionError();
    }

    public static void printJson(HttpServletResponse response, Object object) throws Exception {
        response.setCharacterEncoding(UTF8);
        response.setContentType(CONTENT_TYPE);
        PrintWriter printWriter = response.getWriter();
        ObjectMapper om = new ObjectMapper();
        printWriter.write(om.writeValueAsString(object));
        printWriter.flush();
        printWriter.close();
    }
}