package io.vieira.spacex.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Objects;

public final class LandingPad {
    private final String name;

    private final String fullName;

    private final String type;

    private final String details;

    private final String id;

    private final boolean isActive;

    @JsonIgnore
    private final LandingPadLocation location;

    public LandingPad(String name,
                      String fullName,
                      String type,
                      String details,
                      String id,
                      boolean isActive,
                      LandingPadLocation location) {
        this.name = name;
        this.fullName = fullName;
        this.type = type;
        this.details = details;
        this.id = id;
        this.isActive = isActive;
        this.location = location;
    }

    @JsonCreator
    public LandingPad(@JsonProperty("name") String name,
                      @JsonProperty("full_name") String fullName,
                      @JsonProperty("status") String status,
                      @JsonProperty("type") String type,
                      @JsonProperty("details") String details,
                      @JsonProperty("id") String id,
                      @JsonProperty("region") String region,
                      @JsonProperty("locality") String locality,
                      @JsonProperty("latitude") double latitude,
                      @JsonProperty("longitude") double longitude) {
        this(name, fullName, type, details, id, "active".equals(status), new LandingPadLocation(
                locality,
                region,
                latitude,
                longitude
        ));
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    @JsonUnwrapped
    public LandingPadLocation getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandingPad that)) return false;
        return isActive == that.isActive && Objects.equals(name, that.name) && Objects.equals(fullName, that.fullName) && Objects.equals(type, that.type) && Objects.equals(details, that.details) && Objects.equals(id, that.id) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fullName, type, details, id, isActive, location);
    }
}
