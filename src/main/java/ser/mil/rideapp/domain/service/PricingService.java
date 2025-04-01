package ser.mil.rideapp.domain.service;

import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Localization;

import java.util.*;

@Component
public class PricingService {

    public double calculatePrice(Localization startLocalization, Localization endLocalization) {
        double converter=2;
        double distance=calculateDistance(startLocalization.getLat(), startLocalization.getLon(), endLocalization.getLat(), endLocalization.getLon());
        return Math.round(converter*distance);
    }

    public double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        final double EARTH_RADIUS=6371;

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}