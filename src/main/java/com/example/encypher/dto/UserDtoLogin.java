package com.example.encypher.dto;

import com.example.encypher.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoLogin
{
    private Long id;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
