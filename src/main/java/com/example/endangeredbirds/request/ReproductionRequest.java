package com.example.endangeredbirds.request;

import com.example.endangeredbirds.entity.Reproduction;
import com.example.endangeredbirds.entity.Species;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReproductionRequest {
    private int speciesId;
    private String matingSeason;
    private int hatchingTime;
    private int numOffspring;

    public Reproduction convert(Species species){
        Reproduction reproduction = new Reproduction();
        reproduction.setSpeciesId(species);
        reproduction.setMatingSeason(this.matingSeason);
        reproduction.setHatchingTime(this.hatchingTime);
        reproduction.setNumOffspring(this.numOffspring);
        reproduction.setSpeciesName(species.getName());

        return reproduction;
    }

    public Reproduction convertUpdate(int id, Species species){
        return new Reproduction(id,species,matingSeason,hatchingTime,numOffspring,species.getName());
    }
}
