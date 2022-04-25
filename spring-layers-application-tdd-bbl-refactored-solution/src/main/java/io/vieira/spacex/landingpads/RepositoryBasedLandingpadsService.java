package io.vieira.spacex.landingpads;

import java.util.List;
import java.util.Optional;

public class RepositoryBasedLandingpadsService implements LandingpadsService {

    private final LandingpadsRepository repository;

    public RepositoryBasedLandingpadsService(LandingpadsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LandingPad> findAll() {
        return repository
                .findAll()
                .stream()
                .toList();
    }

    @Override
    public Optional<LandingPad> findById(String id) {
        return repository.findById(id);
    }
}
