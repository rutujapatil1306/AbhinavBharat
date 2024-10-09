package com.gtasterix.AbhinavNGO.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qualification

{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String standard;
    private String university;
    private String passingYear;
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

}


