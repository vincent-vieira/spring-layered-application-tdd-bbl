package io.vieira.spacex.landingpads;

import io.vieira.spacex.landingpads.data.RSpaceXApiLandingpadsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RSpaceXApiLandingpadsRepositoryShould {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RSpaceXApiLandingpadsRepository landingpadsRepository;

    @Test
    void returnAllLandingPadsFromRemoteApi() {

    }

    @Test
    void returnAKnownLandingPadGivenItsId() {

    }

    @Test
    void returnAnEmptyOptionalWithAnUnknownLandingPadGivenItsId() {

    }
}
