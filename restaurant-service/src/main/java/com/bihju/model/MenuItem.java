package com.bihju.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Document
public class MenuItem {
    @Id
    String id;

    private String restaurantId;
    private String name;
    private String description;
    private int price;
}
