package ser.mil.rideapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ser.mil.rideapp.controller.request.DriverRequest;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.infrastructure.database.jpa.repository.RideRepositorySQL;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DriverControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RideRepositorySQL rideRepositorySQL;

    @BeforeEach
    void setUp() {
        rideRepositorySQL.clearDatabase();
    }

    @Test
    void shouldCreateDriver() {
        //Given
        DriverRequest driverRequest = new DriverRequest("firstName", "LastName", Provider.FREENOW);

        //When //Then
        webTestClient.post().uri("/driver/create")
                .bodyValue(driverRequest)
                .exchange()
                .expectStatus().isOk();

        List<Driver> drivers = rideRepositorySQL.getDrivers();
        assertEquals(1, drivers.size());
        Driver driver = drivers.getFirst();
        assertEquals(driverRequest.firstName(), driver.getFirstName());
        assertEquals(driverRequest.lastName(), driver.getLastName());
        assertEquals(Set.of(Provider.FREENOW), driver.getProviders());
    }

    @Test
    void shouldNotCreateDriver(){
        //Given
        DriverRequest driverRequest = new DriverRequest(null, "LastName", Provider.FREENOW);

        //When //Then
        webTestClient.post().uri("/driver/create")
                .bodyValue(driverRequest)
                .exchange()
                .expectStatus().isBadRequest();
        List<Driver> drivers = rideRepositorySQL.getDrivers();
        assertEquals(0, drivers.size());

    }
}