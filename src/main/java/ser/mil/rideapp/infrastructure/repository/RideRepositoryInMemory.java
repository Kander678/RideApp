package ser.mil.rideapp.infrastructure.repository;

import org.springframework.stereotype.Repository;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RideRepositoryInMemory implements RideRepository {

    private final List<Driver> drivers;
    private final List<Ride> rides = new ArrayList<>();

    public RideRepositoryInMemory() {
        drivers = new ArrayList<>(List.of(new Driver("1", "Robert", "Lewandowski"),
                new Driver("2", "Mateusz", "Maklowicz")));
    }

    @Override
    public void save(Ride ride) {
        rides.add(ride);
        System.out.println(ride);
    }

    @Override
    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public List<Ride> getRides() {
        return rides;
    }


}
