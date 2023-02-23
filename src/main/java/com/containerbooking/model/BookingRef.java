package com.containerbooking.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRef {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bookingRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean available;

}
