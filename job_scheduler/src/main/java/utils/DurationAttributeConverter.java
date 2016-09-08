package main.java.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * Converts Duration to number field in database, and number field from database to Duration.
 * @author Marcin Frankowski
 */

@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration == null ? null : duration.getSeconds());
    }

    @Override
    public Duration convertToEntityAttribute(Long longDur) {
        return (longDur == null ? null : Duration.ofSeconds(longDur));
    }
}
