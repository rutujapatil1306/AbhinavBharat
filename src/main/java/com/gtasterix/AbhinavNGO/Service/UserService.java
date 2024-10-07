package com.gtasterix.AbhinavNGO.Service;

import com.gtasterix.AbhinavNGO.DTO.UserDTO;
import com.gtasterix.AbhinavNGO.mapper.UserMapper;
import com.gtasterix.AbhinavNGO.model.User;
import com.gtasterix.AbhinavNGO.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String MOBILE_REGEX = "^[6-9][0-9]{9}$";

    private boolean validateMobileNumber(String mobileno) {
        if (mobileno == null || !Pattern.matches(MOBILE_REGEX, mobileno)) {
            throw new IllegalArgumentException("Invalid mobile number format");
        }
        return true;
    }

    private boolean validateEmail(String email){
        if(email == null || !Pattern.matches(EMAIL_REGEX, email)){
            throw new IllegalArgumentException(("invalid email format"));
        }
        return true;
    }

    public UserDTO addUser(UserDTO userDTO) throws Exception {
        if(validateMobileNumber(userDTO.getMobileno()) && validateEmail(userDTO.getEmail()) ){
            User user=UserMapper.toEntity(userDTO);
            userRepository.save(user);
            return UserMapper.toDTO(user);
        } else {
            throw new Exception("User not created");
        }
    }

    public List<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(UserMapper.toDTO(user));
        }
        return userDTOList;
    }

    public UserDTO getUserById(Integer id) {
        User userid = userRepository.findById(id).orElse(null);
        if (userid != null) {
            return UserMapper.toDTO(userid);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
