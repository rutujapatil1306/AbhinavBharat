package com.gtasterix.AbhinavNGO.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationDTO {

    private Integer id;
    private Integer application_id;
    private String firstName;
    private String lastName;
    private String mailID;
    private String education;
    private String mobileNo ;
    private String alternateNo;
    private String fatherName;
    private String motherName;
    private String dob;
    private String address;
    private String state;
    private String pinCode;
    private String category;
    private String maritalStatus;
    private String religion;
    private String citizenOfIndia;
    private String anyDisability;

    private List<QualificationDTO> qualifications;
}
