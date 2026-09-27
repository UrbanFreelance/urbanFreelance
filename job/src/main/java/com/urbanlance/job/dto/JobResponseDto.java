package com.urbanlance.job.dto;

import com.urbanlance.job.model.WorkType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.Instant;

public record JobResponseDto(

     String jobName,
     Instant applicationDeadline,
     Double wage,
     Long minWorkers,
     Long maxWorkers,
     String Industry,
     WorkType workType,
     int appliedUsersCount
)
{}
