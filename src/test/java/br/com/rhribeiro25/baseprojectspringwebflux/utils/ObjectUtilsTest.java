package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtilsTest {

    private static final ObjectMapper serializer = new ObjectMapper();

    public final static String getObjecAsString(Object obj) throws JsonProcessingException {
        return serializer.writeValueAsString(obj);
    }
}