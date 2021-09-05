package com.example.endangeredbirds.service;

import com.example.endangeredbirds.entity.Species;
import com.example.endangeredbirds.repository.BirdRepository;
import com.example.endangeredbirds.repository.SpeciesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BirdService {
    private BirdRepository birdRepository;
    private SpeciesRepository speciesRepository;

    public void incrementNumCaptive(Species species){
        int num = species.getNumCaptive();
        species.setNumCaptive(num+1);
    }

}
