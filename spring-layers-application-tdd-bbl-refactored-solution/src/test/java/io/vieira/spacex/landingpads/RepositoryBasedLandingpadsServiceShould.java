package io.vieira.spacex.landingpads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RepositoryBasedLandingpadsServiceShould {

    @Mock
    private LandingpadsRepository repository;

    @InjectMocks
    private RepositoryBasedLandingpadsService landingpadsService;

    @Test
    void returnAllLandingPads() {

    }

    @Test
    void returnAKnownLandingPadGivenItsId() {

    }

    @Test
    void returnAnEmptyOptionalWithAnUnknownLandingPadGivenItsId() {

    }
}
