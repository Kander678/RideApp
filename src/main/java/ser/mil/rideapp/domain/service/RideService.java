package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.List;
import java.util.UUID;

@Component
public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RideService.class);

    public RideService(PricingService pricingService, RideRepository rideRepositorySQL) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepositorySQL;
    }

    public void orderRide(double startLat, double startLon, double endLat, double endLon, String customer, Currency baseCurrency, Currency finalCurrency, Provider provider) {
        Localization startLocalization = new Localization(startLat, startLon);
        Localization endLocalization = new Localization(endLat, endLon);
        Price price = pricingService.calculatePrice(startLocalization, endLocalization, baseCurrency, finalCurrency);
        rideRepository.save(new Ride(UUID.randomUUID().toString(), startLocalization, endLocalization, customer, price, RideStatus.PENDING, provider));
    }

    public void pairPassengerWithDriver(Provider provider) {
        LOGGER.debug("Rozpoczęcie procesu parowania pasażera z kierowcą...");

        List<Ride> rides = rideRepository.pendingRides(provider);
        List<Driver> drivers = rideRepository.availableDrivers(provider);

        while (!rides.isEmpty() && !drivers.isEmpty()) {
            Ride pendingRide = rides.getFirst();

            Driver matchingDriver = null;
            for (Driver driver : drivers) {
                if (driver.getProvider().contains(pendingRide.getProvider())) {
                    matchingDriver = driver;
                    break;
                }
            }

            if (matchingDriver != null) {
                pendingRide.setDriver(matchingDriver);
                pendingRide.setStatus(RideStatus.FOUND);
                matchingDriver.setAvailable(false);

                rideRepository.save(pendingRide);
                rideRepository.save(matchingDriver);

                LOGGER.info("Pasażer {} został sparowany z kierowcą {}", pendingRide.getCustomer(), matchingDriver.getFirstName());

                drivers.remove(matchingDriver);
                rides.remove(pendingRide);
            } else {
                rides.add(rides.removeFirst());
                LOGGER.warn("Brak dostępnego kierowcy dla pasażera {}", pendingRide.getCustomer());
                break;
            }
        }

        LOGGER.debug("Zakończono proces parowania. Pozostało {} oczekujących przejazdów i {} dostępnych kierowców.", rides.size(), drivers.size());
    }
}