package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Integer> {
    List<Bird> findByNickname(String nickname);
    List<Bird> findBySpeciesName(String speciesName);
    List<Bird> findBySex(String sex);
}
