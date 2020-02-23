package co.uk.dgray.validator;

import static co.uk.dgray.validator.BulkMembershipValidator.LOCATION_BODY;
import static co.uk.dgray.validator.BulkMembershipValidator.MEMBERSHIPS_FIELD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class BulkMembershipValidatorTest {

    @Mock
    ValidationExceptionThrower exceptionThrower;

    @InjectMocks
    BulkMembershipValidator bulkMembershipValidator;

    public static final List<ErrorDetailsDto> NO_TIME_FRAME_ERROR = Collections.EMPTY_LIST;
    public static final List<ErrorDetailsDto> OVERLAPPING_TIME_FRAME_ERROR = Collections.singletonList(new ErrorDetailsDto(LOCATION_BODY, MEMBERSHIPS_FIELD));

    public static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.now().minusDays(1);
    public static final ZonedDateTime DEFAULT_DELETED_AT = DEFAULT_CREATED_AT.plusDays(1);

    @Test
    public void validateTimeFrame_TwoMemberships_BothCreatedSameTime_NeitherDeleted(){
        // given
        CreateMembershipBulkDto dto = new CreateMembershipBulkDto(
                Arrays.asList(
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_CREATED_AT.toString(), null),
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_CREATED_AT.toString(), null)
                )
        );

        // when
        bulkMembershipValidator.validateTimeFrame(dto);

        // then
        Mockito.verify(exceptionThrower).throwBadRequestExceptionIfErrors(OVERLAPPING_TIME_FRAME_ERROR);
    }

    @Test
    public void validateTimeFrame_TwoMemberships_SecondCreatedAfterFirstDeleted(){
        // given
        CreateMembershipBulkDto dto = new CreateMembershipBulkDto(
                Arrays.asList(
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_CREATED_AT.toString(), DEFAULT_DELETED_AT.toString()),
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_DELETED_AT.plusMinutes(5).toString(), null)
                )
        );

        // when
        bulkMembershipValidator.validateTimeFrame(dto);

        // then
        Mockito.verify(exceptionThrower).throwBadRequestExceptionIfErrors(NO_TIME_FRAME_ERROR);
    }

    @Test
    public void validateTimeFrame_TwoMemberships_SecondCreatedBeforeFirstDeleted(){
        // given
        CreateMembershipBulkDto dto = new CreateMembershipBulkDto(
                Arrays.asList(
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_CREATED_AT.toString(), DEFAULT_DELETED_AT.toString()),
                        new CreateMembershipDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                                DEFAULT_DELETED_AT.minusMinutes(5).toString(), null)
                )
        );

        // when
        bulkMembershipValidator.validateTimeFrame(dto);

        // then
        Mockito.verify(exceptionThrower).throwBadRequestExceptionIfErrors(OVERLAPPING_TIME_FRAME_ERROR);
    }

}