package com.urbanlance.user.repository;

import com.urbanlance.user.model.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<Profile,Long> {

    @Query("SELECT p FROM Profile p WHERE p.authId = :authId")
    Optional<Profile> findByAuthId(@Param("authId") String authId);

    @Query("DELETE FROM PROFILE p WHERE p.authId = :authId")
    void deleteByAuthId(@Param("authId")String authId);
}
