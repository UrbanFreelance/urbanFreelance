package com.urbanlance.job.dto;


import com.urbanlance.common.domain.Industry;
import com.urbanlance.job.model.JobIndustry;
import com.urbanlance.job.model.WorkType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {

    @NotBlank
    private String jobName;
    @NotNull
    @Future
    private Instant applicationDeadline;
    @PositiveOrZero
    private Double wage;
    @Positive
    @Min(value = 1)
    private Long minWorkers;
    @Positive
    @Max(value = Integer.MAX_VALUE)
    private Long maxWorkers;
    @NotBlank
    private JobIndustry industry;
    @NotNull
    private WorkType workType;

//     address
    @NotBlank
    private String country;
    @NotBlank
    private String state;
    @NotBlank
    private String district;
    @NotBlank
    private String localAddress;
    @Positive
    @Max(value = 999999)
    private int pinNumber;
}
