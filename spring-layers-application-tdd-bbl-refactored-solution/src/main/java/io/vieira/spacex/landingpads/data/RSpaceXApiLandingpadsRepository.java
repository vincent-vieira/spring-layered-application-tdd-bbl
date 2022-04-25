package io.vieira.spacex.landingpads.data;

import io.vieira.spacex.exceptions.RSpaceXApiException;
import io.vieira.spacex.landingpads.LandingPad;
import io.vieira.spacex.landingpads.LandingpadsRepository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class RSpaceXApiLandingpadsRepository implements LandingpadsRepository {

    private final RestTemplate restTemplate;

    public RSpaceXApiLandingpadsRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<LandingPad> findAll() {
        final var landingpadsResponse = restTemplate.getForEntity("/landpads", RSpaceXLandingPad[].class);

        return switch (landingpadsResponse.getStatusCodeValue()) {
            case 200 -> Arrays
                    .stream(Objects.requireNonNull(landingpadsResponse.getBody()))
                    .map(this::convertRSpaceXLandingPadToLandingPad)
                    .toList();
            default -> throw RSpaceXApiException.fromResponseCode(landingpadsResponse.getStatusCodeValue());
        };
    }

    private LandingPad convertRSpaceXLandingPadToLandingPad(RSpaceXLandingPad rSpaceXLandingPad) {
        return new LandingPad(
                rSpaceXLandingPad.getName(),
                rSpaceXLandingPad.getFullName(),
                rSpaceXLandingPad.getType(),
                rSpaceXLandingPad.getDetails(),
                rSpaceXLandingPad.getId(),
                rSpaceXLandingPad.getStatus() == RSpaceXLandingPad.Status.ACTIVE,
                new LandingPad.Location(
                        rSpaceXLandingPad.getLocality(),
                        rSpaceXLandingPad.getRegion(),
                        rSpaceXLandingPad.getLatitude(),
                        rSpaceXLandingPad.getLongitude()
                )
        );
    }

    @Override
    public Optional<LandingPad> findById(String id) {
        try {
            final var landingpadsResponse = restTemplate.getForObject(
                    "/landpads/{id}",
                    RSpaceXLandingPad.class,
                    Map.of("id", id)
            );

            return Optional
                    .ofNullable(landingpadsResponse)
                    .map(this::convertRSpaceXLandingPadToLandingPad);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            final var rawStatusCode = httpStatusCodeException.getRawStatusCode();
            if (rawStatusCode == 404) {
                return Optional.empty();
            }
            throw RSpaceXApiException.fromResponseCode(rawStatusCode);
        }
    }
}
