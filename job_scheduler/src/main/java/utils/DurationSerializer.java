package main.java.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 29.07.16.
 */
public class DurationSerializer extends StdSerializer<Duration>{

    public DurationSerializer() {
        this(null);
    }

    public DurationSerializer(Class<Duration> t) {
        super(t);
    }

    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long totalSeconds = duration.getSeconds();
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = (totalSeconds / 3600) % 24;
        String sec = String.format("%02d", (int)seconds);
        String min = String.format("%02d", (int)minutes);
        String hh = String.format("%02d", (int)hours);
        String durationString = hh + ":" + min + ":" + sec;
        jsonGenerator.writeString(durationString);
    }
}
