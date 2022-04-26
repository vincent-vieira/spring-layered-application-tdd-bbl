package io.vieira.spacex.landingpads;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static io.vieira.spacex.landingpads.LandingPadFixtures.sampleLandingPad;
import static io.vieira.spacex.landingpads.LandingPadFixtures.sampleLandingPadResponse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class LandingpadsControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LandingpadsService landingpadsService;

    @Test
    void returnAllLandingPads() throws Exception {
        when(landingpadsService.findAll()).thenReturn(List.of(sampleLandingPad));

        mockMvc
                .perform(get("/landpads").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(sampleLandingPadResponse)), true));

        verify(landingpadsService).findAll();
        verifyNoMoreInteractions(landingpadsService);
    }

    @Test
    void returnAKnownLandingPadGivenItsId() throws Exception {
        when(landingpadsService.findById(any())).thenReturn(Optional.of(sampleLandingPad));

        mockMvc
                .perform(get("/landpads/{id}", "id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleLandingPadResponse), true));

        verify(landingpadsService).findById("id");
        verifyNoMoreInteractions(landingpadsService);
    }

    @Test
    void returnANotFoundResponseWithAnUnknownLandingPadGivenItsId() throws Exception {
        when(landingpadsService.findById(any())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/landpads/{id}", "id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(landingpadsService).findById("id");
        verifyNoMoreInteractions(landingpadsService);
    }
}
