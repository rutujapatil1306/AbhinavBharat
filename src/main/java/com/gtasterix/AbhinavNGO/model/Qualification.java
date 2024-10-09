package com.gtasterix.AbhinavNGO.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qualification

{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer QualificationId;

    private String standard;
    private String university;
    private String passingYear;
    private Double percentage;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", nullable = false)
    private Application application;

}


