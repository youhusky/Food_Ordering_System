package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Document
public class AddressInfo {
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
}
