package com.example.endangeredbirds.controller;

import com.example.endangeredbirds.entity.Bird;
import com.example.endangeredbirds.entity.Species;
import com.example.endangeredbirds.repository.BirdRepository;
import com.example.endangeredbirds.repository.SpeciesRepository;
import com.example.endangeredbirds.request.BirdRequest;
import com.example.endangeredbirds.response.BirdResponse;
import com.example.endangeredbirds.response.SpeciesResponse;
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

@RestController
@RequestMapping("/bird")
public class BirdController {
    @Autowired
    private BirdRepository birdRepository;
    private SpeciesRepository speciesRepository;

    @GetMapping("/list")
    public ResponseEntity<List<BirdResponse>> listBirds(){
        List<Bird> birds = birdRepository.findAll();
        return ResponseEntity.ok().body(BirdResponse.convert(birds));
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try{
            Bird bird = birdRepository.getById(id);
            return ResponseEntity.ok().body(new BirdResponse(bird));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

    @GetMapping("/findNickname/{nickname}")
    public ResponseEntity<?> findByNickname(@PathVariable String nickname){
        try{
            List<Bird> birds = birdRepository.findByNickname(nickname);
            if(!birds.isEmpty()){
                return ResponseEntity.ok().body(BirdResponse.convert(birds));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bird not found. Provided nickname: "+nickname);
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @GetMapping("/findSpeciesName/{speciesName}")
    public ResponseEntity<?> findBySpeciesName(@PathVariable String speciesName){
        try{
            List<Bird> birds = birdRepository.findBySpeciesName(speciesName);
            if(!birds.isEmpty()){
                return ResponseEntity.ok().body(BirdResponse.convert(birds));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bird not found. Providade species name: "+speciesName);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @GetMapping("/findSex/{sex}")
    public ResponseEntity<?> findBySex(@PathVariable String sex){
        try{
            List<Bird> birds = birdRepository.findBySex(sex);
            if(!birds.isEmpty()){
                return ResponseEntity.ok().body(BirdResponse.convert(birds));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Birds not found. Provided sex: "+sex);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PostMapping
    public ResponseEntity<?> addBird(@RequestBody BirdRequest birdRequest,
                                     UriComponentsBuilder uriComponentsBuilder) throws Exception {
        Species species = speciesRepository.findById(birdRequest.getSpeciesId()).orElseThrow(Exception::new);
        Bird bird = birdRequest.convert(species);

        try{
            birdRepository.save(bird);

            URI uri = uriComponentsBuilder.path("/bird/{id}").buildAndExpand(bird.getId()).toUri();

            return ResponseEntity.created(uri).body(new BirdResponse(bird));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to connect to the database");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody BirdRequest birdRequest) throws Exception {
        Species species = speciesRepository.findById(id).orElseThrow(Exception::new);

        List<Bird> birds = birdRepository.findAll();
        if(birds.stream().anyMatch(b -> b.getId() == id)){
            try{
                Bird bird = birdRequest.convertUpdate(id,species);
                birdRepository.save(bird);
                return ResponseEntity.ok().body(new BirdResponse(bird));
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
            birdRepository.deleteById(id);
            return ResponseEntity.ok().body("Bird "+id+" deleted successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found. Provided ID: "+id);
        }
    }

}
