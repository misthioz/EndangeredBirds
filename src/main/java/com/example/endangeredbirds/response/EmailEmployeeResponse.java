package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.EmailEmployee;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class EmailEmployeeResponse {
    @Getter private int id;
    @Getter private int employeeId;
    @Getter private String email;

    public EmailEmployeeResponse(EmailEmployee emailEmployee){
        this.id = emailEmployee.getId();
        this.employeeId = emailEmployee.getIdEmployee().getId_employee();
        this.email = emailEmployee.getEmail();
    }

    public static List<EmailEmployeeResponse> convert(List<EmailEmployee> emails){
        return emails.stream().map(EmailEmployeeResponse::new).collect(Collectors.toList());
    }
}
