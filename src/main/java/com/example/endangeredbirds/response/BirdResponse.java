package com.example.endangeredbirds.response;

import com.example.endangeredbirds.entity.Bird;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BirdResponse {
    @Getter private int id;
    @Getter private int speciesId;
    @Getter private String speciesName;
    @Getter private String nickname;
    @Getter private String sex;
    @Getter private LocalDate dateOfBirth;

    public BirdResponse(Bird bird){
        this.id = bird.getId();
        this.speciesId = bird.getSpeciesId().getId();
        this.speciesName = bird.getSpeciesName();
        this.nickname = bird.getNickname();
        this.sex = bird.getSex();
        this.dateOfBirth = bird.getDateOfBirth();
    }

    public static List<BirdResponse> convert(List<Bird> birds){
        return birds.stream().map(BirdResponse::new).collect(Collectors.toList());
    }
}
