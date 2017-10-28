package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreditCardInfo {
    private String firstName;
    private String lastName;
    private int expiredMonth;
    private int expiredYear;
    private int securityCode;
}
