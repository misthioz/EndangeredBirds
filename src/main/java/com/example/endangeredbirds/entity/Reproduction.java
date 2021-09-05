package com.example.endangeredbirds.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Reproduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reproductionId;

    @OneToOne
    @JoinColumn(name="species_id", referencedColumnName = "species_id")
    private Species speciesId;
    private String matingSeason;
    private int hatchingTime;
    private int numOffspring;
    private String speciesName;
}
