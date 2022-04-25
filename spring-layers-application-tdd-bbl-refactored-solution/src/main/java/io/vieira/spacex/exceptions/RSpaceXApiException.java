package io.vieira.spacex.exceptions;

public class RSpaceXApiException extends RuntimeException {

    public RSpaceXApiException(String message) {
        super(message);
    }

    public static RSpaceXApiException fromResponseCode(int responseCode) {
        return new RSpaceXApiException(String.format("Unable to fetch data from remote API : server returned status code %d", responseCode));
    }
}
