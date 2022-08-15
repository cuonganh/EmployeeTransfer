package com.example.employeetransfer.service;

import com.example.employeetransfer.model.DepartmentDto;
import com.example.employeetransfer.model.EmployeeDto;
import com.example.employeetransfer.model.TransferResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeTransferService {

    public TransferResponse<EmployeeDto> getEmployee(Long employeeId) throws ParseException {

        String employeeURL = "http://localhost:8088/employees/" + employeeId;
        List<Map<String, String>> result = getResultRequest(employeeURL);

        EmployeeDto employeeDto = new EmployeeDto();
        if(result.size() > 0){
            employeeDto.setEmployeeId(Long.valueOf(result.get(0).get("employeeId")));
            employeeDto.setDepartment(result.get(0).get("department"));
            employeeDto.setFirstName(result.get(0).get("firstName"));
            employeeDto.setLastName(result.get(0).get("lastName"));
            employeeDto.setDateOfBirth(result.get(0).get("dateOfBirth"));
            employeeDto.setAddress(result.get(0).get("address"));
            employeeDto.setEmail(result.get(0).get("email"));
            employeeDto.setPhoneNumber(result.get(0).get("phoneNumber"));
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

        StringBuilder departmentUrl = new StringBuilder("http://localhost:8088/departments?1=1");

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
            for(int i = 0; i < result.size(); i++){
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setDepartmentId(Long.valueOf(result.get(i).get("departmentId")));
                departmentDto.setName(result.get(i).get("name"));
                departmentDto.setMember(Long.valueOf(result.get(i).get("member")));
                departmentDto.setDescription(result.get(i).get("description"));
                departmentDto.setLeader(result.get(i).get("leader"));
                departmentDtoList.add(departmentDto);
            }
        }
        return new TransferResponse<>(200, "Found department", departmentDtoList);
    }


    private List<Map<String, String>> getResultRequest(String url) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> employeeResponse = restTemplate.getForEntity(url, String.class);
        JSONObject jsonpObject = new JSONObject();
        if(employeeResponse != null &&employeeResponse.hasBody()) {
            Object employeesBody = employeeResponse.getBody();
            if(employeesBody != null){
                JSONParser parser = new JSONParser();
                jsonpObject = (JSONObject) parser.parse(employeesBody.toString());
            }
        }
        List<Map<String, String>> result = (List<Map<String, String>>) jsonpObject.get("result");
        return result;
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
