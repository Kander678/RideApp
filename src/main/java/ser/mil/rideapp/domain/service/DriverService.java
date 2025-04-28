package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.UUID;

@Component
public class DriverService {

    private final RideRepository rideRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);

    public DriverService(RideRepository rideRepositorySQL) {
        this.rideRepository = rideRepositorySQL;
    }

    public Driver addDriver(String firstName, String lastName, Provider provider) {
        LOGGER.debug("Rozpoczęcie dodawania kierowcy...");
        Driver driver = new Driver(UUID.randomUUID().toString(), firstName, lastName, true, provider);
        rideRepository.save(driver);
        LOGGER.info("Kierowca {} został dodany ", driver.getFirstName());
        return driver;
    }

    public void addProvider(String id, Provider provider) {
        Driver driver = rideRepository.getDriverById(id);
        if (driver == null) {
            throw new RuntimeException("Driver not found");
        }
        driver.addProvider(provider);
        rideRepository.save(driver);
    }

    public void removeProvider(String id, Provider provider) {
        Driver driver = rideRepository.getDriverById(id);
        if (driver == null) {
            throw new RuntimeException("Driver not found");
        }
        driver.removeProvider(provider);
        rideRepository.save(driver);
    }
}
