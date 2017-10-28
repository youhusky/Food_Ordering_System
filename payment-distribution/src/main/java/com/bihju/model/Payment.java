package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Payment {
    @Id
    String id;

    private long timestamp;
    private int amount;
    private String orderId;
    private CreditCardInfo creditCardInfo;

    @JsonIgnore
    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }
}
