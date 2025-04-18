package ser.mil.rideapp.infrastructure.database.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import ser.mil.rideapp.domain.service.RideService;

public class PairPassengersScheduler {
    private final RideService rideService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PairPassengersScheduler.class);
    public PairPassengersScheduler(RideService rideService) {
        this.rideService = rideService;
    }
    @Scheduled(cron="${scheduler.pair-passengers.cron}")
    public void schedulePairPassengers() {
        LOGGER.info("Running pair passengers scheduler");
        rideService.pairPassengerWithDriver();

        LOGGER.info("Job completed");
    }

}
