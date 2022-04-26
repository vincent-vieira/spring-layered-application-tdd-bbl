package io.vieira.spacex.models;

import java.util.Objects;

public final class LandingPadLocation {
    private final String locality;

    private final String region;

    private final double latitude;

    private final double longitude;

    public LandingPadLocation(String locality, String region, double latitude, double longitude) {
        this.locality = locality;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandingPadLocation that)) return false;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && Objects.equals(locality, that.locality) && Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locality, region, latitude, longitude);
    }
}
