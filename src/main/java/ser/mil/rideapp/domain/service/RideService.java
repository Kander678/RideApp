package ser.mil.rideapp.domain.service;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.controller.request.LoggingController;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;
    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    public RideService(PricingService pricingService, RideRepository rideRepository) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepository;
    }

    public void orderRide(String from, String to, String customer) {
        double price=pricingService.calculatePrice(from,to);
        rideRepository.save(new Ride("1",from, to, customer, price, RideStatus.PENDING));
    }

    public void pairPassengerWithDriver() {
        logger.debug("Rozpoczęcie procesu parowania pasażera z kierowcą...");

        List<Ride> rides = rideRepository.pendingRides();
        List<Driver> drivers = rideRepository.getDrivers().stream()
                .filter(Driver::getAvailable)
                .collect(Collectors.toList());

        while (!rides.isEmpty() && !drivers.isEmpty()) {
            Ride pendingRide = rides.removeFirst();  // Pobierz i usuń pierwszy przejazd
            Driver availableDriver = drivers.removeFirst(); // Pobierz i usuń pierwszego dostępnego kierowcę

            pendingRide.setDriver(availableDriver);
            pendingRide.setStatus(RideStatus.FOUND);
            availableDriver.setAvailable(false);

            logger.info("Pasażer {} został sparowany z kierowcą {}",
                    pendingRide.getCustomer(), availableDriver.getFirstName());
        }

        if (rides.isEmpty()) {
            logger.debug("Brak oczekujących przejazdów do sparowania.");
        }
        if (drivers.isEmpty()) {
            logger.debug("Brak dostępnych kierowców.");
        }
    }

}
