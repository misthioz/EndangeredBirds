package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.Species;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class SpeciesResponse {
    @Getter private int id;
    @Getter private String name;
    @Getter private String scientificName;
    @Getter private String habitat;
    @Getter private int numCaptive;
    @Getter private int numWild;

    public SpeciesResponse(Species species){
        this.id = species.getSpeciesId();
        this.name = species.getName();
        this.scientificName = species.getScientificName();
        this.habitat = species.getHabitat();
        this.numCaptive = species.getNumCaptive();
        this.numWild = species.getNumWild();
    }

    public static List<SpeciesResponse> convert(List<Species> speciesList){
        return speciesList.stream().map(SpeciesResponse::new).collect(Collectors.toList());
    }
}
