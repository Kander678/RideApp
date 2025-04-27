package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

        Iterator<Ride> rideIterator = rides.iterator();

        while (rideIterator.hasNext() && !drivers.isEmpty()) {
            Ride pendingRide = rideIterator.next();

            Optional<Driver> matchingDriverOpt = drivers.stream()
                    .filter(driver -> driver.getProvider().contains(pendingRide.getProvider()))
                    .findFirst();

            if (matchingDriverOpt.isPresent()) {
                Driver availableDriver = matchingDriverOpt.get();

                pendingRide.setDriver(availableDriver);
                pendingRide.setStatus(RideStatus.FOUND);
                availableDriver.setAvailable(false);

                rideRepository.save(pendingRide);
                rideRepository.save(availableDriver);

                LOGGER.info("Pasażer {} został sparowany z kierowcą {}", pendingRide.getCustomer(), availableDriver.getFirstName());
            }

            LOGGER.debug("Zakończono proces parowania. Pozostało {} oczekujących przejazdów i {} dostępnych kierowców.", rides.size(), drivers.size());
        }

    }
}