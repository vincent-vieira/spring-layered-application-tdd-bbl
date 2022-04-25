package io.vieira.spacex.landingpads;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class LandingpadsControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LandingpadsService landingpadsService;

    @Test
    void returnAllLandingPads() {

    }

    @Test
    void returnAKnownLandingPadGivenItsId() {

    }

    @Test
    void returnANotFoundResponseWithAnUnknownLandingPadGivenItsId() {

    }
}
