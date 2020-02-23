package co.uk.dgray.validator;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtils {

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // Create a custom formatter since postgres uses a non-ISO datetime format
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern(TIME_FORMAT)
        .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
        .appendOffset("+HH", "Z")
        .toFormatter();
}
