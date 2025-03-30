package ser.mil.rideapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.domain.repository.RideRepository;
import ser.mil.rideapp.domain.service.RideService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideAppApplicationTests {
    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    @Test
    void shouldAddRide() {
        // Given
        String from = "Politechnika";
        String to = "Saski";
        String customer = "Kuba";

        // When
        rideService.orderRide(from, to, customer);

        // Then
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    void pairDriverWithPassengerOneWhenOne() {
        //Given
        Ride ride = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk"));

        Mockito.when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride)));
        Mockito.when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride, times(1)).setDriver(driver);
    }

    @Test
    void pairDriverWithPassengerOneWhenTwo() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Ride ride2 = spy(new Ride("2", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk"));

        Mockito.when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        Mockito.when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, times(1)).setDriver(driver);
    }

    @Test
    void pairDriverWithPassengerTwoWhenThree() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Ride ride2 = spy(new Ride("2", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk"));
        Driver driver2 = spy(new Driver("2", "Kuba", "Matejczyk"));
        Driver driver3 = spy(new Driver("3", "Kuba", "Matejczyk"));

        Mockito.when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        Mockito.when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver1, driver2, driver3)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, times(1)).setDriver(driver1);
        verify(ride2, times(1)).setDriver(driver2);

        verify(driver1, times(1)).setAvailable(false);
    }

    @Test
    void pairDriverWithPassengerZeroWhenOne() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Mockito.when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, times(0)).setDriver(null);
    }

    @Test
    void pairDriverWithPassengerOneWhenZero() {
        //Given
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk"));
        Mockito.when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver1)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(driver1, times(0)).setAvailable(false);
    }

}


