package co.uk.dgray.validator;

import lombok.Value;

@Value
public class CreateMembershipDto {

//    @ApiModelProperty(position = 1, required = true,
//        example = "737066b3-10f5-42d5-83f7-70f142aae48b", value = "The unique member id in UUID form.")
    private final String memberId;

//    @ApiModelProperty(position = 2, required = true,
//        example = "e098ce03-d73a-4b12-ac6f-379c9c20fa6b", value = "The unique contract id in UUID form.")
    private final String contractId;

    /**
     * The datetime when the membership was created.
     * Example value: 2018-08-20T17:54:09.33Z
     *
     * Note included as part of the Swagger API as intended for internal use only during migration.
     */
//    @ApiModelProperty(hidden = true)
    private String createdAt;

    /**
     * The datetime when the membership was deleted.  Only supported in the bulk creation service.
     * Example value: 2018-08-20T17:54:09.33Z
     *
     * Note included as part of the Swagger API as intended for internal use only during migration.
     */
//    @ApiModelProperty(hidden = true)
    private String deletedAt;
}
