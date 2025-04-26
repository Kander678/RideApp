package ser.mil.rideapp.infrastructure.database.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.service.RideService;

public class PairPassengersFreeNowScheduler {
    private final RideService rideService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PairPassengersFreeNowScheduler.class);
    public PairPassengersFreeNowScheduler(RideService rideService) {
        this.rideService = rideService;
    }
    @Scheduled(cron="${scheduler.pair-passengers.cron}")
    public void schedulePairPassengers() {
        LOGGER.info("Running pair passengers scheduler FREENOW");

        rideService.pairPassengerWithDriver(Provider.FREENOW);

        LOGGER.info("Job completed FREENOW");
    }
}
