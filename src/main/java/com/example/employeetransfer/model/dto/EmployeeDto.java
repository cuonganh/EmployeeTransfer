package com.example.employeetransfer.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

    private Long employeeId;

    private String department;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String address;

    private String email;

    private String phoneNumber;

}
