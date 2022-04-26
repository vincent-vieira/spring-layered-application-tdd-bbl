package io.vieira.spacex.controllers;

import io.vieira.spacex.models.LandingPad;
import io.vieira.spacex.services.LandingpadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("landpads")
public class LandingpadsController {

    @Autowired
    private LandingpadsService landingpadsService;

    @GetMapping
    List<LandingPad> findAll() {
        return landingpadsService.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<LandingPad> findById(@PathVariable String id) {
        final var result = landingpadsService.findById(id);

        if(result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
