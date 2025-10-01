package com.thealtening.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/* loaded from: L-out.jar:com/thealtening/api/utils/HttpUtils.class */
public class HttpUtils {
    protected String connect(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(str).openStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                sb.append(line).append("\n");
            } else {
                return sb.toString();
            }
        }
    }
}
