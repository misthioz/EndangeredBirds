package com.example.endangeredbirds.controller;

import com.example.endangeredbirds.entity.Species;
import com.example.endangeredbirds.repository.SpeciesRepository;
import com.example.endangeredbirds.request.SpeciesRequest;
import com.example.endangeredbirds.response.SpeciesResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/species")
public class SpeciesController {

    private SpeciesRepository speciesRepository;

    @GetMapping("/list")
    public ResponseEntity<List<SpeciesResponse>> listSpecies(){
        List<Species> listSpecies = speciesRepository.findAll();
        return ResponseEntity.ok().body(SpeciesResponse.convert(listSpecies));
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try{
            Species species = speciesRepository.getById(id);
            return ResponseEntity.ok().body(new SpeciesResponse(species));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @GetMapping("/findName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name){
        try{
            List<Species> lspecies = speciesRepository.findByName(name);
            if(!lspecies.isEmpty()){
                return ResponseEntity.ok().body(SpeciesResponse.convert(lspecies));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Species not found. Provided name: "+name);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @GetMapping("/findHabitat/{habitat}")
    public ResponseEntity<?> findByHabitat(@PathVariable String habitat){
        try{
            List<Species> lspecies = speciesRepository.findByHabitat(habitat);
            if(!lspecies.isEmpty()){
                return ResponseEntity.ok().body(SpeciesResponse.convert(lspecies));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habitat not found. Provided habitat: "+habitat);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PostMapping
    public ResponseEntity<?> addSpecies(
            @RequestBody SpeciesRequest speciesRequest,
            UriComponentsBuilder uriComponentsBuilder){
        Species species = speciesRequest.convert();
        try{
            speciesRepository.save(species);

            URI uri = uriComponentsBuilder.path("/species/{id}").buildAndExpand(species.getSpeciesId()).toUri();
            return ResponseEntity.created(uri).body(new SpeciesResponse(species));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id, @RequestBody SpeciesRequest speciesRequest){
        List<Species> lspecies = speciesRepository.findAll();
        if(lspecies.stream().anyMatch(s -> s.getSpeciesId() == id)){
            try{
                Species species = speciesRequest.convertUpdate(id);
                speciesRepository.save(species);
                return ResponseEntity.ok().body(new SpeciesResponse(species));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try{
            speciesRepository.deleteById(id);
            return ResponseEntity.ok().body("Species "+ id + " deleted successufully.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }
}
