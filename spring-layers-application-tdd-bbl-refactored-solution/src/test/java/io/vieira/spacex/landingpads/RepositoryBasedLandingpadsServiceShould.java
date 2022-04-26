package io.vieira.spacex.landingpads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.vieira.spacex.landingpads.LandingPadFixtures.sampleLandingPad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryBasedLandingpadsServiceShould {

    @Mock
    private LandingpadsRepository repository;

    @InjectMocks
    private RepositoryBasedLandingpadsService landingpadsService;

    @Test
    void returnAllLandingPads() {
        when(repository.findAll()).thenReturn(List.of(sampleLandingPad));

        final var result = landingpadsService.findAll();

        assertEquals(List.of(sampleLandingPad), result);

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void returnAKnownLandingPadGivenItsId() {
        when(repository.findById(any())).thenReturn(Optional.of(sampleLandingPad));

        final var result = landingpadsService.findById("id");

        assertEquals(Optional.of(sampleLandingPad), result);

        verify(repository).findById("id");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void returnAnEmptyOptionalWithAnUnknownLandingPadGivenItsId() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        final var result = landingpadsService.findById("id");

        assertEquals(Optional.empty(), result);

        verify(repository).findById("id");
        verifyNoMoreInteractions(repository);
    }
}
