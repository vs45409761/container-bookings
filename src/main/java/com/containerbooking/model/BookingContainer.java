package com.containerbooking.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingContainer {
    public enum ContainerType{
        DRY, REEFER;

    }
    public Integer containerSize;
    public ContainerType containerType;
    @Size(min = 5, max = 20, message = "Length of origin string must be between 5 to 20")
    public String origin;

    @Size(min = 5, max = 20, message = "Length of destination string must be between 5 to 20")
    public String destination;

    @NotNull
    @Min(value = 1, message = "Minimum containers to request is 1")
    @Max(value = 100, message = "Maximum containers to request is 100")
    public Integer quantity;

    @AssertTrue(message = "Container size must be either 20 or 40")
    private boolean isValidContainerSize() {
        return containerSize == 20 || containerSize == 40;
    }

    @AssertFalse(message = "Container size must be either 20 or 40")
    private boolean isInvalidContainerSize() {
        return containerSize != null && (containerSize != 20 && containerSize != 40);
    }

}

