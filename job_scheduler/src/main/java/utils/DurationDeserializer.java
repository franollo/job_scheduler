package main.java.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom Duration deserializer.
 * It deserializes string into Duration object.
 * @author Marcin Frankowski
 */
public class DurationDeserializer extends StdDeserializer<Duration> {
    public DurationDeserializer() {
        this(null);
    }

    public DurationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String stringDuration = jsonParser.getValueAsString();
        if(stringDuration.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]") == false) {
            return Duration.ZERO;
        }
        String[] output = stringDuration.split("\\:");
        long seconds = Long.parseLong(output[2]);
        long minutes = Long.parseLong(output[1]);
        long hours = Long.parseLong(output[0]);
        return Duration.ofSeconds(seconds + 60*minutes + 3600*hours);
    }
}
