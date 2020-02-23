package co.uk.dgray.validator;

import static co.uk.dgray.validator.DateTimeUtils.DATE_TIME_FORMATTER;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Optional;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@JsonSerialize(using = TimeRangeSerializer.class)
public class TimeRange {

    private final ZonedDateTime validFrom;
    private final ZonedDateTime validTo;

    public TimeRange(String validFrom, String validTo) {
        this.validFrom = validFrom==null? ZonedDateTime.now() : ZonedDateTime.parse(validFrom);
        this.validTo = validTo==null? ZonedDateTime.now() : ZonedDateTime.parse(validTo);
    }

    private static String dateTimeToString(ZonedDateTime dt) {
        return Optional.ofNullable(dt)
            .map(zonedDateTime -> zonedDateTime.format(DATE_TIME_FORMATTER))
            .orElse("");
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", dateTimeToString(validFrom), dateTimeToString(validTo));
    }

    public boolean isOverlapped(TimeRange other)
    {
        TimeRange thisTimeframe = TimeRange.builder()
                .validFrom (validFrom == null ? ZonedDateTime.now() : validFrom)
                .validTo (validTo == null ? ZonedDateTime.now() : validTo)
                .build();
        TimeRange otherTimeframe = TimeRange.builder()
                .validFrom (other.validFrom == null ? ZonedDateTime.now() : other.validFrom)
                .validTo (other.validTo == null ? ZonedDateTime.now() : other.validTo)
                .build();
        return thisTimeframe.validFrom.compareTo(otherTimeframe.validTo) < 0 &&
                otherTimeframe.validFrom.compareTo(thisTimeframe.validTo) < 0;
    }
}
