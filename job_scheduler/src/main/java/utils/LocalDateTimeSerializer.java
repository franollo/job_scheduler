package main.java.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Marcin Frankowski on 29.07.16.
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime>{

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String year = String.format("%04d", localDateTime.getYear());
        String month = String.format("%02d", localDateTime.getMonthValue());
        String day = String.format("%02d", localDateTime.getDayOfMonth());
        String hour = String.format("%02d", localDateTime.getHour());
        String minute = String.format("%02d", localDateTime.getMinute());
        String second = String.format("%02d", localDateTime.getSecond());
        String dateString = year + '-' + month + '-' + day + 'T' + hour + ':' + minute + ':' + second;
        jsonGenerator.writeString(dateString);
    }
}
