package com.urbanlance.job.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "work_type")
public class JobWorkType {

    @Id
    private Integer id;
    private WorkType type;
}
