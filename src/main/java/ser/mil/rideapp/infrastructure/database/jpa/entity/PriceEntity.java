package ser.mil.rideapp.infrastructure.database.jpa.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ser.mil.rideapp.domain.model.Currency;

@Embeddable
public class PriceEntity {
    private double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public PriceEntity(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public PriceEntity() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
