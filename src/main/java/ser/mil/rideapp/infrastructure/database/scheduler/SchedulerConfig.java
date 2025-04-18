package ser.mil.rideapp.infrastructure.database.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ser.mil.rideapp.domain.service.RideService;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    public PairPassengersScheduler pairPassengersScheduler(RideService rideService) {
        return new PairPassengersScheduler(rideService);
    }
}
