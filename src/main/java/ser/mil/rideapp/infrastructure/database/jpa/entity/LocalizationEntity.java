package ser.mil.rideapp.infrastructure.database.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class LocalizationEntity {
    double lat;
    double lon;

    public LocalizationEntity() {
    }

    public LocalizationEntity(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
