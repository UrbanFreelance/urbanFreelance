package com.urbanlance.user.repository;

import com.urbanlance.user.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<Profile,Long> {
}
