package com.traffic.android.util;

import android.os.StrictMode;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebServiceClient {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static TypeReference<Map<String, Object>> ref = new TypeReference<Map<String, Object>>() {
    };

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public WebServiceClient() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public Map<String, Object> getResponse() throws IOException {
        URL url = new URL("http://192.168.0.13:8080/traffic-web-console/TrafficApp/CongestionsResponse/congestions?user=rajat");
        Map<String, Object> response = objectMapper.readValue(url, ref);
        return response;
    }
}
