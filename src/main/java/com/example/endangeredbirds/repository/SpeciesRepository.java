package com.example.endangeredbirds.repository;

import com.example.endangeredbirds.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    List<Species> findByName(String name);
    List<Species> findByHabitat(String habitat);
}
