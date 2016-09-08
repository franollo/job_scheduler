package main.java.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom LocalDateTime deserializer.
 * It deserializes ISO date string into LocalDateTime object.
 * @author Marcin Frankowski
 */

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public LocalDateTimeDeserializer() {
        this(null);
    }

    public LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String date = jsonParser.getValueAsString();
        if(date.length() > 19) {
            date = date.substring(0, 19);
        }
        return LocalDateTime.parse(date, formatter);
    }
}
