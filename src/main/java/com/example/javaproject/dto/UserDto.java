package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "First name is mandatory!")
    private String firstName;

    @NotBlank(message = "Last name is mandatory!")
    private String lastName;

    @NotBlank(message = "Email is mandatory!")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory!")
    private String password;

    @NotNull(message = "Address is mandatory!")
    private String address;

    @NotNull(message = "Phone number is mandatory!")
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private java.util.Date birthDate;
}
