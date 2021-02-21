package com.alvaroalonso.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Patient {

    private final String id;
    private final String name;
    private final String surname;
    private final String phone;

    @JsonCreator
    public Patient(@JsonProperty(value = "id", required = true) String id,
                   @JsonProperty(value = "name", required = true) String name,
                   @JsonProperty(value = "surname", required = true) String surname,
                   @JsonProperty(value = "phone", required = true) String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;

    }
}
