package com.gtasterix.AbhinavNGO.mapper;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.Qualification;
import org.springframework.stereotype.Component;

@Component
public class QualificationMapper {

    public static QualificationDTO toQualificationDTO(Qualification qualification) {
        QualificationDTO qualificationDTO = new QualificationDTO();

        qualificationDTO.setApplicationId(qualification.getApplication() != null ? qualification.getApplication().getApplicationId() : null);
        qualificationDTO.setQualificationId(qualification.getQualificationId());
        qualificationDTO.setPercentage(qualification.getPercentage());
        qualificationDTO.setStandard(qualification.getStandard());
        qualificationDTO.setUniversity(qualification.getUniversity());
        qualificationDTO.setPassingYear(qualification.getPassingYear());
        return qualificationDTO;
    }

    public static Qualification toQualification(QualificationDTO qualificationDTO) {
        Qualification qualification = new Qualification();

        qualification.setQualificationId(qualificationDTO.getQualificationId());
        qualification.setPercentage(qualificationDTO.getPercentage());
        qualification.setStandard(qualificationDTO.getStandard());
        qualification.setUniversity(qualificationDTO.getUniversity());
        qualification.setPassingYear(qualificationDTO.getPassingYear());

        // Set the application
        if (qualificationDTO.getApplicationId() != null) {
            Application application = new Application();
            application.setApplicationId(qualificationDTO.getApplicationId());
            qualification.setApplication(application);
        }

        return qualification;
    }
}
