package com.gtasterix.AbhinavNGO.DTO;

import com.gtasterix.AbhinavNGO.model.Qualification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationDTO {

    private Integer applicationId;
    private String applyFor;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String mailID;
    private String mobileNo ;
    private String alternateNo;
    private String dob;
    private String maritalStatus;
    private String adharCard;
    private String panCardNo;
    private String organizationName;
    private String workingLocation;
    private String position;
    private String typeOfEngagement;
    private String experienceYear;
    private String experienceMonths;
    private String experienceDays;

    private List<QualificationDTO> qualifications;
    private List<AddressDTO> addresses;

}
