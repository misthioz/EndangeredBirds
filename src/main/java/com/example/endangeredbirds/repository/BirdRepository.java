package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Integer> {
}
