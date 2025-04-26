package ser.mil.rideapp.infrastructure.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.rideapp.domain.model.Driver;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.model.RideStatus;
import ser.mil.rideapp.infrastructure.database.jpa.entity.RideEntity;

import java.util.List;

public interface RideRepositorySpringData extends JpaRepository<RideEntity, String> {
    List<RideEntity> getAllByProviderAndStatus(Provider provider, RideStatus status);

}
