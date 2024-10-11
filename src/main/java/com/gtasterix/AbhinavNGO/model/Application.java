package com.gtasterix.AbhinavNGO.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(nullable = false)
    private String applyFor;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String mailID;

    @Column(nullable = false)

    private String mobileNo;

    private String alternateNo;

    @Column(nullable = false)
    private String dob;

    @Column(nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String adharCard;

    @Column(nullable = false)
    private String panCardNo;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Qualification> qualifications = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();

    private String organizationName;
    private String workingLocation;
    private String position;
    private String typeOfEngagement;
    private String experienceYear;
    private String experienceMonths;
    private String experienceDays;

    // Method to add a Qualification
    public void addQualification(Qualification qualification) {
        qualifications.add(qualification);
        qualification.setApplication(this);
    }

    // Method to remove a Qualification
    public void removeQualification(Qualification qualification) {
        qualifications.remove(qualification);
        qualification.setApplication(null);
    }

    // Method to add an Address
    public void addAddress(Address address) {
        addresses.add(address);
        address.setApplication(this);
    }

    // Method to remove an Address
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setApplication(null);
    }
}
