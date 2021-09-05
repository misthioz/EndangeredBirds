package com.example.endangeredbirds.controller;

import com.example.endangeredbirds.entity.EmailEmployee;
import com.example.endangeredbirds.entity.Employee;
import com.example.endangeredbirds.repository.EmailEmployeeRepository;
import com.example.endangeredbirds.repository.EmployeeRepository;
import com.example.endangeredbirds.request.EmailEmployeeRequest;
import com.example.endangeredbirds.response.EmailEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emailemployee")
public class EmailEmployeeController {
    @Autowired
    private EmailEmployeeRepository emailEmployeeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/list")
    public ResponseEntity<List<EmailEmployeeResponse>> list(){
        List<EmailEmployee> emails = emailEmployeeRepository.findAll();
        return ResponseEntity.ok().body(EmailEmployeeResponse.convert(emails));
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try {
            EmailEmployee email = emailEmployeeRepository.getById(id);
            return ResponseEntity.ok().body(new EmailEmployeeResponse(email));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @GetMapping("/findEmployee/{id}")
    public ResponseEntity<?> findByEmployeeId(@PathVariable int id){
        try{
            List<EmailEmployee> emails = emailEmployeeRepository.findByIdEmployee(id);
            if(!emails.isEmpty()){
                return ResponseEntity.ok().body(EmailEmployeeResponse.convert(emails));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PostMapping
    public ResponseEntity<?> addEmailEmployee(
            @RequestBody EmailEmployeeRequest emailEmployeeRequest,
            UriComponentsBuilder uriComponentsBuilder) throws Exception {
        Employee employee = employeeRepository.findById(emailEmployeeRequest.getEmployeeId()).orElseThrow(Exception::new);
        EmailEmployee emailEmployee = emailEmployeeRequest.convert(employee);

        try{
            emailEmployeeRepository.save(emailEmployee);

            URI uri = uriComponentsBuilder.path("/emailemployee/{id}").
                    buildAndExpand(emailEmployee.getId()).toUri();
            return ResponseEntity.created(uri).body(new EmailEmployeeResponse(emailEmployee));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestBody EmailEmployeeRequest emailEmployeeRequest) throws Exception {
        Employee employee = employeeRepository.findById(emailEmployeeRequest.getEmployeeId()).orElseThrow(Exception::new);
        List<EmailEmployee> emails = emailEmployeeRepository.findAll();

        if(emails.stream().anyMatch(e -> e.getId() == id)){
            try{
                EmailEmployee emailEmployee = emailEmployeeRepository.getById(id);
                emailEmployeeRepository.save(emailEmployee);
                return ResponseEntity.ok().body(new EmailEmployeeResponse(emailEmployee));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try{
            emailEmployeeRepository.deleteById(id);
            return ResponseEntity.ok().body("Email "+ id + " deleted successfully.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }


}
