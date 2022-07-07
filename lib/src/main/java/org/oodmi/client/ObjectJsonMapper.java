package org.oodmi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.Response;

import java.io.IOException;

final class ObjectJsonMapper {

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    private static final ObjectJsonMapper INSTANCE = new ObjectJsonMapper();

    private ObjectJsonMapper() {
    }

    public static ObjectJsonMapper getInstance() {
        return INSTANCE;
    }

    public <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T toObject(Response response, TypeReference<T> valueTypeRef) {
        try {
            String jsonAsString = response.body().string();
            return objectMapper.readValue(jsonAsString, valueTypeRef);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
