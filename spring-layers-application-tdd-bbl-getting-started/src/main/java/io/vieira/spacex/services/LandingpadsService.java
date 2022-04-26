package io.vieira.spacex.services;

import io.vieira.spacex.RSpaceXConfiguration;
import io.vieira.spacex.models.LandingPad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class LandingpadsService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private RSpaceXConfiguration spaceXConfiguration;

    private RestTemplate restTemplate;

    @PostConstruct
    void init() {
        this.restTemplate = restTemplateBuilder
                .rootUri(spaceXConfiguration.getApi().getUrl())
                .build();
    }

    public List<LandingPad> findAll() {
        final var response = restTemplate.getForObject("/landpads", LandingPad[].class);
        return Arrays.asList(Objects.requireNonNull(response));
    }

    public LandingPad findById(String id) {
        return restTemplate.getForObject("/landpads/" + id, LandingPad.class);
    }
}
