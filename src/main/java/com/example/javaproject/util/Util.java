package com.example.javaproject.util;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Util {
   public static Map<String, Object> generateErrorBody(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        return body;
    }
}
