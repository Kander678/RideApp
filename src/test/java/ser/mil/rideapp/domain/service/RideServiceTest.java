package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ser.mil.rideapp.domain.model.Currency.PLN;
import static ser.mil.rideapp.domain.model.Currency.USD;


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
        double startLat = 52;
        double startLon = 21;
        double endLat = 50;
        double endLon = 19;
        String customer = "Kuba";
        Price price = new Price(30, USD);
        when(pricingService.calculatePrice(any(Localization.class), any(Localization.class), eq(USD), eq(PLN))).thenReturn(price);

        // When
        rideService.orderRide(startLat, startLon, endLat, endLon, customer, USD, PLN, Provider.FREENOW);

        // Then
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    void pairPassengerWithDriver_oneRide_oneDriver() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));

        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride)));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(ride, times(1)).setDriver(driver);
        verify(ride, times(1)).setStatus(RideStatus.FOUND);

        verify(driver, times(1)).setAvailable(false);
    }

    @Test
    void pairPassengerWithDriver_twoRides_oneDriver() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride1 = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Ride ride2 = spy(new Ride("2", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));

        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(ride1, times(1)).setDriver(driver);
        verify(ride2, never()).setDriver(driver);

        verify(ride1, times(1)).setStatus(RideStatus.FOUND);
        verify(ride2, never()).setStatus(RideStatus.FOUND);

        verify(driver, times(1)).setAvailable(false);
    }

    @Test
    void pairPassengerWithDriver_twoRides_threeDrivers() {
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        //Given
        Ride ride1 = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Ride ride2 = spy(new Ride("2", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));
        Driver driver2 = spy(new Driver("2", "Kuba", "Matejczyk", Provider.FREENOW));
        Driver driver3 = spy(new Driver("3", "Kuba", "Matejczyk", Provider.FREENOW));

        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver1, driver2, driver3)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

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
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(ride, never()).setDriver(any());
        verify(ride, never()).setStatus(RideStatus.FOUND);
    }

    @Test
    void pairPassengerWithDriver_zeroRides_oneDriver() {
        //Given
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(driver, never()).setAvailable(false);
    }

    @Test
    void shouldNotParPassenger_whenDriverIsFromAnotherProvider() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.BOLT));

        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride)));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(ride, times(0)).setDriver(driver);
        verify(ride, times(0)).setStatus(RideStatus.FOUND);

        verify(driver, times(0)).setAvailable(false);
    }

    @Test
    void shouldNotPairPassenger_FreeNowDriverAndRideBolt() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.BOLT));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));

        when(rideRepository.pendingRides(Provider.BOLT)).thenReturn(new ArrayList<>(List.of(ride)));
        when(rideRepository.availableDrivers(Provider.BOLT)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.BOLT);
        //Then
        verify(ride, times(0)).setDriver(driver);
        verify(ride, times(0)).setStatus(RideStatus.FOUND);

        verify(driver, times(0)).setAvailable(false);
    }

    @Test
    void shouldNotPairPassenger_FreeNowDriverAndRideUber() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.UBER));
        Driver driver = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));

        when(rideRepository.pendingRides(Provider.UBER)).thenReturn(new ArrayList<>(List.of(ride)));
        when(rideRepository.availableDrivers(Provider.UBER)).thenReturn(new ArrayList<>(List.of(driver)));

        //When
        rideService.pairPassengerWithDriver(Provider.UBER);
        //Then
        verify(ride, times(0)).setDriver(driver);
        verify(ride, times(0)).setStatus(RideStatus.FOUND);

        verify(driver, times(0)).setAvailable(false);
    }

    @Test
    void shouldPairOnePassenger_TwoRidesFreeNow_OneDriverFreeNow_OneDriverUber() {
        //Given
        Localization localization1 = new Localization(52, 21);
        Localization localization2 = new Localization(50, 19);
        Price price = new Price(30, USD);
        Ride ride1 = spy(new Ride("1", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Ride ride2 = spy(new Ride("2", localization1, localization2, "Kuba", price, RideStatus.PENDING, Provider.FREENOW));
        Driver driver1 = spy(new Driver("1", "Kuba", "Matejczyk", Provider.FREENOW));
        Driver driver2 = spy(new Driver("2", "Kuba", "Matejczyk", Provider.UBER));

        when(rideRepository.pendingRides(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(ride1, ride2)));
        when(rideRepository.availableDrivers(Provider.FREENOW)).thenReturn(new ArrayList<>(List.of(driver1, driver2)));

        //When
        rideService.pairPassengerWithDriver(Provider.FREENOW);

        //Then
        verify(ride1, times(1)).setDriver(driver1);
        verify(ride2, never()).setDriver(driver2);

        verify(ride1, times(1)).setStatus(RideStatus.FOUND);
        verify(ride2, never()).setStatus(RideStatus.FOUND);

        verify(driver1, times(1)).setAvailable(false);
        verify(driver2, never()).setAvailable(false);
    }
}