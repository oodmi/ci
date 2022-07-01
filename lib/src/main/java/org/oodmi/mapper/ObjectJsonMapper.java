package org.oodmi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;

public class ObjectJsonMapper {
    private final ObjectMapper objectMapper;

    public ObjectJsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String toJson(T object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T toObject(Response response, TypeReference<T> valueTypeRef) {
        try {
            String jsonAsString = response.body().string();
            return objectMapper.readValue(jsonAsString, valueTypeRef);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
