package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@Document
public class Order {
    @Id
    String id;

    private String restaurantId;
    private List<ItemQuantity> items;
    private int totalPrice;
    private long orderTime;
    private String specialNote;
    private long deliveryTime;
    private String paymentId;

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
