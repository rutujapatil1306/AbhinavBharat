package com.gtasterix.AbhinavNGO.Controller;

import com.gtasterix.AbhinavNGO.DTO.QualificationDTO;
import com.gtasterix.AbhinavNGO.Service.QualificationService;
import com.gtasterix.AbhinavNGO.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Qualification")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    @PostMapping("/save/Qualification")
    public ResponseEntity<Response> addQualification(@RequestBody QualificationDTO qualificationDTO){
        try{
            QualificationDTO savedQualification = qualificationService.addQualification(qualificationDTO);
            Response response = new Response("Qualification added Sucessfully", savedQualification,false);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (Exception e){
            Response errorRespone = new Response("Failed to Add Qualification",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll(){
        try{
            QualificationDTO allQualification = qualificationService.getAll(id);


        }catch (Exception e){

        }

    }

}
