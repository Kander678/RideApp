package ser.mil.rideapp.controller.request;

public record RideRequest(double startLat, double startLong, double endLat, double endLong, String customer) {
}
