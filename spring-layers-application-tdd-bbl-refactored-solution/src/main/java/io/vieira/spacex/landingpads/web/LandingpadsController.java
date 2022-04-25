package io.vieira.spacex.landingpads.web;

import io.vieira.spacex.landingpads.LandingPad;
import io.vieira.spacex.landingpads.LandingpadsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("landpads")
public class LandingpadsController {

    private final LandingpadsService landingpadsService;

    public LandingpadsController(LandingpadsService landingpadsService) {
        this.landingpadsService = landingpadsService;
    }

    @GetMapping
    List<LandingPadResponse> findAll() {
        return landingpadsService
                .findAll()
                .stream()
                .map(this::toLandingPadResponse)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<LandingPadResponse> findById(@PathVariable String id) {
        return ResponseEntity.of(landingpadsService
                .findById(id)
                .map(this::toLandingPadResponse)
        );
    }

    private LandingPadResponse toLandingPadResponse(LandingPad landingPad) {
        return new LandingPadResponse(
                landingPad.name(),
                landingPad.fullName(),
                landingPad.type(),
                landingPad.details(),
                landingPad.id(),
                landingPad.isActive(),
                landingPad.location()
        );
    }
}
