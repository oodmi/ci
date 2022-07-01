package org.oodmi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.oodmi.mapper.ObjectJsonMapper;

final class ConstantHolder {

    static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    static final ObjectJsonMapper objectJsonMapper = new ObjectJsonMapper(objectMapper);

    private ConstantHolder() {
    }
}
