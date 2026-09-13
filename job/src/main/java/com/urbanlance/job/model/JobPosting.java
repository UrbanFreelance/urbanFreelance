package com.urbanlance.job.model;

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
    private Instant applicationDeadline;
    private Double wage;
    private Long minWorkers;
    private Long maxWorkers;
    private String Industry;
    @Column(name = "work_type")
    private AggregateReference<JobWorkType,Integer> workType;

    private Instant createdAt;
    private Instant lastModifiedAt;
    private Instant deletedAt;
    private boolean isDeleted;

    @PrePersist
    void persist() {
        this.createdAt = Instant.now();
        this.isDeleted = false;
    }

    public JobPosting(String jobName, Instant applicationDeadline, Double wage, Long minWorkers, Long maxWorkers, String industry) {
        this.jobName = jobName;
        this.applicationDeadline = applicationDeadline;
        this.wage = wage;
        this.minWorkers = minWorkers;
        this.maxWorkers = maxWorkers;
        Industry = industry;
    }
}
