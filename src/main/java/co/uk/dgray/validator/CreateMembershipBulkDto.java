package co.uk.dgray.validator;

import lombok.Value;

import java.util.List;

@Value
public class CreateMembershipBulkDto {

    //@ApiModelProperty(position = 1, required = true, value = "A list of unique memberships.")
    private final List<CreateMembershipDto> memberships;
}
