package net.kf03w5t5741l.sensorbase.server.domain.location;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Location {
    private static final double DEFAULT_ALTITUDE = 0;

    public static double getDefaultAltitude() {
        return Location.DEFAULT_ALTITUDE;
    }

    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final LocationSource source;

    @Id
    @NotNull
    Integer id;

    public Location(double latitude,
                    double longitude,
                    double altitude,
                    LocationSource source) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.source = source;

        this.id = this.hashCode();
    }

    public Location(double latitude, double longitude, LocationSource source) {
        this(latitude, longitude, DEFAULT_ALTITUDE, source);
    }

    public Location() {
        this(0, 0, 0, LocationSource.zero);
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public LocationSource getSource() {
        return this.source;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }

        if (this.getClass() != otherObject.getClass()) {
            return false;
        }

        Location otherLocation = (Location) otherObject;

        if (this.hashCode() == otherObject.hashCode()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.latitude,
                this.longitude,
                this.altitude,
                this.source);
    }
}
