package com.limbo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 *
 * Created by limbo on 17-2-6.
 */
public final class JacksonUtil {

    private static ObjectMapper objectMapper;

    public static <T>T fullDataBinding(String json, Class<T> valueType) throws IOException {
        if(objectMapper==null){
            objectMapper = new ObjectMapper();
        }
            return objectMapper.readValue(json, valueType);

    }

    public static String fullDataConversion(Object object) throws JsonProcessingException {
        if(objectMapper==null){
            objectMapper = new ObjectMapper();
        }
        return objectMapper.writeValueAsString(object);
    }


}
