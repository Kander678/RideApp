package ser.mil.rideapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ser.mil.rideapp.controller.request.LocalizationRequest;
import ser.mil.rideapp.controller.request.RideRequest;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;
import ser.mil.rideapp.domain.service.RideService;
import ser.mil.rideapp.infrastructure.database.jpa.repository.DriverRepositorySpringData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RideControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RideRepository rideRepositorySQL;
    @Autowired
    private DriverRepositorySpringData driverRepositorySpringData;
    @Autowired
    private RideService rideService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldRequestRide() {
        //Given
        RideRequest rideRequest = new RideRequest(
                new LocalizationRequest(52, 21),
                new LocalizationRequest(50, 19),
                "Kuba",
                Currency.PLN,
                Currency.PLN);

        //When //Then
        webTestClient.post()
                .uri("/ride/request")
                .bodyValue(rideRequest)
                .exchange()
                .expectStatus().isOk();

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

    @Test
    void pairPassengerWithDriver_oneRide_oneDriver() {
        //Given
        Ride ride = new Ride(
                "1",
                new Localization(52, 21),
                new Localization(50, 19),
                "Kuba",
                new Price(50, Currency.PLN),
                RideStatus.PENDING);
        rideRepositorySQL.save(ride);

        //When
        webTestClient.get()
                .uri("/ride/assignPendings")
                .exchange()
                .expectStatus().isOk();

        //Then
        List<Ride> foundRides=rideRepositorySQL.getRides().stream()
                .filter(rides -> rides.getStatus().equals(RideStatus.FOUND))
                .toList();

        assertEquals(1, foundRides.size());
        Ride foundRide = foundRides.getFirst();

        assertEquals(0, rideRepositorySQL.pendingRides().size());
        assertEquals(ride.getFrom(), foundRide.getFrom());
        assertEquals(ride.getTo(), foundRide.getTo());
        assertEquals(ride.getCustomer(), foundRide.getCustomer());
        assertNotEquals(ride.getStatus(), foundRide.getStatus());
        assertEquals(ride.getPrice(), foundRide.getPrice());
    }

    @Test
    void pairPassengerWithDriver_threeRide_twoDriver() {
        //Given
        Ride ride1 = new Ride("1", new Localization(52, 21), new Localization(50, 19), "Kuba",
                new Price(50, Currency.PLN), RideStatus.PENDING);
        Ride ride2 = new Ride("2", new Localization(53, 22), new Localization(51, 20), "Marcin",
                new Price(51, Currency.USD), RideStatus.PENDING);
        Ride ride3 = new Ride("3", new Localization(54, 23), new Localization(52, 21), "Marcel",
                new Price(52, Currency.EURO), RideStatus.PENDING);
        rideRepositorySQL.save(ride1);
        rideRepositorySQL.save(ride2);
        rideRepositorySQL.save(ride3);

        //When
        webTestClient.get()
                .uri("/ride/assignPendings")
                .exchange()
                .expectStatus().isOk();

        assertEquals(1, rideRepositorySQL.pendingRides().size());
        assertEquals(2, rideRepositorySQL.getRides().stream().filter(rides -> rides.getStatus().equals(RideStatus.FOUND)).count());
    }
}