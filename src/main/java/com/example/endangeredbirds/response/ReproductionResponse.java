package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.Reproduction;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ReproductionResponse {
    @Getter private int id;
    @Getter private int speciesId;
    @Getter private String matingSeason;
    @Getter private int hatchingTime;
    @Getter private int numOffspring;

    public ReproductionResponse(Reproduction reproduction){
        this.id = reproduction.getId();
        this.speciesId = reproduction.getSpeciesId().getId();
        this.matingSeason = reproduction.getMatingSeason();
        this.hatchingTime = reproduction.getHatchingTime();
        this.numOffspring = reproduction.getNumOffspring();
    }

    public static List<ReproductionResponse> convert(List<Reproduction> listrep){
        return listrep.stream().map(ReproductionResponse::new).collect(Collectors.toList());
    }
}
