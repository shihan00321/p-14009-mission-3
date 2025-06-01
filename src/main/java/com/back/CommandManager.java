package com.back;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandManager {
    private final String actionName;
    private final Map<String, String> paramsMap;

    public CommandManager(String cmd) {
        // cmd = "목록 ? searchKeywordType=content&searchKeyword=자바&page=2"
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        // searchKeywordType=content & searchKeyword=자바&page=2"
        String[] queryStringBits = queryString.split("&");
        paramsMap = Arrays.stream(queryStringBits)
                .map(part -> part.split("=", 2))
                .filter(bits -> bits.length > 1 && !bits[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        bits -> bits[0].trim(),
                        bits -> bits[1].trim()
                ));

    }

    public String getActionName() {
        return actionName;
    }

    public int getParamAsInt(String paramName, int defaultValue) {
        String value = getParam(paramName);
        if (value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getParam(String paramName) {
        return paramsMap.getOrDefault(paramName, "");
    }
}