package io.vieira.spacex.landingpads;

import java.util.List;
import java.util.Optional;

public interface LandingpadsRepository {

    List<LandingPad> findAll();

    Optional<LandingPad> findById(String id);
}
