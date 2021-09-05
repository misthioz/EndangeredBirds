package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.Reproduction;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ReproductionResponse {
    @Getter private int reproductionId;
    @Getter private int speciesId;
    @Getter private String matingSeason;
    @Getter private int hatchingTime;
    @Getter private int numOffspring;
    @Getter private String speciesName;

    public ReproductionResponse(Reproduction reproduction){
        this.reproductionId = reproduction.getReproductionId();
        this.speciesId = reproduction.getSpeciesId().getSpeciesId();
        this.matingSeason = reproduction.getMatingSeason();
        this.hatchingTime = reproduction.getHatchingTime();
        this.numOffspring = reproduction.getNumOffspring();
        this.speciesName = reproduction.getSpeciesName();
    }

    public static List<ReproductionResponse> convert(List<Reproduction> listrep){
        return listrep.stream().map(ReproductionResponse::new).collect(Collectors.toList());
    }
}
