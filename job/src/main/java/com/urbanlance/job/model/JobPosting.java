package com.urbanlance.job.model;

import com.urbanlance.common.domain.Industry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;

@Table(name = "job_postings")
@Getter
@Setter
@NoArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;
    private Long employerId;
    private Instant applicationDeadline;
    private Double wage;
    private Long minWorkers;
    private Long maxWorkers;
    private JobIndustry industry;
    @Column(name = "work_type")
    @Enumerated(EnumType.STRING)
    private WorkType workType;
    private int appliedUsersCount;
    @Column(name = "location",nullable = false)
    private AggregateReference<Address,Long> location;

    private Instant createdAt;
    private Instant lastModifiedAt;
    private Instant deletedAt;
    private boolean isDeleted;

    @PrePersist
    void persist() {
        this.appliedUsersCount = 0;
        this.createdAt = Instant.now();
        this.isDeleted = false;
    }

    public JobPosting(String jobName, Instant applicationDeadline, Double wage, Long minWorkers, Long maxWorkers, JobIndustry industry,WorkType workType) {
        this.jobName = jobName;
        this.applicationDeadline = applicationDeadline;
        this.wage = wage;
        this.minWorkers = minWorkers;
        this.maxWorkers = maxWorkers;
        this.industry = industry;
        this.workType = workType;
    }
}
