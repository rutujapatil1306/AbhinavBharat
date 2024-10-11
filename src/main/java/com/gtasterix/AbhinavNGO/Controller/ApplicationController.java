package com.gtasterix.AbhinavNGO.Controller;

import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.Service.ApplicationService;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import com.gtasterix.AbhinavNGO.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping({"/api/application", "/api/qualification"})
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @PostMapping("/saveApplication")
    public ResponseEntity<Response> addApplication(@RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO savedApplication = applicationService.addApplication(applicationDTO);
            Response response = new Response("Application Added", savedApplication, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Add Application", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllApplications() {
        try {
            List<ApplicationDTO> applicationDTOList = applicationService.getAllApplication();
            Response response = new Response("List of the Applicants", applicationDTOList, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to display all Applications", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Response> getByIdApplication(@RequestParam Integer id) {
        try {
            ApplicationDTO applicationDTO = applicationService.getByIDApplication(id);
            Response response = new Response("Application retrieved successfully using ID", applicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to retrieve Application using ID", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getByFirstName")
    public ResponseEntity<Response> getByFirstNameApplication(@RequestParam String firstName) {
        try {
            ApplicationDTO applicationDTO = applicationService.getByfirstName(firstName);
            Response response = new Response("Application retrieved successfully using First Name", applicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to retrieve Application using First Name", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/putById")
    public ResponseEntity<Response> updateById(@RequestParam Integer id, @RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO updatedApplicationDTO = applicationService.updateById(id, applicationDTO);
            updatedApplicationDTO.setApplicationId(id);
            Response response = new Response("Applicant updated successfully", updatedApplicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Update Applicant", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PatchMapping("/patchById")
    public ResponseEntity<Response> patchById(@RequestParam Integer id, @RequestBody ApplicationDTO patchBody) {
        try {
            ApplicationDTO patchedApplicationDTO = applicationService.patchById(id, patchBody);
            Response response = new Response("Applicant Patched Successfully", patchedApplicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Patch Applicant", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Response> deleteById(@RequestParam Integer id) {
        try {
            applicationService.deleteById(id); // Assuming this method returns nothing on success
            Response response = new Response("Applicant Deleted Successfully", "Applicant Deleted Successfully", false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed To Delete Applicant", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
