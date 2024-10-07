package com.gtasterix.AbhinavNGO.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String mailID;

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String mobileNo ;

    private String alternateNo;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String motherName;

    @Column(nullable = false)
    private String dob;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String religion;

    @Column(nullable = false)
    private String citizenOfIndia;

    @Column(nullable = false)
    private String anyDisability;

}
