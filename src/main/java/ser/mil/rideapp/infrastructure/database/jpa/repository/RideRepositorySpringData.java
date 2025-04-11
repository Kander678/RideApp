package ser.mil.rideapp.infrastructure.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.rideapp.infrastructure.database.jpa.entity.RideEntity;

public interface RideRepositorySpringData extends JpaRepository<RideEntity, String> {
}
