package com.urbanlance.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;

@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String profilePhoto;
    private String headline;
    private Industry industry;

    private Instant createdAt;
    private Instant deletedAt;
    private boolean isDeleted;

    @Column(name = "address_id")
    private AggregateReference<Address,Long> address;


    @PrePersist
    void persist(){
        this.createdAt = Instant.now();
        this.isDeleted = false;
    }


}
