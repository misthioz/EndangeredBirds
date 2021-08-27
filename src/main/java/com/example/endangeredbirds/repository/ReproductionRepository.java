package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.Reproduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReproductionRepository extends JpaRepository<Reproduction, Integer> {
}
