package ser.mil.rideapp.infrastructure.database.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import ser.mil.rideapp.domain.model.Provider;

@Entity
public class DriverEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private boolean available;
    private Provider provider;

    public DriverEntity() {

    }

    public DriverEntity(String id, String firstName, String lastName, boolean available, Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
