package ser.mil.rideapp.infrastructure.database.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ser.mil.rideapp.domain.service.RideService;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    @ConditionalOnProperty(name = "scheduler.pair-passengers.enabled",havingValue = "true")
    public PairPassengersUberScheduler pairPassengersUberScheduler(RideService rideService) {
        return new PairPassengersUberScheduler(rideService);
    }

    @Bean
    @ConditionalOnProperty(name = "scheduler.pair-passengers.enabled",havingValue = "true")
    public PairPassengersFreeNowScheduler pairPassengersFreeNowScheduler(RideService rideService) {
        return new PairPassengersFreeNowScheduler(rideService);
    }
    @Bean
    @ConditionalOnProperty(name = "scheduler.pair-passengers.enabled",havingValue = "true")
    public PairPassengersBoltScheduler pairPassengersBoltScheduler(RideService rideService) {
        return new PairPassengersBoltScheduler(rideService);
    }
}
