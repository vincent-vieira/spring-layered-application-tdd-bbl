package io.vieira.spacex.landingpads.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RSpaceXLandingPad {

    public enum Status {
        @JsonProperty("active")
        ACTIVE,
        @JsonProperty("inactive")
        INACTIVE,
        @JsonProperty("unknown")
        UNKNOWN,
        @JsonProperty("retired")
        RETIRED,
        @JsonProperty("lost")
        LOST,
        @JsonProperty("under construction")
        UNDER_CONSTRUCTION
    }

    private final double longitude;

    private final double latitude;

    private final String name;

    private final String fullName;

    private final Status status;

    private final String type;

    private final String locality;

    private final String region;

    private final String details;

    private final String id;

    @JsonCreator
    public RSpaceXLandingPad(@JsonProperty("name") String name,
                             @JsonProperty("full_name") String fullName,
                             @JsonProperty("status") Status status,
                             @JsonProperty("type") String type,
                             @JsonProperty("locality") String locality,
                             @JsonProperty("region") String region,
                             @JsonProperty("details") String details,
                             @JsonProperty("id") String id,
                             @JsonProperty("latitude") double latitude,
                             @JsonProperty("longitude") double longitude) {
        this.name = name;
        this.fullName = fullName;
        this.status = status;
        this.type = type;
        this.locality = locality;
        this.region = region;
        this.details = details;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Status getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getDetails() {
        return details;
    }

    public String getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RSpaceXLandingPad that)) return false;
        return Double.compare(that.longitude, longitude) == 0 && Double.compare(that.latitude, latitude) == 0 && Objects.equals(name, that.name) && Objects.equals(fullName, that.fullName) && status == that.status && Objects.equals(type, that.type) && Objects.equals(locality, that.locality) && Objects.equals(region, that.region) && Objects.equals(details, that.details) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, name, fullName, status, type, locality, region, details, id);
    }
}
