package com.example.employeetransfer.controller;

import com.example.employeetransfer.service.EmployeeTransferService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeTransferController {

    @Autowired
    EmployeeTransferService employeeTransferService;

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<?> getEmployee(@PathVariable("employeeId") Long employeeId) throws ParseException {
        return ResponseEntity.ok(employeeTransferService.getEmployee(employeeId));
    }

    @GetMapping("/departments")
    public ResponseEntity<?> getDepartment(
            @RequestParam(value = "member", required = false) Long member,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sortBy", required = false) String sortBy

    ) throws ParseException {
        return ResponseEntity.ok(employeeTransferService.getDepartments(
                member,
                name,
                limit,
                offset,
                sort,
                sortBy)
        );
    }


}
