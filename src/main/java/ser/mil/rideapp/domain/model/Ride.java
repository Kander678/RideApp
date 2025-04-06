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

    public Ride(String id, Localization from, Localization to, String customer, Price price, RideStatus status) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.price = price;
        this.status = status;
    }

    public Ride(String id, Localization from, Localization to, String customer, Price price, Driver driver, RideStatus status) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.price = price;
        this.driver = driver;
        this.status = status;
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
