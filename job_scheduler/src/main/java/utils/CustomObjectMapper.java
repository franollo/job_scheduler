package main.java.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import main.java.model.Order;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class which allows to add custom serializers and deserializers to Jackson
 * @author Marcin Frankowski
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(Duration.class, new DurationSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addDeserializer(Duration.class, new DurationDeserializer());
        registerModule(module);
    }
}
