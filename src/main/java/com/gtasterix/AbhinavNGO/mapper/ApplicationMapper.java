package com.gtasterix.AbhinavNGO.mapper;

import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.DTO.UserDTO;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.User;

public class ApplicationMapper {

    public static ApplicationDTO toApplicationDTO(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();

        applicationDTO.setId(application.getId());
        applicationDTO.setFirstName(application.getFirstName());
        applicationDTO.setLastName(application.getLastName());
        applicationDTO.setMailID(application.getMailID());
        applicationDTO.setEducation(application.getEducation());
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

        return applicationDTO;
    }

    public static Application toApplicationEntity(ApplicationDTO applicationDTO) {
        Application applicationEntity = new Application();

        applicationEntity.setId(applicationDTO.getId());
        applicationEntity.setFirstName(applicationDTO.getFirstName());
        applicationEntity.setLastName(applicationDTO.getLastName());
        applicationEntity.setMailID(applicationDTO.getMailID());
        applicationEntity.setEducation(applicationDTO.getEducation());
        applicationEntity.setMobileNo(applicationDTO.getMobileNo());
        applicationEntity.setAlternateNo(applicationDTO.getAlternateNo());
        applicationEntity.setFatherName(applicationDTO.getFatherName());
        applicationEntity.setMotherName(applicationDTO.getMotherName());
        applicationEntity.setDob(applicationDTO.getDob());
        applicationEntity.setAddress(applicationDTO.getAddress());
        applicationEntity.setState(applicationDTO.getState());
        applicationEntity.setPinCode(applicationDTO.getPinCode());
        applicationEntity.setCategory(applicationDTO.getCategory());
        applicationEntity.setMaritalStatus(applicationDTO.getMaritalStatus());
        applicationEntity.setReligion(applicationDTO.getReligion());
        applicationEntity.setCitizenOfIndia(applicationDTO.getCitizenOfIndia());
        applicationEntity.setAnyDisability(applicationDTO.getAnyDisability());

        return applicationEntity;
    }
}
