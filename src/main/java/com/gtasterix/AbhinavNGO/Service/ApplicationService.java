package com.gtasterix.AbhinavNGO.Service;


import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.mapper.ApplicationMapper;
import com.gtasterix.AbhinavNGO.mapper.QualificationMapper;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.Qualification;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

//    @Autowired
//    private static ModelMapper modelMapper;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String MOBILE_REGEX = "^[6-9][0-9]{9}$";


    private static boolean validateMobileNumber(String first_MobileNo) {
//        System.out.println("Validating mobile number: " + first_MobileNo);
        if (first_MobileNo == null || !Pattern.matches(MOBILE_REGEX, first_MobileNo)) {
            throw new IllegalArgumentException("Invalid mobile number fort");
        }
        return true;
    }

    private static boolean validateMobileNumber1(String second_MobileNo) {
        if (second_MobileNo == null || !Pattern.matches(MOBILE_REGEX, second_MobileNo)) {
            throw new IllegalArgumentException("Invalid mobile number format");
        }
        return true;
    }


    private static boolean validateEmail(String mailId) {
        if (mailId == null || !Pattern.matches(EMAIL_REGEX, mailId)) {
            throw new IllegalArgumentException(("invalid email format"));
        }
        return true;
    }

    // ApplicationService.java
    public ApplicationDTO addApplication(ApplicationDTO applicationDTO) throws Exception {
        if (validateMobileNumber(applicationDTO.getMobileNo()) && validateEmail(applicationDTO.getMailID())) {
            Application application = ApplicationMapper.toApplicationEntity(applicationDTO);
            Application savedApplication = applicationRepository.save(application);

            // Map the qualifications
            List<QualificationDTO> qualificationDTOs = applicationDTO.getQualifications();
            if (qualificationDTOs != null) {
                for (QualificationDTO qualificationDTO : qualificationDTOs) {
                    Qualification qualification = QualificationMapper.toQualification(qualificationDTO);
                    qualification.setApplication(savedApplication);
                    savedApplication.getQualifications().add(qualification);
                }
                applicationRepository.save(savedApplication);
            }

            ApplicationDTO savedApplicationDTO = ApplicationMapper.toApplicationDTO(savedApplication);
            return savedApplicationDTO;
        } else {
            throw new Exception("User   not created due to invalid details");
        }
    }

    public List<ApplicationDTO> getAllApplication() {
        List<Application> applicationList = applicationRepository.findAll();
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        for (Application application : applicationList) {
            applicationDTOList.add(ApplicationMapper.toApplicationDTO(application));
        }
        return applicationDTOList;
    }

    public ApplicationDTO getByIDApplication(Integer id) throws Exception {
        Application applicationId = applicationRepository.findById(id).orElse(null);
        if (applicationId != null) {
            return ApplicationMapper.toApplicationDTO(applicationId);
        } else {
            throw new Exception("Could not retrive the Application using ID ");
        }
    }


    public ApplicationDTO getByfirstName(String firstName) throws Exception {
        Application applicationName = applicationRepository.findByFirstName(firstName)
                .orElse(null);
        if (applicationName != null) {
            return ApplicationMapper.toApplicationDTO(applicationName);
        } else {
            throw new Exception("Could noy get the Applicant by First Name ");
        }
    }


    public ApplicationDTO updateById(Integer id, ApplicationDTO updatedApplicationDTO) throws Exception {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID Not Found"));
        if (existingApplication != null) {

            ValidateApplicationDTO(updatedApplicationDTO);

            // existingApplication.setId(updatedApplicationDTO.getId());
            existingApplication.setFirstName(updatedApplicationDTO.getFirstName());
            existingApplication.setLastName(updatedApplicationDTO.getLastName());
            existingApplication.setMailID(updatedApplicationDTO.getMailID());
            //existingApplication.setEducation(updatedApplicationDTO.getEducation());
            existingApplication.setMobileNo(updatedApplicationDTO.getMobileNo());
            existingApplication.setAlternateNo(updatedApplicationDTO.getAlternateNo());
            existingApplication.setFatherName(updatedApplicationDTO.getFatherName());
            existingApplication.setMotherName(updatedApplicationDTO.getMotherName());
            existingApplication.setDob(updatedApplicationDTO.getDob());
            existingApplication.setAddress(updatedApplicationDTO.getAddress());
            existingApplication.setState(updatedApplicationDTO.getState());
            existingApplication.setPinCode(updatedApplicationDTO.getPinCode());
            existingApplication.setCategory(updatedApplicationDTO.getCategory());
            existingApplication.setMaritalStatus(updatedApplicationDTO.getMaritalStatus());
            existingApplication.setReligion(updatedApplicationDTO.getReligion());
            existingApplication.setCitizenOfIndia(updatedApplicationDTO.getCitizenOfIndia());
            existingApplication.setAnyDisability(updatedApplicationDTO.getAnyDisability());

            applicationRepository.save(existingApplication);

            return ApplicationMapper.toApplicationDTO(existingApplication);
        } else {
            throw new Exception("Application with ID " + id + " not found.");
        }
    }


    public ApplicationDTO patchById(Integer id, ApplicationDTO patchBody) throws Exception {
        Application existingApplication = applicationRepository.findById(id).orElse(null);

        if (existingApplication != null) {

            if (patchBody.getFirstName() != null) {
                existingApplication.setFirstName(patchBody.getFirstName());
            }
            if (patchBody.getLastName() != null) {
                existingApplication.setLastName(patchBody.getLastName());
            }
            if (patchBody.getMailID() != null) {
                validateEmail(patchBody.getMailID());
                existingApplication.setMailID(patchBody.getMailID());
            }
//            if (patchBody.getEducation() != null) {
//                existingApplication.setEducation(patchBody.getEducation());
//            }
            if (patchBody.getMobileNo() != null) {
                validateMobileNumber(patchBody.getMobileNo());
                existingApplication.setMobileNo(patchBody.getMobileNo());
            }
            if (patchBody.getAlternateNo() != null) {
                existingApplication.setAlternateNo(patchBody.getAlternateNo());
            }
            if (patchBody.getFatherName() != null) {
                existingApplication.setFatherName(patchBody.getFatherName());
            }
            if (patchBody.getMotherName() != null) {
                existingApplication.setMotherName(patchBody.getMotherName());
            }
            if (patchBody.getDob() != null) {
                existingApplication.setDob(patchBody.getDob());
            }
            if (patchBody.getAddress() != null) {
                existingApplication.setAddress(patchBody.getAddress());
            }
            if (patchBody.getState() != null) {
                existingApplication.setState(patchBody.getState());
            }
            if (patchBody.getPinCode() != null) {
                existingApplication.setPinCode(patchBody.getPinCode());
            }
            if (patchBody.getCategory() != null) {
                existingApplication.setCategory(patchBody.getCategory());
            }
            if (patchBody.getMaritalStatus() != null) {
                existingApplication.setMaritalStatus(patchBody.getMaritalStatus());
            }
            if (patchBody.getReligion() != null) {
                existingApplication.setReligion(patchBody.getReligion());
            }
            if (patchBody.getCitizenOfIndia() != null) {
                existingApplication.setCitizenOfIndia(patchBody.getCitizenOfIndia());
            }
            if (patchBody.getAnyDisability() != null) {
                existingApplication.setAnyDisability(patchBody.getAnyDisability());
            }
            applicationRepository.save(existingApplication);
            return ApplicationMapper.toApplicationDTO(existingApplication);
        } else {
            throw new Exception ("Application with ID " + id + " not found.");
        }
    }

    public ApplicationDTO deleteById(Integer id) throws Exception {
        Application existingApplication = applicationRepository.findById(id).orElse(null);
        if (existingApplication != null) {
            applicationRepository.deleteById(id);
            return ApplicationMapper.toApplicationDTO(existingApplication);
        } else {
            throw new Exception("Application with ID " + id + " not found.");
        }
    }


    private void ValidateApplicationDTO(ApplicationDTO applicationDTO) throws Exception {
        if ((applicationDTO.getFirstName() == null) || applicationDTO.getFirstName().isEmpty()) {
            throw new Exception("First Name is required");
        }
        if ((applicationDTO.getLastName() == null) || applicationDTO.getLastName().isEmpty()) {
            throw new Exception("Last Name is required");
        }
        if ((applicationDTO.getMailID() == null) || applicationDTO.getMailID().isEmpty()) {
            throw new Exception("Mail ID is required");
        }
//        if ((applicationDTO.getEducation() == null) || applicationDTO.getEducation().isEmpty()) {
//            throw new Exception("Education is required");
//        }
        if ((applicationDTO.getMobileNo() == null) || applicationDTO.getMobileNo().isEmpty()) {
            throw new Exception("Mobile Number is required");
        }
//        if ((applicationDTO.getAlternateNo() == null) || applicationDTO.getAlternateNo().isEmpty()) {
//            throw new Exception("Alternate Mobile Number is required");
//        }
        if ((applicationDTO.getFatherName() == null) || applicationDTO.getFatherName().isEmpty()) {
            throw new Exception("Father's Name is required");
        }
        if ((applicationDTO.getMotherName() == null) || applicationDTO.getMotherName().isEmpty()) {
            throw new Exception("Mother's Name is required");
        }
        if ((applicationDTO.getDob() == null) || applicationDTO.getDob().isEmpty()) {
            throw new Exception("Date of Birth is required");
        }
        if ((applicationDTO.getAddress() == null) || applicationDTO.getAddress().isEmpty()) {
            throw new Exception("Address is required");
        }
        if ((applicationDTO.getState() == null) || applicationDTO.getState().isEmpty()) {
            throw new Exception("State is required");
        }
        if ((applicationDTO.getPinCode() == null) || applicationDTO.getPinCode().isEmpty()) {
            throw new Exception("Pin Code is required");
        }
        if ((applicationDTO.getCategory() == null) || applicationDTO.getCategory().isEmpty()) {
            throw new Exception("Category is required");
        }
        if ((applicationDTO.getMaritalStatus() == null) || applicationDTO.getMaritalStatus().isEmpty()) {
            throw new Exception("Marital Status is required");
        }
        if ((applicationDTO.getReligion() == null) || applicationDTO.getReligion().isEmpty()) {
            throw new Exception("Religion is required");
        }
        if ((applicationDTO.getCitizenOfIndia() == null) || applicationDTO.getCitizenOfIndia().isEmpty()) {
            throw new Exception("Citizen of India is required");
        }
        if ((applicationDTO.getAnyDisability() == null) || applicationDTO.getAnyDisability().isEmpty()) {
            throw new Exception("Disability information is required");
        }
    }


}



