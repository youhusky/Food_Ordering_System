package com.bihju.model;

import lombok.Data;

@Data
public class Version {
    private String value;

    public Version(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
