package io.vieira.spacex.landingpads;

import io.vieira.spacex.exceptions.RSpaceXApiException;
import io.vieira.spacex.landingpads.data.RSpaceXApiLandingpadsRepository;
import io.vieira.spacex.landingpads.data.RSpaceXLandingPad;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.vieira.spacex.landingpads.LandingPadFixtures.sampleLandingPad;
import static io.vieira.spacex.landingpads.LandingPadFixtures.sampleRSpaceXLandingPad;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RSpaceXApiLandingpadsRepositoryShould {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RSpaceXApiLandingpadsRepository landingpadsRepository;

    @Test
    void returnAllLandingPadsFromRemoteApi() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new RSpaceXLandingPad[]{sampleRSpaceXLandingPad});

        final var result = assertDoesNotThrow(() -> landingpadsRepository.findAll());

        assertEquals(List.of(sampleLandingPad), result);

        verify(restTemplate).getForObject("/landpads", RSpaceXLandingPad[].class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void shouldPropagateExceptionsWhenFetchingAllLandingPads() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(RSpaceXApiException.class, () -> landingpadsRepository.findAll());
    }

    @Test
    void returnAKnownLandingPadGivenItsId() {
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenReturn(sampleRSpaceXLandingPad);

        final var result = assertDoesNotThrow(() -> landingpadsRepository.findById("id"));

        assertEquals(Optional.of(sampleLandingPad), result);

        verify(restTemplate).getForObject("/landpads/{id}", RSpaceXLandingPad.class, Map.of("id", "id"));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void returnAnEmptyOptionalWithAnUnknownLandingPadGivenItsId() {
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        final var result = assertDoesNotThrow(() -> landingpadsRepository.findById("id"));

        assertEquals(Optional.empty(), result);

        verify(restTemplate).getForObject("/landpads/{id}", RSpaceXLandingPad.class, Map.of("id", "id"));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void shouldPropagateExceptionsWhenFetchingALandingPadGivenItsId() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(RSpaceXApiException.class, () -> landingpadsRepository.findAll());
    }
}
