package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFullname(String fullname);
}
