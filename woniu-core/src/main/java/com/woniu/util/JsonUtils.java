package com.woniu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Convert object to json
     *
     * @return json string
     */
    public static final String toJson(final Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Convert string to given type
     *
     * @return instance of type
     */
    public static final <V> V fromJson(String json, Class<V> type) {

        try {
            return mapper.readValue(json, type);
        } catch (Exception err) {
//            Log.i("mapper.readValue", err.getMessage());
            return null;
        }
    }

    public static <T> ArrayList<T> fromJson2Array(String json, Class<T> cls) {

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, cls);
            ArrayList<T> list = mapper.readValue(json, javaType);

            return list;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
