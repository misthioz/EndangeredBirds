package com.example.endangeredbirds.request;

import com.example.endangeredbirds.entity.Bird;
import com.example.endangeredbirds.entity.Species;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BirdRequest {
    private int speciesId;
    //private String speciesName;
    private String nickname;
    private String sex;
    private LocalDate dateOfBirth;

    public Bird convert(Species species){
        Bird bird = new Bird();
        bird.setSpeciesId(species);
        bird.setSpeciesName(species.getName());
        bird.setNickname(this.nickname);
        bird.setSex(this.sex);
        bird.setDateOfBirth(this.dateOfBirth);

        return bird;
    }

    public Bird convertUpdate(int id, Species species){
        return new Bird(id,species,species.getName(),nickname,sex,dateOfBirth);
    }
}
