package ser.mil.rideapp.infrastructure.database.jpa.entity;

import jakarta.persistence.*;
import ser.mil.rideapp.domain.model.RideStatus;

@Entity
public class RideEntity {
    @Id
    private String id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "start_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "start_lon"))
    })
    private LocalizationEntity startLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "end_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "end_lon"))
    })
    private LocalizationEntity endLocation;
    private String customer;
    @Embedded
    private PriceEntity price;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private DriverEntity driver;
    @Enumerated(EnumType.STRING)
    private RideStatus status;

    public RideEntity() {
    }

    public RideEntity(String id, LocalizationEntity startLocation, LocalizationEntity endLocation, String customer, PriceEntity price, DriverEntity driver, RideStatus status) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.customer = customer;
        this.price = price;
        this.driver = driver;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalizationEntity getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocalizationEntity startLocation) {
        this.startLocation = startLocation;
    }

    public LocalizationEntity getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocalizationEntity endLocation) {
        this.endLocation = endLocation;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public PriceEntity getPrice() {
        return price;
    }

    public void setPrice(PriceEntity price) {
        this.price = price;
    }

    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }
}
