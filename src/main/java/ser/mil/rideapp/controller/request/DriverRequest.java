package ser.mil.rideapp.controller.request;

import ser.mil.rideapp.domain.model.Provider;

public record DriverRequest(String firstName, String lastName, Provider provider) {
}
