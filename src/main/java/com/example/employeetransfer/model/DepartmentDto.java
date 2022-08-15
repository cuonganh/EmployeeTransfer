package com.example.employeetransfer.model;

import lombok.Data;

@Data
public class DepartmentDto {

    Long departmentId;

    String name;

    Long member;

    String description;

    String leader;

}
