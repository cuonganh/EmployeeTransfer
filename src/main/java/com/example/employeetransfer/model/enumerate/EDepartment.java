package com.example.employeetransfer.model.enumerate;

public enum EDepartment {
    DEPARTMENT_ID("departmentId"),
    DEPARTMENT_NAME("name"),
    DEPARTMENT_MEMBERS("member"),
    DEPARTMENT_DESCRIPTION("description"),
    DEPARTMENT_LEADER("leader")
    ;

    private String value;

    EDepartment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
