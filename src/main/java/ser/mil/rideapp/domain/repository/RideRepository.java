package ser.mil.rideapp.domain.repository;

import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;

import java.util.List;

@Component
public interface RideRepository {
    void save(Ride ride);
    List<Ride> getRides();
    List<Driver> getDrivers();

    List<Ride> pendingRides();
    List<Driver> availableDrivers();
}
