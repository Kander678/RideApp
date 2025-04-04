package ser.mil.rideapp.controller.request;

import ser.mil.rideapp.domain.model.Currency;

public record RideRequest(double startLat, double startLong, double endLat, double endLong, String customer,
                          Currency baseCurrency, Currency finalCurrency) {
}
