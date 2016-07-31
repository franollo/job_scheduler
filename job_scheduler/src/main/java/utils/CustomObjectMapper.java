package main.java.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import main.java.model.Order;

import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 28.07.2016.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(Order.class, new OrderSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        registerModule(module);
    }
}
