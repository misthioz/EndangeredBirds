package com.example.endangeredbirds.request;

import com.example.endangeredbirds.entity.EmailEmployee;
import com.example.endangeredbirds.entity.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailEmployeeRequest {
    private int employeeId;
    private String email;

    public EmailEmployee convert(Employee employee){
        EmailEmployee emailEmployee = new EmailEmployee();
        emailEmployee.setIdEmployee(employee);
        emailEmployee.setEmail(this.email);

        return emailEmployee;
    }

    public EmailEmployee convertUpdate(int id, Employee employee){
        return new EmailEmployee(id, email, employee);
    }
}
