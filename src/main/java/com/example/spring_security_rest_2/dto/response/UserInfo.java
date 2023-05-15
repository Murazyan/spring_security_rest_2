package com.example.spring_security_rest_2.dto.response;

import com.example.spring_security_rest_2.model.Role;
import com.example.spring_security_rest_2.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private int id;
    private String name;
    private String surname;
    private String email;
    private Gender gender;

    private List<Role> roles;
}
