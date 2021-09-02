package com.example.endangeredbirds.request;

import com.example.endangeredbirds.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {
    private String fullname;
    private String role;
    private LocalDate date_of_birth;

    public Employee convert(){
        Employee employee = new Employee();
        employee.setFullname(fullname);
        employee.setRole(role);
        employee.setDate_of_birth(date_of_birth);

        return employee;
    }

    public Employee convertUpdate(int id){
        return new Employee(id, fullname, role, date_of_birth);
    }
}
