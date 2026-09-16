package com.urbanlance.job.repository;

import com.urbanlance.job.model.JobPosting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends CrudRepository<JobPosting,Long> {
}
