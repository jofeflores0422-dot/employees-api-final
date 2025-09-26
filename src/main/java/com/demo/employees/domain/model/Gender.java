package com.demo.employees.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    M, F, O;

    @JsonCreator
    public static Gender from(String v) {
        return Gender.valueOf(v.trim().toUpperCase());
    }

    @JsonValue
    public String toJson() { return name(); }
}
