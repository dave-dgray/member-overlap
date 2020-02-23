package co.uk.dgray.validator;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value
class ErrorDetailsDto {
    String location;
    String field;
}

class ValidationExceptionThrower {

    void throwBadRequestExceptionIfErrors(List<ErrorDetailsDto> errors) {
        if (!errors.isEmpty()) {
            throw new BadRequestException(Arrays.toString(errors.toArray(new ErrorDetailsDto[0])));
        }
    }

}

@AllArgsConstructor
class BulkMembershipValidator {

    public static final String LOCATION_BODY = "body";
    public static final String MEMBERSHIPS_FIELD = "memberships";

    ValidationExceptionThrower exceptionThrower;

    public void validateTimeFrame(CreateMembershipBulkDto dto) {

        List<ErrorDetailsDto> errors = new ArrayList<>();
        List<TimeRange> validTimeFrames = new ArrayList<>();

        dto.getMemberships().forEach(
                membership -> {
                    TimeRange membershipRange = new TimeRange(membership.getCreatedAt(), membership.getDeletedAt());

                    boolean timeRangeOverlaps = validTimeFrames.stream()
                            .anyMatch(validTimeFrame -> validTimeFrame.isOverlapped(membershipRange));

                    if (timeRangeOverlaps) {
                        errors.add(new ErrorDetailsDto(LOCATION_BODY, MEMBERSHIPS_FIELD));
                    } else {
                        validTimeFrames.add(membershipRange);
                    }
                });

        exceptionThrower.throwBadRequestExceptionIfErrors(errors);

    }

}
