package com.urbanlance.job.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "job_Applied_users")
@Getter
@Setter
@NoArgsConstructor
public class AppliedUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private Long userId;

    public AppliedUsers(Long jobId, Long userId) {
        this.jobId = jobId;
        this.userId = userId;
    }
}
