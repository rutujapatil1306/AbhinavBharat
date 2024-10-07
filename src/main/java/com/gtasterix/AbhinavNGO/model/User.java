package com.gtasterix.AbhinavNGO.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String mobileno;

    @Column(nullable = false)
    private double amount;
}
