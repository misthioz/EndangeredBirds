package com.example.endangeredbirds.controller;

import com.example.endangeredbirds.entity.Employee;
import com.example.endangeredbirds.entity.Species;
import com.example.endangeredbirds.repository.EmployeeRepository;
import com.example.endangeredbirds.request.EmployeeRequest;
import com.example.endangeredbirds.response.EmployeeResponse;
import com.example.endangeredbirds.response.SpeciesResponse;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeResponse>> listEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok().body(EmployeeResponse.convert(employees));
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try{
            Employee employee = employeeRepository.getById(id);
            return ResponseEntity.ok().body(new EmployeeResponse(employee));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @GetMapping("/findName/{fullname}")
    public ResponseEntity<?> findByName(@PathVariable String name){
        try{
            List<Employee> employees = employeeRepository.findByFullname(name);
            if(!employees.isEmpty()){
                return ResponseEntity.ok().body(EmployeeResponse.convert(employees));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Name not found. Provided name: "+name);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(
            @RequestBody EmployeeRequest employeeRequest,
            UriComponentsBuilder uriComponentsBuilder){
        Employee employee = employeeRequest.convert();

        try{
            employeeRepository.save(employee);

            URI uri = uriComponentsBuilder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri();
            return ResponseEntity.created(uri).body(new EmployeeResponse(employee));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id, @RequestBody EmployeeRequest employeeRequest){
        List<Employee> employees = employeeRepository.findAll();
        if(employees.stream().anyMatch(e -> e.getId() == id)){
            try{
                Employee employee = employeeRequest.convertUpdate(id);
                employeeRepository.save(employee);
                return ResponseEntity.ok().body(new EmployeeResponse(employee));
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
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().body("Employee "+ id + " deleted successfully.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+ id);
        }
    }
}
