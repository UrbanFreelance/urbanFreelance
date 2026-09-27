package com.urbanlance.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;
import java.util.List;

@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String authId;

    private String email;
    private String fullName;
    private String preferredName;
    private String profilePhoto;
    private String headline;
    private List<Industry> industries;
    private Gender gender;

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

    public Profile(String authId, String email, String fullName, String preferredName) {
        this.authId = authId;
        this.email = email;
        this.fullName = fullName;
        this.preferredName = preferredName;
    }

    public enum Gender{
        MALE,FEMALE,OTHER
    }
}
