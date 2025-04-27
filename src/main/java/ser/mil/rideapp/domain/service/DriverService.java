package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.repository.RideRepository;
import ser.mil.rideapp.infrastructure.database.jpa.repository.RideRepositorySQL;

import java.util.Set;
import java.util.UUID;

@Component
public class DriverService {

    private final RideRepository rideRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);

    public DriverService(RideRepository rideRepositorySQL) {
        this.rideRepository = rideRepositorySQL;
    }

    public void addDriver(String firstName, String lastName, boolean available, Provider provider) {
        LOGGER.debug("Rozpoczęcie dodawania kierowcy...");
        Driver driver = new Driver(UUID.randomUUID().toString(), firstName, lastName, available, provider);
        rideRepository.save(driver);
        LOGGER.info("Kierowca {} został dodany ", driver.getFirstName());
    }

    public void addProvider(String firstName,Provider provider) {
        Driver driver=rideRepository.findDriverByFirstName(firstName);
        if(driver==null) {
            return;
        }
        driver.addProvider(provider);
        rideRepository.save(driver);
    }

    public void removeProvider(String firstName,Provider provider) {
        Driver driver=rideRepository.findDriverByFirstName(firstName);
        if(driver==null) {
            return;
        }
        driver.removeProvider(provider);
        rideRepository.save(driver);
    }
}
