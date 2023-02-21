package com.containerbooking.model;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class BookingContainer {
    enum ContainerType{
        DRY, REEFER;

    }
    private Integer containerSize;
    private ContainerType containerType;
    @Size(min = 5, max = 20, message = "Length of origin string must be between 5 to 20")
    private String origin;

    @Size(min = 5, max = 20, message = "Length of destination string must be between 5 to 20")
    private String destination;

    @NotNull
    @Min(value = 1, message = "Minimum containers to request is 1")
    @Max(value = 100, message = "Maximum containers to request is 100")
    private Integer quantity;

    @AssertTrue(message = "Container size must be either 20 or 40")
    private boolean isValidContainerSize() {
        return containerSize == 20 || containerSize == 40;
    }

    @AssertFalse(message = "Container size must be either 20 or 40")
    private boolean isInvalidContainerSize() {
        return containerSize != null && (containerSize != 20 && containerSize != 40);
    }

}

