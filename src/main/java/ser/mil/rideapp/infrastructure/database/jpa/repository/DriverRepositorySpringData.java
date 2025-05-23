package ser.mil.rideapp.infrastructure.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.infrastructure.database.jpa.entity.DriverEntity;

import java.util.List;

public interface DriverRepositorySpringData extends JpaRepository<DriverEntity, String> {
    List<DriverEntity> getAllByProviderAndAvailable(Provider provider, boolean available);

    DriverEntity getDriverById(String id);
}
