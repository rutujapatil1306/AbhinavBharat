package com.gtasterix.AbhinavNGO.mapper;

import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.Qualification;

import java.util.ArrayList;
import java.util.List;


public class ApplicationMapper {
    public static Application toApplicationEntity(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setFirstName(applicationDTO.getFirstName());
        application.setLastName(applicationDTO.getLastName());
        application.setMailID(applicationDTO.getMailID());
        application.setMobileNo(applicationDTO.getMobileNo());
        application.setAlternateNo(applicationDTO.getAlternateNo());
        application.setFatherName(applicationDTO.getFatherName());
        application.setMotherName(applicationDTO.getMotherName());
        application.setDob(applicationDTO.getDob());
        application.setAddress(applicationDTO.getAddress());
        application.setState(applicationDTO.getState());
        application.setPinCode(applicationDTO.getPinCode());
        application.setCategory(applicationDTO.getCategory());
        application.setMaritalStatus(applicationDTO.getMaritalStatus());
        application.setReligion(applicationDTO.getReligion());
        application.setCitizenOfIndia(applicationDTO.getCitizenOfIndia());
        application.setAnyDisability(applicationDTO.getAnyDisability());
        application.setQualifications(new ArrayList<>()); // Initialize the qualifications collection
        return application;
    }

    public static ApplicationDTO toApplicationDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getApplicationId());
        applicationDTO.setFirstName(application.getFirstName());
        applicationDTO.setLastName(application.getLastName());
        applicationDTO.setMailID(application.getMailID());
        applicationDTO.setMobileNo(application.getMobileNo());
        applicationDTO.setAlternateNo(application.getAlternateNo());
        applicationDTO.setFatherName(application.getFatherName());
        applicationDTO.setMotherName(application.getMotherName());
        applicationDTO.setDob(application.getDob());
        applicationDTO.setAddress(application.getAddress());
        applicationDTO.setState(application.getState());
        applicationDTO.setPinCode(application.getPinCode());
        applicationDTO.setCategory(application.getCategory());
        applicationDTO.setMaritalStatus(application.getMaritalStatus());
        applicationDTO.setReligion(application.getReligion());
        applicationDTO.setCitizenOfIndia(application.getCitizenOfIndia());
        applicationDTO.setAnyDisability(application.getAnyDisability());

        // Map the qualifications
        List<Qualification> qualifications = application.getQualifications();
        if (qualifications != null) {
            List<QualificationDTO> qualificationDTOs = new ArrayList<>();
            for (Qualification qualification : qualifications) {
                QualificationDTO qualificationDTO = QualificationMapper.toQualificationDTO(qualification);
                qualificationDTOs.add(qualificationDTO);
            }
            applicationDTO.setQualifications(qualificationDTOs);
        }

        return applicationDTO;
    }
}