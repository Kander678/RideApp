package ser.mil.rideapp.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Driver {
    private final String id;
    private final String firstName;
    private final String lastName;
    private boolean available;
    private Set<Provider> provider;

    public Driver(String id, String firstName, String lastName,Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.provider=new HashSet<>();
        this.provider.add(provider);
        available = true;
    }

    public Driver(String id, String firstName, String lastName, boolean available, Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider=new HashSet<>();
        this.provider.add(provider);
    }

    public Driver(String id, String firstName, String lastName, boolean available, Set<Provider> provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider=provider;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    @Override
    public String toString() {
        return "Driver{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", available=" + available + '}';
    }
}
