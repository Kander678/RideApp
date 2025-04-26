package ser.mil.rideapp.domain.model;

import java.util.Objects;

public class Ride {
    private final String id;
    private final Localization from;
    private final Localization to;
    private final String customer;
    private final Price price;
    private Driver driver;
    private RideStatus status;

    private Provider provider;

    public Ride(String id, Localization from, Localization to, String customer, Price price, RideStatus status, Provider provider) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.price = price;
        this.status = status;
        this.provider = provider;
    }

    public Ride(String id, Localization from, Localization to, String customer, Price price, Driver driver, RideStatus status, Provider provider) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.price = price;
        this.driver = driver;
        this.status = status;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public Localization getFrom() {
        return from;
    }

    public Localization getTo() {
        return to;
    }

    public String getCustomer() {
        return customer;
    }

    public Price getPrice() {
        return price;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(id, ride.id) && Objects.equals(from, ride.from) && Objects.equals(to, ride.to) && Objects.equals(customer, ride.customer) && Objects.equals(price, ride.price) && Objects.equals(driver, ride.driver) && status == ride.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, customer, price, driver);
    }

    @Override
    public String toString() {
        return "Ride{" + "id='" + id + '\'' + ", from=" + from + ", to=" + to + ", customer='" + customer + '\'' + ", price=" + price + ", driver=" + driver + ", status=" + status + '}';
    }
}
