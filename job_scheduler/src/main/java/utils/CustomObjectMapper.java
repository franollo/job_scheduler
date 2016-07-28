package main.java.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

/**
 * Created by Marcin Frankowski on 28.07.2016.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new ISO8601DateFormat());
    }
}
