package com.gtasterix.AbhinavNGO.DTO;

import com.gtasterix.AbhinavNGO.model.Application;
import lombok.Data;

@Data
public class QualificationDTO {

    private Integer id;
    private String standard;
    private String university;
    private String passingYear;
    private Double percentage;
    private Integer applicationId;

}
