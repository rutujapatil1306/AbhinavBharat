package com.gtasterix.AbhinavNGO.Controller;

import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.Service.AdminService;
import com.gtasterix.AbhinavNGO.Service.ApplicationService;
import com.gtasterix.AbhinavNGO.mapper.AdminMapper;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import com.gtasterix.AbhinavNGO.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AdminService adminService;

        @GetMapping("/getAll")
        public ResponseEntity<Response> getAllApplications() {
            try {
                List<ApplicationDTO> applicationDTOList = adminService.getAllApplication();
                Response response = new Response("List of the Applicants", applicationDTOList, false);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception e) {
                Response errorResponse = new Response("Failed to display all Applications", e.getMessage(), true);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        }
    }

