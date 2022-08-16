package com.example.employeetransfer.service;

import com.example.employeetransfer.model.dto.DepartmentDto;
import com.example.employeetransfer.model.dto.EmployeeDto;
import com.example.employeetransfer.model.enumerate.EDepartment;
import com.example.employeetransfer.model.enumerate.EEmployee;
import com.example.employeetransfer.model.response.TransferResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeTransferService {

    @Value("${employeeURLs}")
    private String employeeURLs;

    @Value("${departmentURLs}")
    private String departmentsURLs;

    public TransferResponse<EmployeeDto> getEmployee(Long employeeId) throws ParseException {

        String employeeURL = employeeURLs + employeeId;
        List<Map<String, String>> result = getResultRequest(employeeURL);

        EmployeeDto employeeDto = new EmployeeDto();
        if(result.size() > 0){
            employeeDto.setEmployeeId(Long.valueOf(result.get(0).get(EEmployee.EMPLOYEE_ID.getValue())));
            employeeDto.setDepartment(result.get(0).get(EEmployee.DEPARTMENT.getValue()));
            employeeDto.setFirstName(result.get(0).get(EEmployee.FIRST_NAME.getValue()));
            employeeDto.setLastName(result.get(0).get(EEmployee.LAST_NAME.getValue()));
            employeeDto.setDateOfBirth(result.get(0).get(EEmployee.DATE_OF_BIRTH.getValue()));
            employeeDto.setAddress(result.get(0).get(EEmployee.ADDRESS.getValue()));
            employeeDto.setEmail(result.get(0).get(EEmployee.EMAIL.getValue()));
            employeeDto.setPhoneNumber(result.get(0).get(EEmployee.PHONE_NUMBER.getValue()));
        }
        return new TransferResponse<>(200, "Found employee", employeeDto);
    }

    public TransferResponse<List<DepartmentDto>> getDepartments(
            Long member,
            String name,
            Integer limit,
            Integer offset,
            String sort,
            String sortBy
    ) throws ParseException {

        StringBuilder departmentUrl = new StringBuilder(departmentsURLs);

        if (member != null) {
            departmentUrl.append("&member=").append(member);
        }
        if (name != null) {
            departmentUrl.append("&name=").append(name);
        }

        departmentUrl.append(urlCondition(sort, sortBy, limit, offset));

        List<Map<String, String>> result = getResultRequest(departmentUrl.toString());

        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        if(result.size() > 0) {
            for (Map<String, String> stringStringMap : result) {
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setDepartmentId(Long.valueOf(stringStringMap.get(EDepartment.DEPARTMENT_ID.getValue())));
                departmentDto.setName(stringStringMap.get(EDepartment.DEPARTMENT_NAME.getValue()));
                departmentDto.setMember(Long.valueOf(stringStringMap.get(EDepartment.DEPARTMENT_MEMBERS.getValue())));
                departmentDto.setDescription(stringStringMap.get(EDepartment.DEPARTMENT_DESCRIPTION.getValue()));
                departmentDto.setLeader(stringStringMap.get(EDepartment.DEPARTMENT_LEADER.getValue()));
                departmentDtoList.add(departmentDto);
            }
        }
        return new TransferResponse<>(200, "Found departments", departmentDtoList);
    }


    private List<Map<String, String>> getResultRequest(String url) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> employeeResponse = restTemplate.getForEntity(url, String.class);
        JSONObject jsonpObject = new JSONObject();
        if(employeeResponse.hasBody()) {
            Object employeesBody = employeeResponse.getBody();
            if(employeesBody != null){
                JSONParser parser = new JSONParser();
                jsonpObject = (JSONObject) parser.parse(employeesBody.toString());
            }
        }
        return (List<Map<String, String>>) jsonpObject.get("result");
    }

    private StringBuilder urlCondition(String sort, String sortBy, Integer limit, Integer offset){

        //Need validate value and fields name of sortBy
        StringBuilder departmentUrl = new StringBuilder();

        if(sort != null && sort.equalsIgnoreCase("desc")) {
            sort = "desc";
        }else{
            sort = "asc";
        }
        departmentUrl.append("&sort=").append(sort);
        if(sortBy != null){
            departmentUrl.append("&sortBy=").append(sortBy);
        }
        if(limit != null){
            limit = 10;
        }
        if(offset!= null){
            offset = 0;
        }
        departmentUrl.append("&limit=").append(limit);
        departmentUrl.append("&offset=").append(offset);

        return departmentUrl;
    }

}
