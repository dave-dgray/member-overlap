package co.uk.dgray.validator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TimeRangeSerializer extends StdSerializer<TimeRange> {

    public TimeRangeSerializer() {
        this(null);
    }

    public TimeRangeSerializer(Class<TimeRange> t) {
        super(t);
    }

    private String dateTimeToString(ZonedDateTime zdt) {
        return Optional.ofNullable(zdt)
            .map(dt -> dt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            .orElse(null);
    }

    @Override
    public void serialize(TimeRange value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField("validFrom", dateTimeToString(value.getValidFrom()));
        gen.writeStringField("validTo", dateTimeToString(value.getValidTo()));
        gen.writeEndObject();
    }
}
