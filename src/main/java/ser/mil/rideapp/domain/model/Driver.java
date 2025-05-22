package ser.mil.rideapp.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Driver {
    private final String id;
    private final String firstName;
    private final String lastName;
    private boolean available;
    private Set<Provider> providers;

    public Driver(String id, String firstName, String lastName,Provider providers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.providers =new HashSet<>();
        this.providers.add(providers);
        available = true;
    }

    public Driver(String id, String firstName, String lastName, boolean available, Provider providers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.providers =new HashSet<>();
        this.providers.add(providers);
    }

    public Driver(String id, String firstName, String lastName, boolean available, Set<Provider> providers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.providers = providers;
    }

    public Driver(String firstName, String lastName, Provider providers, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.providers =new HashSet<>();
        this.providers.add(providers);
        available = true;
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

    public Set<Provider> getProviders() {
        return providers;
    }

    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
    }

    public void addProvider(Provider provider) {
        this.providers.add(provider);
    }
    public void removeProvider(Provider provider) {
        this.providers.remove(provider);
    }

    @Override
    public String toString() {
        return "Driver{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", available=" + available + '}';
    }
}
