package ser.mil.rideapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ser.mil.rideapp.controller.request.LocalizationRequest;
import ser.mil.rideapp.controller.request.RideRequest;
import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.infrastructure.database.jpa.entity.DriverEntity;
import ser.mil.rideapp.infrastructure.database.jpa.repository.DriverRepositorySpringData;
import ser.mil.rideapp.infrastructure.database.jpa.repository.RideRepositorySQL;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RideControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RideRepositorySQL rideRepositorySQL;
    @Autowired
    private DriverRepositorySpringData driverRepositorySpringData;

    @BeforeEach
    void setUp() {
        rideRepositorySQL.clearDatabase();
        List<DriverEntity> drivers = List.of(new DriverEntity("1", "Robert", "Lewandowski", true, Set.of(Provider.FREENOW)), new DriverEntity("2", "Kuba", "Marcinowski", true, Set.of(Provider.FREENOW)));
        driverRepositorySpringData.saveAll(drivers);
    }

    @Test
    void shouldRequestRide() {
        //Given
        RideRequest rideRequest = new RideRequest(new LocalizationRequest(52, 21), new LocalizationRequest(50, 19), "Kuba", Currency.PLN, Currency.PLN, Provider.FREENOW);

        //When //Then
        webTestClient.post().uri("/ride/request").bodyValue(rideRequest).exchange().expectStatus().isOk();

        List<Ride> rides = rideRepositorySQL.getRides();

        assertEquals(1, rides.size());
        Ride ride = rides.getFirst();
        assertEquals(rideRequest.startLocalization().lat(), ride.getFrom().lat());
        assertEquals(rideRequest.startLocalization().lon(), ride.getFrom().lon());
        assertEquals(rideRequest.endLocalization().lat(), ride.getTo().lat());
        assertEquals(rideRequest.endLocalization().lon(), ride.getTo().lon());
        assertEquals(rideRequest.customer(), ride.getCustomer());
        assertEquals(RideStatus.PENDING, ride.getStatus());
        assertNotNull(UUID.fromString(ride.getId()));
        assertNotNull(ride.getPrice());
        assertTrue(ride.getPrice().amount() > 0);
        assertEquals(Currency.PLN, ride.getPrice().currency());
        assertNull(ride.getDriver());
    }
}