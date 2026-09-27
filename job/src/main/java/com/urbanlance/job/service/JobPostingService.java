package com.urbanlance.job.service;

import com.urbanlance.job.dto.CreateJobRequest;
import com.urbanlance.job.dto.JobResponseDto;
import com.urbanlance.job.exception.JobNotFoundException;
import com.urbanlance.job.mapper.JobMapper;
import com.urbanlance.job.model.Address;
import com.urbanlance.job.model.JobPosting;
import com.urbanlance.job.repository.AddressRepository;
import com.urbanlance.job.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final TransactionTemplate transactionTemplate;
    private final JobMapper jobMapper;

    public void addJob(CreateJobRequest jobDto){
        JobPosting jobPosting = jobMapper.toJobPostingEntity(jobDto);
        Address address = jobMapper.toAddressEntity(jobDto);

        JobPosting savedJob = transactionTemplate.execute(status -> {
            Address savedAddress = addressRepository.save(address);
            jobPosting.setLocation(AggregateReference.to(savedAddress.getId()));
            return jobPostingRepository.save(jobPosting);
        });
    }

    public JobResponseDto getJobById(Long id){
        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(()->new JobNotFoundException("could not find the job" + id, HttpStatus.NOT_FOUND));

        return modelMapper.map(jobPosting, JobResponseDto.class);
    }

    public String delete(Long id){
        return transactionTemplate.execute(status -> {
            jobPostingRepository.deleteById(id);
            return "deleted successfully";
        });
    }


}
