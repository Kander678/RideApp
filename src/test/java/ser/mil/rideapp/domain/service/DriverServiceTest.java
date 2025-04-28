package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private DriverService driverService;


    @Test
    void shouldAddDriver() {
        //Given
        String firstName = "John";
        String lastName = "Doe";
        Provider provider=Provider.FREENOW;

        //When
        driverService.addDriver(firstName, lastName, provider);

        //Then
        verify(rideRepository,times(1)).save(any(Driver.class));
    }
    @Test
    void shouldAddProvider(){
        //Given
        String firstName = "John";
        String lastName = "Doe";
        Provider provider1=Provider.FREENOW;
        Provider provider2=Provider.BOLT;
        Driver driver=driverService.addDriver(firstName, lastName, provider1);

        when(rideRepository.findDriverById(driver.getId())).thenReturn(driver);

        //When
        driverService.addProvider(driver.getId(), provider2);

        //Then
        assertTrue(driver.getProvider().contains(provider1));
        assertTrue(driver.getProvider().contains(provider2));
    }
    @Test
    void shouldRemoveProvider(){
        String firstName = "John";
        String lastName = "Doe";
        Provider provider1=Provider.FREENOW;
        Provider provider2=Provider.BOLT;
        Driver driver=driverService.addDriver(firstName, lastName, provider1);

        when(rideRepository.findDriverById(driver.getId())).thenReturn(driver);
        driverService.addProvider(driver.getId(), provider2);

        driverService.removeProvider(driver.getId(), provider2);

        assertTrue(driver.getProvider().contains(Provider.FREENOW));
        assertFalse(driver.getProvider().contains(Provider.BOLT));
    }
}