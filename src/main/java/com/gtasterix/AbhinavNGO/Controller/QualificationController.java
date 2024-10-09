package com.gtasterix.AbhinavNGO.Controller;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.Service.QualificationService;
import com.gtasterix.AbhinavNGO.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Qualification")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;
    private QualificationDTO allQualification;

    @PostMapping("/savedQualification")
    public ResponseEntity<Response> addQualification(@RequestBody QualificationDTO qualificationDTO){
        try{
            QualificationDTO savedQualification = qualificationService.addQualification(qualificationDTO);
            Response response = new Response("Qualification added Sucessfully", savedQualification,false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }catch (Exception e){
            Response errorRespone = new Response("Failed to Add Qualification",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll(){
        try{
            List<QualificationDTO> allQualification = qualificationService.getAll();
            Response response = new Response("List of all Qualification ",allQualification,false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Response errorResponse = new Response("Failed to display the list",e.getMessage(),false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateQualification(@RequestParam Integer id, @RequestBody QualificationDTO qualificationDTO) {
        try {
            validateQualification(qualificationDTO);
            QualificationDTO updatedQualification = qualificationService.updateQualificationById(id, qualificationDTO);
            Response response = new Response("Qualification Updated Sucessfully",updatedQualification,false);
            return ResponseEntity.ok(updatedQualification);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Updated Qualification Sucessfully",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Patch a qualification by ID
    @PatchMapping("/patchById")
    public ResponseEntity<?> patchQualification(@RequestParam Integer id, @RequestBody QualificationDTO patchBody) {
        try {
            validateQualification(patchBody);
            QualificationDTO patchedQualification = qualificationService.patchQualificationById(id, patchBody);
            Response response = new Response("Qualification Patched Sucessfully",patchedQualification,false);
            return ResponseEntity.ok(patchedQualification);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Patched Qualification ",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);        }
    }

    // Delete a qualification by ID
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteQualification(@RequestParam Integer id) {
        try {
            QualificationDTO deletedQualification = qualificationService.deleteQualificationById(id);
            Response response = new Response("Qualification Deleted Sucessfully",deletedQualification,false);
            return ResponseEntity.ok(deletedQualification);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Delete Qualification ",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    private void validateQualification(QualificationDTO qualificationDTO) throws Exception {
        if (qualificationDTO.getPercentage() == null) {
            throw new Exception("Percentage is required");
        }
        if (qualificationDTO.getPercentage() < 0 || qualificationDTO.getPercentage() > 100) {
            throw new Exception("Percentage must be between 0 and 100");
        }
    }
}




