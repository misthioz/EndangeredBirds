package com.example.endangeredbirds.controller;

import com.example.endangeredbirds.entity.Reproduction;
import com.example.endangeredbirds.entity.Species;
import com.example.endangeredbirds.repository.ReproductionRepository;
import com.example.endangeredbirds.repository.SpeciesRepository;
import com.example.endangeredbirds.request.ReproductionRequest;
import com.example.endangeredbirds.response.ReproductionResponse;
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
@RequestMapping("/reproduction")
public class ReproductionController {

    private ReproductionRepository reproductionRepository;
    private SpeciesRepository speciesRepository;

    @GetMapping("/list")
    public ResponseEntity<List<ReproductionResponse>> listReproduction(){
        List<Reproduction> list = reproductionRepository.findAll();
        return ResponseEntity.ok().body(ReproductionResponse.convert(list));
    }

    @GetMapping("/findID/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try{
            Reproduction reproduction = reproductionRepository.getById(id);
            return ResponseEntity.ok().body(new ReproductionResponse(reproduction));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @GetMapping("findMatingSeason/{matingSeason}")
    public ResponseEntity<?> findByMatingSeason(@PathVariable String matingSeason){
        try{
            List<Reproduction> replist = reproductionRepository.findByMatingSeason(matingSeason);
            if(!replist.isEmpty()){
                return ResponseEntity.ok().body(ReproductionResponse.convert(replist));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Season not found. Provided season: "+matingSeason);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @GetMapping("/findHatchingTime/{hatchingTime}")
    public ResponseEntity<?> findByHatchingTime(@PathVariable int hatchingTime){
        try{
            List<Reproduction> replist = reproductionRepository.findByHatchingTime(hatchingTime);
            if(!replist.isEmpty()){
                return ResponseEntity.ok().body(ReproductionResponse.convert(replist));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Species not found. Provided hatching time: "+hatchingTime);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PostMapping
    public ResponseEntity<?> addReproduction(
            @RequestBody ReproductionRequest reproductionRequest,
            UriComponentsBuilder uriComponentsBuilder) throws Exception {
        Species species = speciesRepository.findById(reproductionRequest.getSpeciesId()).orElseThrow(Exception::new);
        Reproduction reproduction = reproductionRequest.convert(species);

        try{
            reproductionRepository.save(reproduction);
            URI uri = uriComponentsBuilder.path("/reproduction/{idReproduction}").
                    buildAndExpand(reproduction.getSpeciesId()).toUri();

            return ResponseEntity.created(uri).body(new ReproductionResponse(reproduction));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PutMapping("/{idReproduction}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ReproductionRequest reproductionRequest) throws Exception {
        Species species = speciesRepository.findById(reproductionRequest.getSpeciesId()).orElseThrow(Exception::new);
        List<Reproduction> replist = reproductionRepository.findAll();

        if(replist.stream().anyMatch(r -> r.getReproductionId() == id)){
            try{
                Reproduction reproduction = reproductionRequest.convertUpdate(id, species);
                reproductionRepository.save(reproduction);
                return ResponseEntity.ok().body(new ReproductionResponse(reproduction));
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try{
            reproductionRepository.deleteById(id);
            return ResponseEntity.ok().body("Reproduction info "+id+" deleted successufylly");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }



}
