package ser.mil.rideapp.infrastructure.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.rideapp.infrastructure.database.jpa.entity.DriverEntity;

public interface DriverRepositorySpringData extends JpaRepository<DriverEntity, String> {
}
