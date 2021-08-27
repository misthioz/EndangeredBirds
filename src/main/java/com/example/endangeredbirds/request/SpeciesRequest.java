package com.example.endangeredbirds.request;

import com.example.endangeredbirds.entity.Species;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeciesRequest {
    private String name;
    private String scientificName;
    private String habitat;
    private int numCaptive;
    private int numWild;

    public Species convert(){
        Species species = new Species();
        species.setName(this.name);
        species.setScientificName(this.scientificName);
        species.setHabitat(this.habitat);
        species.setNumCaptive(this.numCaptive);
        species.setNumWild(this.numWild);

        return species;
    }

    public Species convertUpdate(int id){
        return new Species(id, name, scientificName, habitat, numCaptive, numWild);
    }
}
