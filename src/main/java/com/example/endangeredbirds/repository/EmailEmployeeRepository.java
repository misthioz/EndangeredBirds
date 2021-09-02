package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.EmailEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailEmployeeRepository extends JpaRepository<EmailEmployee, Integer> {
    List<EmailEmployee> findByIdEmployee(int id);
}
