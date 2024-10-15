package com.gtasterix.AbhinavNGO.Service;

import com.gtasterix.AbhinavNGO.DTO.AdminDTO;
import com.gtasterix.AbhinavNGO.DTO.ApplicationDTO;
import com.gtasterix.AbhinavNGO.mapper.ApplicationMapper;
import com.gtasterix.AbhinavNGO.model.Admin;
import com.gtasterix.AbhinavNGO.model.Application;
import com.gtasterix.AbhinavNGO.repository.AdminRepository;
import com.gtasterix.AbhinavNGO.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

//    @Autowired
//    private AdminRepository adminRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<ApplicationDTO> getAllApplication() {

            List<Application> applicationList = applicationRepository.findAll();
            List<ApplicationDTO> applicationDTOList = new ArrayList<>();
            for (Application application : applicationList) {
                applicationDTOList.add(ApplicationMapper.toApplicationDTO(application));
            }
            return applicationDTOList;
        }
    }

