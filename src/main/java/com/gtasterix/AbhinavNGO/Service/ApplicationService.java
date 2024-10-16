package com.gtasterix.AbhinavNGO.Service;


import com.gtasterix.AbhinavNGO.DTO.AddressDTO;
import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.mapper.AddressMapper;
import com.gtasterix.AbhinavNGO.mapper.ApplicationMapper;
import com.gtasterix.AbhinavNGO.mapper.QualificationMapper;
import com.gtasterix.AbhinavNGO.model.Address;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.model.Qualification;
import com.gtasterix.AbhinavNGO.repository.AddressRepository;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import com.gtasterix.AbhinavNGO.repository.QualificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private QualificationMapper qualificationMapper;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String MOBILE_REGEX = "^[6-9][0-9]{9}$";
    private static final String AADHAARCARD_REGEX = "^[2-9]{1}[0-9]{11}$";
    private static final String PANCARD_REGEX = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";


    private static boolean validateMobileNumber(String first_MobileNo) {
        if (first_MobileNo == null || !Pattern.matches(MOBILE_REGEX, first_MobileNo)) {
            throw new IllegalArgumentException("Invalid mobile number fort");
        }
        return true;
    }

    private static boolean validateMobileNumber1(String second_MobileNo) {
        if (!Pattern.matches(MOBILE_REGEX, second_MobileNo)) {
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

    private Boolean validatePan(String pan) {
        if (pan == null || !Pattern.matches(PANCARD_REGEX, pan)) {
            throw new IllegalArgumentException("Invalid PAN card format");
        }
        return true;
    }

    private Boolean validateAadhaar(String aadhaar) {
        if (aadhaar == null || !Pattern.matches(AADHAARCARD_REGEX, aadhaar)) {
            throw new IllegalArgumentException("Invalid Aadhaar number format");
        }
        return true;
    }

    private Boolean validateDob(String dob) {
        if (dob == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date of birth format or logical date.");
        }
        return true;
    }

    @Transactional
    public ApplicationDTO addApplication(ApplicationDTO applicationDTO) throws Exception {

        applicationDTO.setPanCardNo(applicationDTO.getPanCardNo().toUpperCase()); // Convert PAN to uppercase

        if (applicationRepository.findByMailID(applicationDTO.getMailID()).isPresent()) {
            throw new IllegalArgumentException("Email ID is already in use.");
        }
        if (applicationRepository.findByAdharCard(applicationDTO.getAdharCard()).isPresent()) {
            throw new IllegalArgumentException("AadhaarCard no. is already in use.");
        }
        if (applicationRepository.findByPanCardNo(applicationDTO.getPanCardNo()).isPresent()) {
            throw new IllegalArgumentException("PanCard no. is already in use.");
        }

        if (validateEmail(applicationDTO.getMailID()) && validateMobileNumber(applicationDTO.getMobileNo())
                && validateAadhaar(applicationDTO.getAdharCard()) && validatePan(applicationDTO.getPanCardNo())
                && validateDob(applicationDTO.getDob())) {
            if (applicationDTO.getAlternateNo() != null) {
                validateMobileNumber(applicationDTO.getAlternateNo());
            }

            Application application = ApplicationMapper.toApplicationEntity(applicationDTO);
            Application savedApplication = applicationRepository.save(application);

            List<QualificationDTO> qualificationDTOS = applicationDTO.getQualifications();
            if (qualificationDTOS != null) {
                for (QualificationDTO qualificationDTO : qualificationDTOS) {
                    Qualification qualification = QualificationMapper.toQualification(qualificationDTO);

                    Optional<Qualification> existingQualification = qualificationRepository.findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(
                            savedApplication, qualification.getStandard(), qualification.getUniversity(),
                            qualification.getPassingYear(), qualification.getPercentage());

                    // Check if the qualification already exists
                    if (existingQualification.isEmpty()) {
                        qualification.setApplication(savedApplication);
                        qualificationRepository.save(qualification);
                    } else {
                        new Exception("Duplicate qualification found, skipping save: " + qualificationDTO);
                    }
                }
            } else {
                throw new Exception("Qualification is Mandatory");
            }

            List<AddressDTO> addressDTOs = applicationDTO.getAddresses();
            if (addressDTOs != null) {
                for (AddressDTO addressDTO : addressDTOs) {
                    Address address = AddressMapper.toAddress(addressDTO);

                    Optional<Address> existingAddress = addressRepository.findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
                            savedApplication, address.getStreetAddress(), address.getDistrict(), address.getPincode(), address.getState(), address.getTaluka());

                    if (existingAddress.isEmpty()) {
                        address.setApplication(savedApplication);
                        addressRepository.save(address);
                    } else {
                        // Optionally handle duplicate address scenario (e.g., log or ignore)
                        new Exception("Duplicate address found, skipping save: " + addressDTO);
                    }
                }
            } else {
                throw new Exception("Address is Mandatory");
            }
            applicationRepository.save(savedApplication);
            return ApplicationMapper.toApplicationDTO(savedApplication);
        } else {
            throw new Exception("Applicant not created due to invalid details");
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

    public List<Application> getAllApplicationsSubmittedToday() {
        return applicationRepository.findBySubmissionDate(LocalDate.now());
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
        // Retrieve the existing application by ID
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID Not Found"));

        // Validate the incoming DTO
        validateApplicationDTO(updatedApplicationDTO);

        // Update basic application fields
        existingApplication.setApplyFor(updatedApplicationDTO.getApplyFor());
        existingApplication.setFirstName(updatedApplicationDTO.getFirstName());
        existingApplication.setMiddleName(updatedApplicationDTO.getMiddleName());
        existingApplication.setLastName(updatedApplicationDTO.getLastName());
        existingApplication.setGender(updatedApplicationDTO.getGender());
        existingApplication.setMailID(updatedApplicationDTO.getMailID());
        existingApplication.setMobileNo(updatedApplicationDTO.getMobileNo());
        existingApplication.setAlternateNo(updatedApplicationDTO.getAlternateNo());
        existingApplication.setDob(updatedApplicationDTO.getDob());
        existingApplication.setMaritalStatus(updatedApplicationDTO.getMaritalStatus());
        existingApplication.setAdharCard(updatedApplicationDTO.getAdharCard());
        existingApplication.setPanCardNo(updatedApplicationDTO.getPanCardNo());
        existingApplication.setOrganizationName(updatedApplicationDTO.getOrganizationName());
        existingApplication.setWorkingLocation(updatedApplicationDTO.getWorkingLocation());
        existingApplication.setPosition(updatedApplicationDTO.getPosition());
        existingApplication.setTypeOfEngagement(updatedApplicationDTO.getTypeOfEngagement());
        existingApplication.setExperienceYear(updatedApplicationDTO.getExperienceYear());
        existingApplication.setExperienceMonths(updatedApplicationDTO.getExperienceMonths());
        existingApplication.setExperienceDays(updatedApplicationDTO.getExperienceDays());

        // Update Qualifications
        List<Qualification> existingQualifications = existingApplication.getQualifications();
        List<QualificationDTO> updatedQualifications = updatedApplicationDTO.getQualifications();
        for (QualificationDTO updatedQualification : updatedQualifications) {
            for (Qualification existingQualification : existingQualifications) {
                if (existingQualification.getQualificationId() != null &&
                        existingQualification.getQualificationId().equals(updatedQualification.getQualificationId())) {

                    if (updatedQualification.getStandard() != null) {
                        existingQualification.setStandard(updatedQualification.getStandard());
                    }
                    if (updatedQualification.getUniversity() != null) {
                        existingQualification.setUniversity(updatedQualification.getUniversity());
                    }
                    if (updatedQualification.getPassingYear() != null) {
                        existingQualification.setPassingYear(updatedQualification.getPassingYear());
                    }
                    if (updatedQualification.getPercentage() != null) {
                        existingQualification.setPercentage(updatedQualification.getPercentage());
                    }
                    break;
                }
            }
        }

        List<Address> existingAddresses = existingApplication.getAddresses();
        List<AddressDTO> updatedAddresses = updatedApplicationDTO.getAddresses();

        for (AddressDTO updatedAddress : updatedAddresses) {
            boolean found = false; //  check if a matching address was found
            for (Address existingAddress : existingAddresses) {
                if (existingAddress.getAddressId() != null &&
                        existingAddress.getAddressId().equals(updatedAddress.getAddressId())) {
                    found = true; // Matching address found

                    if (updatedAddress.getStreetAddress() != null) {
                        existingAddress.setStreetAddress(updatedAddress.getStreetAddress());
                    }
                    if (updatedAddress.getTaluka() != null) {
                        existingAddress.setTaluka(updatedAddress.getTaluka());
                    }
                    if (updatedAddress.getDistrict() != null) {
                        existingAddress.setDistrict(updatedAddress.getDistrict());
                    }
                    if (updatedAddress.getState() != null) {
                        existingAddress.setState(updatedAddress.getState());
                    }
                    if (updatedAddress.getPincode() != null) {
                        existingAddress.setPincode(updatedAddress.getPincode());
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("No matching address found for ID: " + updatedAddress.getAddressId());
            }
        }
        Application savedApplication = applicationRepository.save(existingApplication);
        return ApplicationMapper.toApplicationDTO(savedApplication);
    }

    public ApplicationDTO patchById(Integer applicationId, ApplicationDTO patchBody) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application with ID " + applicationId + " not found"));

        if (patchBody.getApplyFor() != null && !patchBody.getApplyFor().isEmpty()) {
            existingApplication.setApplyFor(patchBody.getApplyFor());
        }
        if (patchBody.getFirstName() != null && !patchBody.getFirstName().isEmpty()) {
            existingApplication.setFirstName(patchBody.getFirstName());
        }
        if (patchBody.getMiddleName() != null && !patchBody.getMiddleName().isEmpty()) {
            existingApplication.setMiddleName(patchBody.getMiddleName());
        }
        if (patchBody.getLastName() != null && !patchBody.getLastName().isEmpty()) {
            existingApplication.setLastName(patchBody.getLastName());
        }
        if (patchBody.getGender() != null && !patchBody.getGender().isEmpty()) {
            existingApplication.setGender(patchBody.getGender());
        }
        if (patchBody.getMailID() != null && !patchBody.getMailID().isEmpty()) {
            validateEmail(patchBody.getMailID());
            existingApplication.setMailID(patchBody.getMailID());
        }
        if (patchBody.getMobileNo() != null && !patchBody.getMobileNo().isEmpty()) {
            validateMobileNumber(patchBody.getMobileNo());
            existingApplication.setMobileNo(patchBody.getMobileNo());
        }
        if (patchBody.getAlternateNo() != null && !patchBody.getAlternateNo().isEmpty()) {
            validateMobileNumber(patchBody.getAlternateNo());
            existingApplication.setAlternateNo(patchBody.getAlternateNo());
        }
        if (patchBody.getDob() != null && !patchBody.getDob().isEmpty()) {
            validateDob(patchBody.getDob());
            existingApplication.setDob(patchBody.getDob());
        }
        if (patchBody.getMaritalStatus() != null && !patchBody.getMaritalStatus().isEmpty()) {
            existingApplication.setMaritalStatus(patchBody.getMaritalStatus());
        }
        if (patchBody.getAdharCard() != null && !patchBody.getAdharCard().isEmpty()) {
            validateAadhaar(patchBody.getAdharCard());
            existingApplication.setAdharCard(patchBody.getAdharCard());
        }
        if (patchBody.getPanCardNo() != null && !patchBody.getPanCardNo().isEmpty()) {
            validatePan(patchBody.getPanCardNo());
            existingApplication.setPanCardNo(patchBody.getPanCardNo().toUpperCase());
        }
        if (patchBody.getOrganizationName() != null && !patchBody.getOrganizationName().isEmpty()) {
            existingApplication.setOrganizationName(patchBody.getOrganizationName());
        }
        if (patchBody.getWorkingLocation() != null && !patchBody.getWorkingLocation().isEmpty()) {
            existingApplication.setWorkingLocation(patchBody.getWorkingLocation());
        }
        if (patchBody.getPosition() != null && !patchBody.getPosition().isEmpty()) {
            existingApplication.setPosition(patchBody.getPosition());
        }
        if (patchBody.getTypeOfEngagement() != null && !patchBody.getTypeOfEngagement().isEmpty()) {
            existingApplication.setTypeOfEngagement(patchBody.getTypeOfEngagement());
        }
        if (patchBody.getExperienceYear() != null && !patchBody.getExperienceYear().isEmpty()) {
            existingApplication.setExperienceYear(patchBody.getExperienceYear());
        }
        if (patchBody.getExperienceMonths() != null && !patchBody.getExperienceMonths().isEmpty()) {
            existingApplication.setExperienceMonths(patchBody.getExperienceMonths());
        }
        if (patchBody.getExperienceDays() != null && !patchBody.getExperienceDays().isEmpty()) {
            existingApplication.setExperienceDays(patchBody.getExperienceDays());
        }

        // Patch qualifications
        if (patchBody.getQualifications() != null) {
            existingApplication.getQualifications().clear();
            for (QualificationDTO qualificationDTO : patchBody.getQualifications()) {
                Qualification qualification = new Qualification();
                qualification.setStandard(qualificationDTO.getStandard());
                qualification.setUniversity(qualificationDTO.getUniversity());
                qualification.setPassingYear(qualificationDTO.getPassingYear());
                qualification.setPercentage(qualificationDTO.getPercentage());
                qualification.setApplication(existingApplication); // link the qualification to the application
                existingApplication.getQualifications().add(qualification);
            }
        }

        if (patchBody.getAddresses() != null) {
            existingApplication.getAddresses().clear();
            for (AddressDTO addressDTO : patchBody.getAddresses()) {
                Address address = new Address();
                address.setStreetAddress(addressDTO.getStreetAddress());
                address.setPincode(addressDTO.getPincode());
                address.setState(addressDTO.getState());
                address.setDistrict(addressDTO.getDistrict());
                address.setTaluka(addressDTO.getTaluka());
                address.setApplication(existingApplication); // link the address to the application
                existingApplication.getAddresses().add(address);
            }
        }

        applicationRepository.save(existingApplication);

        return ApplicationMapper.toApplicationDTO(existingApplication);
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

    private void validateApplicationDTO(ApplicationDTO applicationDTO) throws Exception {
        if ((applicationDTO.getFirstName() == null) || applicationDTO.getFirstName().isEmpty()) {
            throw new Exception("First Name is required");
        }
        if ((applicationDTO.getMiddleName() == null) || applicationDTO.getMiddleName().isEmpty()) {
            throw new Exception("Middle Name is required");
        }
        if ((applicationDTO.getLastName() == null) || applicationDTO.getLastName().isEmpty()) {
            throw new Exception("Last Name is required");
        }
        if ((applicationDTO.getGender() == null) || applicationDTO.getGender().isEmpty()) {
            throw new Exception("Gender is required");
        }
        if ((applicationDTO.getMailID() == null) || !Pattern.matches(EMAIL_REGEX, applicationDTO.getMailID())) {
            throw new Exception("Invalid Email ID");
        }
        if ((applicationDTO.getMobileNo() == null) || !Pattern.matches(MOBILE_REGEX, applicationDTO.getMobileNo())) {
            throw new Exception("Invalid Mobile Number");
        }
        if ((applicationDTO.getDob() == null) || applicationDTO.getDob().isEmpty()) {
            throw new Exception("Date of Birth is required");
        }
        if ((applicationDTO.getMaritalStatus() == null) || applicationDTO.getMaritalStatus().isEmpty()) {
            throw new Exception("Marital Status is required");
        }
        if ((applicationDTO.getAdharCard() == null) || !Pattern.matches(AADHAARCARD_REGEX, applicationDTO.getAdharCard())) {
            throw new Exception("Aadhar Card is required");
        }
        if ((applicationDTO.getPanCardNo() == null) || !Pattern.matches(PANCARD_REGEX, applicationDTO.getPanCardNo())) {
            throw new Exception("PAN Card Number is required");
        }
        if ((applicationDTO.getExperienceYear() == null || applicationDTO.getExperienceYear().isEmpty())) {
            throw new Exception("Experience Year must be a valid number");
        }
        if ((applicationDTO.getExperienceMonths() == null || applicationDTO.getExperienceMonths().isEmpty())) {
            throw new Exception("Experience Months must be a valid number");
        }
        if ((applicationDTO.getExperienceDays() == null || applicationDTO.getExperienceDays().isEmpty())) {
            throw new Exception("Experience Days must be a valid number");
        }

        // Validate and patch qualifications
        if (applicationDTO.getQualifications() != null && !applicationDTO.getQualifications().isEmpty()) {
            for (QualificationDTO qualificationDTO : applicationDTO.getQualifications()) {
                if ((qualificationDTO.getStandard() == null) || qualificationDTO.getStandard().isEmpty()) {
                    throw new Exception("Standard is required");
                }
                if ((qualificationDTO.getUniversity() == null) || qualificationDTO.getUniversity().isEmpty()) {
                    throw new Exception("University is required");
                }
                if ((qualificationDTO.getPassingYear() == null) || qualificationDTO.getPassingYear().isEmpty()) {
                    throw new Exception("Passing Year is required");
                }
                if ((qualificationDTO.getPercentage() == null) || qualificationDTO.getPercentage() < 0 || qualificationDTO.getPercentage() > 100) {
                    throw new Exception("Percentage must be a valid number between 0 and 100");
                }
            }
        }

        // Validate and patch addresses
        if (applicationDTO.getAddresses() != null && !applicationDTO.getAddresses().isEmpty()) {
            for (AddressDTO addressDTO : applicationDTO.getAddresses()) {
                if ((addressDTO.getStreetAddress() == null) || addressDTO.getStreetAddress().isEmpty()) {
                    throw new Exception("Street Address is required");
                }
                if ((addressDTO.getTaluka() == null) || addressDTO.getTaluka().isEmpty()) {
                    throw new Exception("Taluka is required");
                }
                if ((addressDTO.getDistrict() == null) || addressDTO.getDistrict().isEmpty()) {
                    throw new Exception("District is required");
                }
                if ((addressDTO.getState() == null) || addressDTO.getState().isEmpty()) {
                    throw new Exception("State is required");
                }
                if ((addressDTO.getPincode() == null) || addressDTO.getPincode().isEmpty()) {
                    throw new Exception("Pincode is required");
                }
            }
        }

    }
}





