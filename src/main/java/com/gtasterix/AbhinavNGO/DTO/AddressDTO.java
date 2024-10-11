package com.gtasterix.AbhinavNGO.DTO;

import com.gtasterix.AbhinavNGO.model.Application;
import lombok.Data;

import java.util.List;

@Data
public class AddressDTO {

    private Integer addressId;
    private String streetAddress;
    private String taluka;
    private String district;
    private String state;
    private String pincode;
    private Integer applicationId;


}
