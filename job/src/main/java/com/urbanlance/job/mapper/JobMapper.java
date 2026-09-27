package com.urbanlance.job.mapper;

import com.urbanlance.job.dto.CreateJobRequest;
import com.urbanlance.job.model.Address;
import com.urbanlance.job.model.JobPosting;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobPosting toJobPostingEntity(CreateJobRequest request){
        return new JobPosting(
                request.getJobName(),
                request.getApplicationDeadline(),
                request.getWage(),
                request.getMinWorkers(),
                request.getMaxWorkers(),
                request.getIndustry(),
                request.getWorkType()
        );
    }

    public Address toAddressEntity(CreateJobRequest request){
        return new Address(
                request.getCountry(),
                request.getState(),
                request.getDistrict(),
                request.getLocalAddress(),
                request.getPinNumber()
        );
    }
}
