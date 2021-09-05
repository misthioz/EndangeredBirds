package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.Employee;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeResponse {
    @Getter private int id;
    @Getter private String fullname;
    @Getter private String role;
    @Getter private LocalDate date_of_birth;

    public EmployeeResponse(Employee employee){
        this.id = employee.getId_employee();
        this.fullname = employee.getFullname();
        this.role = employee.getRole();
        this.date_of_birth = employee.getDate_of_birth();
    }

    public static List<EmployeeResponse> convert(List<Employee> employees){
        return employees.stream().map(EmployeeResponse::new).collect(Collectors.toList());
    }
}
