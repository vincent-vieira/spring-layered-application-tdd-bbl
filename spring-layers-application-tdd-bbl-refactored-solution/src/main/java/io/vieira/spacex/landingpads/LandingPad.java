package io.vieira.spacex.landingpads;

public record LandingPad(String name,
                         String fullName,
                         String type,
                         String details,
                         String id,
                         boolean isActive,
                         Location location) {

    public record Location(String locality, String region, double latitude, double longitude) { }
}
