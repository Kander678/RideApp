package ser.mil.rideapp.domain.service;

import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.domain.repository.RideRepository;

@Component
public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;

    public RideService(PricingService pricingService, RideRepository rideRepository) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepository;
    }

    public void orderRide(String from, String to, String customer) {
        double price=pricingService.calculatePrice(from,to);
        rideRepository.save(new Ride("1",from, to, customer, price, RideStatus.PENDING));
    }

    public void pairPassengerWithDriver(){
        Ride pendingRide= rideRepository.getRides().stream()
                .filter(ride -> ride.getStatus().equals(RideStatus.PENDING))
                .findFirst()
                .orElse(null);
        Driver availableDriver = rideRepository.getDrivers().stream()
                .filter(Driver::getAvailable).findFirst().orElse(null);
        if (pendingRide != null) {
            pendingRide.setDriver(availableDriver);
        }
        if (pendingRide != null) {
            pendingRide.setStatus(RideStatus.FOUND);
        }

        System.out.println(pendingRide);
    }
}
