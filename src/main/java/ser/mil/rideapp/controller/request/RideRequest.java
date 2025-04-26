package ser.mil.rideapp.controller.request;

import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Provider;

public record RideRequest(LocalizationRequest startLocalization, LocalizationRequest endLocalization, String customer,
                          Currency baseCurrency, Currency finalCurrency, Provider provider) {
}
