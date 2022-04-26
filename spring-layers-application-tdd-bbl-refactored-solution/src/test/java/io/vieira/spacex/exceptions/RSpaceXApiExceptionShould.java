package io.vieira.spacex.exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSpaceXApiExceptionShould {

    @ParameterizedTest
    @CsvSource({
            "404, Unable to fetch data from remote API : server returned status code 404",
            "400, Unable to fetch data from remote API : server returned status code 400",
            "500, Unable to fetch data from remote API : server returned status code 500",
            "503, Unable to fetch data from remote API : server returned status code 503",
    })
    void haveAMessageProperlySetDependingOnStatusCode(int statusCode, String expectedMessage) {
        final var exception = RSpaceXApiException.fromResponseCode(statusCode);

        assertEquals(expectedMessage, exception.getMessage());
    }
}
