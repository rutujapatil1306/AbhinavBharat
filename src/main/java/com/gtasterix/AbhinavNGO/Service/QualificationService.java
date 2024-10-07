package com.gtasterix.AbhinavNGO.Service;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.mapper.QualificationMapper;
import com.gtasterix.AbhinavNGO.model.Qualification;
import com.gtasterix.AbhinavNGO.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class QualificationService {

    @Autowired
    private QualificationRepository qualificationRepository;

    public QualificationDTO addQualification(QualificationDTO qualificationDTO) {
        Qualification qualification = QualificationMapper.toQualification(qualificationDTO);
         qualificationRepository.save(qualification);
       return QualificationMapper.toQualificationDTO(qualification);
    }

    public QualificationDTO getAll(Integer id){

    }
}
