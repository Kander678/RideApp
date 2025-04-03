package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.List;

@Component
public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RideService.class);

    public RideService(PricingService pricingService, RideRepository rideRepository) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepository;
    }

    public void orderRide(double startLat, double startLon, double endLat, double endLon, String customer,Currency currency) {
        Localization startLocalization = new Localization(startLat, startLon);
        Localization endLocalization = new Localization(endLat, endLon);
        Price price = pricingService.calculatePrice(startLocalization, endLocalization,currency);
        rideRepository.save(new Ride("1", startLocalization, endLocalization, customer, price, RideStatus.PENDING));
    }

    public void pairPassengerWithDriver() {
        LOGGER.debug("Rozpoczęcie procesu parowania pasażera z kierowcą...");

        List<Ride> rides = rideRepository.pendingRides();
        List<Driver> drivers = rideRepository.availableDrivers();

        while (!rides.isEmpty() && !drivers.isEmpty()) {
            Ride pendingRide = rides.removeFirst();
            Driver availableDriver = drivers.removeFirst();

            pendingRide.setDriver(availableDriver);
            pendingRide.setStatus(RideStatus.FOUND);
            availableDriver.setAvailable(false);

            LOGGER.info("Pasażer {} został sparowany z kierowcą {}", pendingRide.getCustomer(), availableDriver.getFirstName());
        }

        LOGGER.debug("Zakończono proces parowania. Pozostało {} oczekujących przejazdów i {} dostępnych kierowców.", rides.size(), drivers.size());
    }

}
