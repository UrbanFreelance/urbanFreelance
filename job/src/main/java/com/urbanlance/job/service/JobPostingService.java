package com.urbanlance.job.service;

import com.urbanlance.job.repository.JobPostingRepository;
import com.urbanlance.job.repository.WorkTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final WorkTypeRepository workTypeRepository;


}
