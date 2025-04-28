package ser.mil.rideapp.infrastructure.database.jpa.repository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.*;
import ser.mil.rideapp.domain.repository.RideRepository;
import ser.mil.rideapp.infrastructure.database.jpa.entity.DriverEntity;
import ser.mil.rideapp.infrastructure.database.jpa.entity.LocalizationEntity;
import ser.mil.rideapp.infrastructure.database.jpa.entity.PriceEntity;
import ser.mil.rideapp.infrastructure.database.jpa.entity.RideEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RideRepositorySQL implements RideRepository, ApplicationRunner {
    private final DriverRepositorySpringData driverRepository;
    private final RideRepositorySpringData rideRepository;

    public RideRepositorySQL(DriverRepositorySpringData driverRepository, RideRepositorySpringData rideRepository) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public void save(Ride ride) {
        rideRepository.save(mapToRideEntity(ride));
    }

    @Override
    public void save(Driver driver) {
        driverRepository.save(mapToDriverEntity(driver));
    }

    @Override
    public List<Ride> getRides() {
        return rideRepository.findAll().stream().map(RideRepositorySQL::mapToRide).collect(Collectors.toList());
    }

    @Override
    public List<Driver> getDrivers() {
        return driverRepository.findAll().stream().map(RideRepositorySQL::mapToDriver).collect(Collectors.toList());
    }

    @Override
    public List<Ride> pendingRides(Provider provider) {
        return rideRepository.getAllByProviderAndStatus(provider, RideStatus.PENDING).stream().map(RideRepositorySQL::mapToRide).collect(Collectors.toList());
    }

    @Override
    public List<Driver> availableDrivers(Provider provider) {
        return driverRepository.getAllByProviderAndAvailable(provider, true).stream().map(RideRepositorySQL::mapToDriver).collect(Collectors.toList());
    }

    @Override
    public Driver getDriverById(String id) {
        return mapToDriver(driverRepository.getDriverById(id));
    }


    @Override
    public void run(ApplicationArguments args) {
        List<DriverEntity> drivers = List.of(new DriverEntity("1", "Robert", "Lewandowski", true, Provider.FREENOW), new DriverEntity("2", "Kuba", "Marcinowski", true, Provider.FREENOW));
        driverRepository.saveAll(drivers);
    }

    public void clearDatabase() {
        getRides().forEach(ride -> {
                    ride.setDriver(null);
                    save(ride);
                }
        );
        rideRepository.deleteAll();
        driverRepository.deleteAll();
    }

    private static RideEntity mapToRideEntity(Ride ride) {
        return new RideEntity(ride.getId(), mapToLocalizationEntity(ride.getFrom()), mapToLocalizationEntity(ride.getTo()), ride.getCustomer(), mapToPriceEntity(ride.getPrice()), mapToDriverEntity(ride.getDriver()), ride.getStatus(), ride.getProvider());
    }


    private static Ride mapToRide(RideEntity entity) {
        return new Ride(entity.getId(), mapToLocalization(entity.getStartLocation()), mapToLocalization(entity.getEndLocation()), entity.getCustomer(), mapToPrice(entity.getPrice()), mapToDriver(entity.getDriver()), entity.getStatus(), entity.getProvider());
    }

    private static DriverEntity mapToDriverEntity(Driver driver) {
        if (driver == null) return null;
        return new DriverEntity(driver.getId(), driver.getFirstName(), driver.getLastName(), driver.getAvailable(), driver.getProvider());
    }

    private static Driver mapToDriver(DriverEntity entity) {
        if (entity == null) return null;
        return new Driver(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getAvailable(), entity.getProvider());
    }

    private static PriceEntity mapToPriceEntity(Price price) {
        return new PriceEntity(price.amount(), price.currency());
    }

    private static LocalizationEntity mapToLocalizationEntity(Localization localization) {
        return new LocalizationEntity(localization.lat(), localization.lon());
    }

    private static Localization mapToLocalization(LocalizationEntity localization) {
        return new Localization(localization.getLat(), localization.getLon());
    }

    private static Price mapToPrice(PriceEntity price) {
        return new Price(price.getAmount(), price.getCurrency());
    }

}
