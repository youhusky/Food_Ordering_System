package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfo {
    private String id;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
