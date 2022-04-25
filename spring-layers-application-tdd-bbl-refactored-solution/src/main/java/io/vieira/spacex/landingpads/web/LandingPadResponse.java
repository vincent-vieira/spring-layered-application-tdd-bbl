package io.vieira.spacex.landingpads.web;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import static io.vieira.spacex.landingpads.LandingPad.Location;

public record LandingPadResponse (String name,
                                  String fullName,
                                  String type,
                                  String details,
                                  String id,
                                  boolean isActive,
                                  @JsonUnwrapped
                                  Location location) {
}
