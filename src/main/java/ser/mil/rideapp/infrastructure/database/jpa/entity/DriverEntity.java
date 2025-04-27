package ser.mil.rideapp.infrastructure.database.jpa.entity;

import jakarta.persistence.*;
import ser.mil.rideapp.domain.model.Provider;

import java.util.HashSet;
import java.util.Set;

@Entity
public class DriverEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private boolean available;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Provider> provider;

    public DriverEntity() {

    }

    public DriverEntity(String id, String firstName, String lastName, boolean available, Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider=new HashSet<>();
        this.provider.add(provider);
    }

    public DriverEntity(String id, String firstName, String lastName, boolean available, Set<Provider> provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider=provider;
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

    public Set<Provider> getProvider() {
        return provider;
    }

    public void setProvider(Set<Provider> provider) {
        this.provider = provider;
    }

    public void addProvider(Provider provider) {
        this.provider.add(provider);
    }

    public void removeProvider(Provider provider) {
        this.provider.remove(provider);
    }
}
