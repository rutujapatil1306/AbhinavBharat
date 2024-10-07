package com.gtasterix.AbhinavNGO.Controller;

import com.gtasterix.AbhinavNGO.DTO.UserDTO;
import com.gtasterix.AbhinavNGO.Service.UserService;
import com.gtasterix.AbhinavNGO.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<Response> addUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUser = userService.addUser(userDTO); // Ensure savedBook is properly returned by UserService
            Response response = new Response("User Added Successfully", savedUser, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorresponse = new Response("Failed to add User", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorresponse);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllUser() {
        try {
            List<UserDTO> userDTOList = userService.getAllUser();
            Response response = new Response("List of All Users", userDTOList, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorresponse = new Response("Failed to Display all the list of User", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorresponse);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Response> getUserById(@RequestParam Integer id){
        try{
            UserDTO userDTO = userService.getUserById(id);
            Response response = new Response("User Retrived Sussfully", userDTO,false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Response errorResponse = new Response("Failed to display id",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}



