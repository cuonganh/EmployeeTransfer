package com.example.employeetransfer.model;

import lombok.Data;

@Data
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
