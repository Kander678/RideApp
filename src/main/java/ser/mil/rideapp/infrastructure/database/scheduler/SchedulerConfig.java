package ser.mil.rideapp.infrastructure.database.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ser.mil.rideapp.domain.service.RideService;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    public PairPassengersUberScheduler pairPassengersUberScheduler(RideService rideService) {
        return new PairPassengersUberScheduler(rideService);
    }

    @Bean
    public PairPassengersFreeNowScheduler pairPassengersFreeNowScheduler(RideService rideService) {
        return new PairPassengersFreeNowScheduler(rideService);
    }
    @Bean
    public PairPassengersBoltScheduler pairPassengersBoltScheduler(RideService rideService) {
        return new PairPassengersBoltScheduler(rideService);
    }
}
