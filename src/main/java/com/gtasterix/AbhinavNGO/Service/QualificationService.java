package com.gtasterix.AbhinavNGO.Service;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.mapper.QualificationMapper;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.Qualification;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import com.gtasterix.AbhinavNGO.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
// QualificationService.java
public class QualificationService {

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public QualificationDTO addQualification(QualificationDTO qualificationDTO) throws Exception {

        Application application = applicationRepository.findById(qualificationDTO.getApplicationId())
                .orElseThrow(() -> new Exception("Application not found"));

        Qualification qualification = QualificationMapper.toQualification(qualificationDTO);
        qualification.setApplication(application); // Set the existing application
        qualificationRepository.save(qualification);
        return QualificationMapper.toQualificationDTO(qualification);
    }

    public List<QualificationDTO> getAll() {
        List<Qualification> qualificationList = qualificationRepository.findAll();
        List<QualificationDTO> qualificationDTOList = new ArrayList<>();

        for (Qualification qualification : qualificationList){
            qualificationDTOList.add(QualificationMapper.toQualificationDTO(qualification));
        }
        return qualificationDTOList;
    }

    public QualificationDTO updateQualificationById(Integer id, QualificationDTO updatedQualificationDTO) throws Exception {

        if (updatedQualificationDTO.getApplicationId() == null) {
            throw new Exception("Application ID is required");
        }

        Qualification existingQualification = qualificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID Not Found"));

        ValidateQualificationDTO(updatedQualificationDTO);

        existingQualification.setStandard(updatedQualificationDTO.getStandard());
        existingQualification.setUniversity(updatedQualificationDTO.getUniversity());
        existingQualification.setPassingYear(updatedQualificationDTO.getPassingYear());
        existingQualification.setPercentage(updatedQualificationDTO.getPercentage());

        qualificationRepository.save(existingQualification);

        return QualificationMapper.toQualificationDTO(existingQualification);
    }

    public QualificationDTO patchQualificationById(Integer id, QualificationDTO patchBody) throws Exception {

        if (patchBody.getApplicationId() == null) {
            throw new Exception("Application ID is required");
        }

        Qualification existingQualification = qualificationRepository.findById(id)
                .orElseThrow(() -> new Exception("Qualification with ID " + id + " not found."));

        if (patchBody.getStandard() != null ) {
            existingQualification.setStandard(patchBody.getStandard());
        }
        if (patchBody.getUniversity() != null) {
            existingQualification.setUniversity(patchBody.getUniversity());
        }
        if (patchBody.getPassingYear() != null) {
            existingQualification.setPassingYear(patchBody.getPassingYear());
        }
        if (patchBody.getPercentage() != null) {
            existingQualification.setPercentage(patchBody.getPercentage());
        }

        qualificationRepository.save(existingQualification);

        return QualificationMapper.toQualificationDTO(existingQualification);
    }

    public QualificationDTO deleteQualificationById(Integer id) throws Exception {
        Qualification existingQualification = qualificationRepository.findById(id)
                .orElseThrow(() -> new Exception("Qualification with ID " + id + " not found."));

        qualificationRepository.deleteById(id);

        return QualificationMapper.toQualificationDTO(existingQualification);
    }

    private void ValidateQualificationDTO(QualificationDTO qualificationDTO) throws Exception {
        if (qualificationDTO.getStandard() == null || qualificationDTO.getStandard().isEmpty()) {
            throw new Exception("Standard is required");
        }
        if (qualificationDTO.getUniversity() == null || qualificationDTO.getUniversity().isEmpty()) {
            throw new Exception("University is required");
        }
        if (qualificationDTO.getPassingYear() == null || qualificationDTO.getPassingYear().isEmpty()) {
            throw new Exception("Passing Year is required");
        }
        if (qualificationDTO.getPercentage() == null) {   // percentage is given in double and double can not use .isEmpty method because only string can use it
            throw new Exception("Percentage is required");
        }
        if (qualificationDTO.getPercentage() < 0 || qualificationDTO.getPercentage() > 100) {
            throw new Exception("Percentage must be between 0 and 100");
        }
    }
}