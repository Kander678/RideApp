package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Ride;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {
    @Mock
    private RideRepository rideRepository;

    @Mock
    private PricingService pricingService;

    @InjectMocks
    private RideService rideService;

    @Test
    void shouldAddRide() {
        // Given
        String from = "Politechnika";
        String to = "Saski";
        String customer = "Kuba";
        when(pricingService.calculatePrice(from, to)).thenReturn(50.0);

        // When
        rideService.orderRide(from, to, customer);

        // Then
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    void pairPassengerWithDriver_oneRide_oneDriver() {
        //Given
        Ride ride = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk"));

        when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride)));
        when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride, times(1)).setDriver(driver);
        verify(ride, times(1)).setStatus(RideStatus.FOUND);

        verify(driver,times(1)).setAvailable(false);
    }

    @Test
    void pairPassengerWithDriver_twoRides_oneDriver() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Ride ride2 = spy(new Ride("2", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk"));

        when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, times(1)).setDriver(driver);
        verify(ride2, never()).setDriver(driver);

        verify(ride1, times(1)).setStatus(RideStatus.FOUND);
        verify(ride2, never()).setStatus(RideStatus.FOUND);

        verify(driver,times(1)).setAvailable(false);
    }

    @Test
    void pairPassengerWithDriver_twoRides_threeDrivers() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Ride ride2 = spy(new Ride("2", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk"));
        Driver driver2 = spy(new Driver("2", "Kuba", "Matejczyk"));
        Driver driver3 = spy(new Driver("3", "Kuba", "Matejczyk"));

        when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver1, driver2, driver3)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, times(1)).setDriver(driver1);
        verify(ride2, times(1)).setDriver(driver2);

        verify(ride1, times(1)).setStatus(RideStatus.FOUND);
        verify(ride2, times(1)).setStatus(RideStatus.FOUND);

        verify(driver1, times(1)).setAvailable(false);
        verify(driver2, times(1)).setAvailable(false);
        verify(driver3, never()).setAvailable(false);
    }

    @Test
    void pairPassengerWithDriver_oneRide_zeroDriver() {
        //Given
        Ride ride1 = spy(new Ride("1", "Lublin", "Krakow", "Kuba", 50, RideStatus.PENDING));
        when(rideRepository.pendingRides()).thenReturn(new ArrayList<>(List.of(ride1)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(ride1, never()).setDriver(any());
        verify(ride1, never()).setStatus(RideStatus.FOUND);
    }

    @Test
    void pairPassengerWithDriver_zeroRides_oneDriver() {
        //Given
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk"));
        when(rideRepository.availableDrivers()).thenReturn(new ArrayList<>(List.of(driver1)));

        //When
        rideService.pairPassengerWithDriver();

        //Then
        verify(driver1, never()).setAvailable(false);
    }

}