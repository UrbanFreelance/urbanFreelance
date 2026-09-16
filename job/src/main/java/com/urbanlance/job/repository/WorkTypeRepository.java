package com.urbanlance.job.repository;

import com.urbanlance.job.model.JobWorkType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTypeRepository extends CrudRepository<JobWorkType,Integer> {
}
