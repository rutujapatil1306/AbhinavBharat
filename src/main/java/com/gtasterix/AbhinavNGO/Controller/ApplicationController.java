package com.gtasterix.AbhinavNGO.Controller;


import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.Service.ApplicationService;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import com.gtasterix.AbhinavNGO.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/application")
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
            Response errorResponse = new Response("Failed to display all Application", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Response> getByIDApplication(@RequestParam Integer id) {
        try {
            ApplicationDTO applicationDTO = applicationService.getByIDApplication(id);
            Response response = new Response("Application retrived  Succesfully using ID", applicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to retrived Application using ID", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getByfristName")
    public ResponseEntity<Response> getByNameApplicatioon(@RequestParam String firstName) {
        try {
            ApplicationDTO applicationDTO = applicationService.getByfirstName(firstName);
            Response response = new Response("Application retrived  Succesfully using First Name", applicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to retrived Application using First Name", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/putById")
    public ResponseEntity<Response> updateById(@RequestParam Integer id, @RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO updateapplicationDTO = applicationService.updateById(id,applicationDTO);
            updateapplicationDTO.setId(id);
            Response response = new Response("Applicant updated Sucessfully", applicationDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Update Applicant", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PatchMapping("/patchById")
    public ResponseEntity<Response> patchById(@RequestParam Integer id, @RequestBody ApplicationDTO patchBody){
        try{
            ApplicationDTO patchapplicationDTO = applicationService.patchById(id,patchBody);
            Response response = new Response("Applicant Patched Sucessfully", patchapplicationDTO,false);
            return  ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Response errorResponse = new Response("Failed to Patch Applicant",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Response> deleteById(@RequestParam Integer id) {
        try{
            ApplicationDTO applicationDTO = applicationService.deleteById(id);
            Response response = new Response("Applicant Deleted Sucessfully", "Applicant Deleted Sucessfully",false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
            }catch (Exception e){
            Response errorResponse = new Response("Failed To Delete Applicant",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}





