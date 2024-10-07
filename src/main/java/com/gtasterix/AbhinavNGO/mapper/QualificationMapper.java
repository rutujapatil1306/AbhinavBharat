package com.gtasterix.AbhinavNGO.mapper;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.model.Qualification;

public class QualificationMapper {

    public static QualificationDTO toQualificationDTO (Qualification qualification){

        QualificationDTO qualificationDTO = new QualificationDTO();

        qualificationDTO.setId(qualification.getId());
        qualificationDTO.setPercentage(qualification.getPercentage());
        qualificationDTO.setStandard(qualification.getStandard());
        qualificationDTO.setUniversity(qualification.getUniversity());
        qualificationDTO.setPassingYear(qualification.getPassingYear());
        return qualificationDTO;
    }

    public static Qualification toQualification (QualificationDTO qualificationDTO){

        Qualification qualification = new Qualification();

        qualification.setId(qualificationDTO.getId());
        qualification.setPercentage(qualificationDTO.getPercentage());
        qualification.setStandard(qualificationDTO.getStandard());
        qualification.setUniversity(qualificationDTO.getUniversity());
        qualification.setPassingYear(qualificationDTO.getPassingYear());
        return qualification;
    }

}
