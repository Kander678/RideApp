package ser.mil.rideapp.infrastructure.database.inmemory.repository;

import org.springframework.stereotype.Repository;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RideRepositoryInMemory implements RideRepository {

    private final List<Driver> drivers;
    private final List<Ride> rides = new ArrayList<>();

    public RideRepositoryInMemory() {
        drivers = new ArrayList<>(List.of(new Driver("1", "Robert", "Lewandowski", Provider.FREENOW),
                new Driver("2", "Mateusz", "Maklowicz", Provider.BOLT)));
    }

    @Override
    public void save(Ride ride) {
        rides.add(ride);
    }

    @Override
    public void save(Driver driver) {
        drivers.add(driver);
    }

    @Override
    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public List<Ride> pendingRides(Provider provider) {
        return rides.stream()
                .filter(ride -> ride.getStatus().equals(RideStatus.PENDING) && ride.getProvider() == provider)
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> availableDrivers(Provider provider) {
        return drivers.stream().
                filter(driver -> driver.getAvailable() && driver.getProvider().contains(provider))
                .collect(Collectors.toList());
    }

    @Override
    public Driver getDriverById(String id) {
        return drivers.stream().filter(driver -> driver.getId().equals(id)).findFirst().orElse(null);
    }


    @Override
    public List<Ride> getRides() {
        return rides;
    }


}
