package com.urbanlance.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    private Long id;
    private String country;
    private String state;
    private String district;
    private String localAddress;
    private String pinNumber;

}
