package com.urbanlance.job.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String localAddress;
    @Column(nullable = false)
    private int pinNumber;

    public Address(String country, String state, String district, String localAddress, int pinNumber) {
        this.country = country;
        this.state = state;
        this.district = district;
        this.localAddress = localAddress;
        this.pinNumber = pinNumber;
    }
}
