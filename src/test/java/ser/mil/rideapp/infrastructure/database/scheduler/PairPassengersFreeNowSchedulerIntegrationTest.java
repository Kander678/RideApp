package ser.mil.rideapp.infrastructure.database.scheduler;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.infrastructure.database.jpa.entity.DriverEntity;
import ser.mil.rideapp.infrastructure.database.jpa.repository.DriverRepositorySpringData;
import ser.mil.rideapp.infrastructure.database.jpa.repository.RideRepositorySQL;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties = {
        "scheduler.pair-passengers.enabled=true"
})
class PairPassengersFreeNowSchedulerIntegrationTest {
    @Autowired
    private RideRepositorySQL rideRepository;
    @Autowired
    private DriverRepositorySpringData driverRepositorySpringData;

    @BeforeEach
    void setUp() {
        rideRepository.clearDatabase();
        List<DriverEntity> drivers = List.of(new DriverEntity("1", "Robert", "Lewandowski", true, Provider.FREENOW), new DriverEntity("2", "Kuba", "Marcinowski", true, Provider.BOLT));
        driverRepositorySpringData.saveAll(drivers);
    }

    @Test
    void shouldPairPassenger() {
        //Given
        rideRepository.save(new Ride("1", new Localization(52, 21), new Localization(50, 19), "Kuba", new Price(50, Currency.PLN), RideStatus.PENDING, Provider.FREENOW));

        //When //Then
        Awaitility.await().until(this::checkIfRideAssigned);
    }

    private boolean checkIfRideAssigned() {
        List<Ride> rides = rideRepository.getRides();
        return rides.getFirst().getStatus() == RideStatus.FOUND;
    }
}