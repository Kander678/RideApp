package ser.mil.rideapp.domain.model;

public class Driver {
    private final String id;
    private final String firstName;
    private final String lastName;
    private boolean available;
    private Provider provider;

    public Driver(String id, String firstName, String lastName,Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.provider = provider;
        available = true;
    }

    public Driver(String id, String firstName, String lastName, boolean available, Provider provider) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
        this.provider = provider;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Driver{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", available=" + available + '}';
    }
}
